import gym
import RCRS_gym

import os
import numpy as np
import shutil
import sys
import socket
import argparse
from scipy import stats
import pandas as pd
from openpyxl import Workbook 
import matplotlib.pyplot as plt
import time
from datetime import date, datetime
import subprocess
from subprocess import *

from stable_baselines.common.vec_env import DummyVecEnv, VecNormalize, VecEnv
from stable_baselines import PPO2, DQN, A2C, DDPG
from stable_baselines import results_plotter
from stable_baselines.deepq.policies import MlpPolicy, FeedForwardPolicy, DQNPolicy
from stable_baselines.bench import Monitor
from stable_baselines.results_plotter import load_results, ts2xy
from stable_baselines.ddpg import AdaptiveParamNoiseSpec

import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning) 

# Directory 
hostname = socket.gethostname()
# Parent Directory path 
parent_dir = sys.path[0]
# Path 
path = os.path.join(parent_dir, hostname) 
# os.mkdir(path) 
path_for_kill_file = os.path.join(parent_dir, "kill.sh")
columns = ['Mean Rewards', 'Standard deviation']
df = pd.DataFrame(columns=columns)


env = gym.make('RCRS-v2')
# The algorithms require a vectorized environment to run
env = DummyVecEnv([lambda: env]) 
# Automatically normalize the input features
env = VecNormalize(env, norm_obs=True, norm_reward=False, clip_obs=10.)

class CustomPolicy(FeedForwardPolicy):
    def __init__(self, *args, **kwargs):
        super(CustomPolicy, self).__init__(*args, **kwargs,
                                           net_arch=[dict(pi=[256, 256, 64, 64],
                                                          vf=[256, 256, 64, 64])], 
                                           feature_extraction="mlp")

class DQN(MlpPolicy):

    def learn(self, total_timesteps, callback=None, log_interval=100, tb_log_name="DQN",
              reset_num_timesteps=True, replay_wrapper=None):

        new_tb_log = self._init_num_timesteps(reset_num_timesteps)

        with SetVerbosity(self.verbose), TensorboardWriter(self.graph, self.tensorboard_log, tb_log_name, new_tb_log) \
                as writer:
            self._setup_learn()

            # Create the replay buffer
            if self.prioritized_replay:
                self.replay_buffer = PrioritizedReplayBuffer(self.buffer_size, alpha=self.prioritized_replay_alpha)
                if self.prioritized_replay_beta_iters is None:
                    prioritized_replay_beta_iters = total_timesteps
                else:
                    prioritized_replay_beta_iters = self.prioritized_replay_beta_iters
                self.beta_schedule = LinearSchedule(prioritized_replay_beta_iters,
                                                    initial_p=self.prioritized_replay_beta0,
                                                    final_p=1.0)
            else:
                self.replay_buffer = ReplayBuffer(self.buffer_size)
                self.beta_schedule = None

            if replay_wrapper is not None:
                assert not self.prioritized_replay, "Prioritized replay buffer is not supported by HER"
                self.replay_buffer = replay_wrapper(self.replay_buffer)

            # Create the schedule for exploration starting from 1.
            self.exploration = LinearSchedule(schedule_timesteps=int(self.exploration_fraction * total_timesteps),
                                              initial_p=self.exploration_initial_eps,
                                              final_p=self.exploration_final_eps)

            episode_rewards = [0.0]
            episode_successes = []
            obs = self.env.reset()

            obs_hdqn_old = None
            action_hdqn = None
            F = 0.
            reset = True

            for _ in range(total_timesteps):
                if callback is not None:
                    # Only stop training if return value is False, not when it is None. This is for backwards
                    # compatibility with callbacks that have no return statement.
                    if callback(locals(), globals()) is False:
                        break
                # Take action and update exploration to the newest value
                kwargs = {}
                if not self.param_noise:
                    update_eps = self.exploration.value(self.num_timesteps)
                    update_param_noise_threshold = 0.
                else:
                    update_eps = 0.
                    # Compute the threshold such that the KL divergence between perturbed and non-perturbed
                    # policy is comparable to eps-greedy exploration with eps = exploration.value(t).
                
                    update_param_noise_threshold = \
                        -np.log(1. - self.exploration.value(self.num_timesteps) +
                                self.exploration.value(self.num_timesteps) / float(self.env.action_space.n))
                    kwargs['reset'] = reset
                    kwargs['update_param_noise_threshold'] = update_param_noise_threshold
                    kwargs['update_param_noise_scale'] = True
               
                ### UPDATE ###
                obs[-1] = 0
                if(obs[-1] == 0):
                    if not reset:
                        # Store HDQN transition
                        self.replay_buffer.add(obs_hdqn_old, action_hdqn, F, obs, float(done))

                    # Select new goal for the agent using the current Q function
                    action = self.act(np.array(obs)[None], update_eps=update_eps, **kwargs)[0]
                    env_action = action

                    # Update bookkeepping for next HDQN buffer update
                    obs_hdqn_old = obs
                    action_hdqn = env_action
                    F = 0.
                else:
                    # Agent is busy, so select a dummy action (it will be ignored anyway)
                    env_action = 0

                reset = False
                new_obs, rew, done, info = self.env.step(env_action)
                F = F + rew

                if writer is not None:
                    ep_rew = np.array([rew]).reshape((1, -1))
                    ep_done = np.array([done]).reshape((1, -1))
                    total_episode_reward_logger(self.episode_reward, ep_rew, ep_done, writer,
                                                self.num_timesteps)

                episode_rewards[-1] += rew

                if done:
                    # Store HDQN transition
                    self.replay_buffer.add(obs_hdqn_old, action_hdqn, F, obs, float(done))

                    maybe_is_success = info.get('is_success')
                    if maybe_is_success is not None:
                        episode_successes.append(float(maybe_is_success))
                    if not isinstance(self.env, VecEnv):
                        obs = self.env.reset()
                    episode_rewards.append(0.0)
                    reset = True

                # Do not train if the warmup phase is not over
                # or if there are not enough samples in the replay buffer
                can_sample = self.replay_buffer.can_sample(self.batch_size)
                if can_sample and self.num_timesteps > self.learning_starts \
                        and self.num_timesteps % self.train_freq == 0:
                    # Minimize the error in Bellman's equation on a batch sampled from replay buffer.
                    # pytype:disable=bad-unpacking
                    if self.prioritized_replay:
                        assert self.beta_schedule is not None, \
                               "BUG: should be LinearSchedule when self.prioritized_replay True"
                        experience = self.replay_buffer.sample(self.batch_size,
                                                               beta=self.beta_schedule.value(self.num_timesteps))
                        (obses_t, actions, rewards, obses_tp1, dones, weights, batch_idxes) = experience
                    else:
                        obses_t, actions, rewards, obses_tp1, dones = self.replay_buffer.sample(self.batch_size)
                        weights, batch_idxes = np.ones_like(rewards), None
                    # pytype:enable=bad-unpacking

                    if writer is not None:
                        # run loss backprop with summary, but once every 100 steps save the metadata
                        # (memory, compute time, ...)
                        if (1 + self.num_timesteps) % 100 == 0:
                            run_options = tf.RunOptions(trace_level=tf.RunOptions.FULL_TRACE)
                            run_metadata = tf.RunMetadata()
                            summary, td_errors = self._train_step(obses_t, actions, rewards, obses_tp1, obses_tp1,
                                                                  dones, weights, sess=self.sess, options=run_options,
                                                                  run_metadata=run_metadata)
                            writer.add_run_metadata(run_metadata, 'step%d' % self.num_timesteps)
                        else:
                            summary, td_errors = self._train_step(obses_t, actions, rewards, obses_tp1, obses_tp1,
                                                                  dones, weights, sess=self.sess)
                        writer.add_summary(summary, self.num_timesteps)
                    else:
                        _, td_errors = self._train_step(obses_t, actions, rewards, obses_tp1, obses_tp1, dones, weights,
                                                        sess=self.sess)

                    if self.prioritized_replay:
                        new_priorities = np.abs(td_errors) + self.prioritized_replay_eps
                        assert isinstance(self.replay_buffer, PrioritizedReplayBuffer)
                        self.replay_buffer.update_priorities(batch_idxes, new_priorities)

                if can_sample and self.num_timesteps > self.learning_starts and \
                        self.num_timesteps % self.target_network_update_freq == 0:
                    # Update target network periodically.
                    self.update_target(sess=self.sess)

                if len(episode_rewards[-101:-1]) == 0:
                    mean_100ep_reward = -np.inf
                else:
                    mean_100ep_reward = round(float(np.mean(episode_rewards[-101:-1])), 1)

                num_episodes = len(episode_rewards)
                if self.verbose >= 1 and done and log_interval is not None and len(episode_rewards) % log_interval == 0:
                    logger.record_tabular("steps", self.num_timesteps)
                    logger.record_tabular("episodes", num_episodes)
                    if len(episode_successes) > 0:
                        logger.logkv("success rate", np.mean(episode_successes[-100:]))
                    logger.record_tabular("mean 100 episode reward", mean_100ep_reward)
                    logger.record_tabular("% time spent exploring",
                                          int(100 * self.exploration.value(self.num_timesteps)))
                    logger.dump_tabular()

                self.num_timesteps += 1

        return self



def run_model(algorithm, training_timesteps, testing_timesteps, training_iterations, testing_iterations, learning_rate, batch_size):
	 
    model = DQN(CustomPolicy, env, learning_rate=learning_rate,  batch_size = batch_size)

    for k in range(training_iterations):
        model.learn(total_timesteps=int(training_timesteps))
        model.save("{}_{}_{}_{}".format("rcrs_wgts", k, algorithm, hostname))
        subprocess.Popen(path_for_kill_file, shell=True)

    for j in range(testing_iterations):
	    # Load the trained agent

	    model = DQN.load("{}_{}_{}_{}".format("rcrs_wgts", j, algorithm, hostname))
	    # Reset the environment
	    obs = env.reset()
	    # Create an empty list to store reward values 
	    final_rewards = []
	    for _ in range(testing_timesteps):
	        # predict the values
	        action, _states = model.predict(obs)
	        obs, rewards, dones, info = env.step(action)
	        if dones == True:
	            final_rewards.append(rewards)
	    # Print the mean reward
	    print(np.mean(final_rewards))
	    # Print the standard deviation of reward
	    print(np.std(final_rewards))
	    # Create a DataFrame to save the mean and standard deviation
	    df = df.append({'Mean Rewards': np.mean(final_rewards), 'Standard deviation': np.std(final_rewards)}, ignore_index=True)
	    
	    df.to_csv("{}_{}_{}".format(1, algorithm, "MeanAndStdReward.csv", sep=',',index=True))
	    
	    subprocess.Popen(path_for_kill_file, shell=True)
    subprocess.Popen(path_for_kill_file, shell=True)


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("algorithm", help = 'Which algorithm are you using', type= str)
	parser.add_argument("training_timesteps", help = "How many traning steps are there?", type=int)
	parser.add_argument("testing_timesteps", help = "How many testing steps are there?", type=int)
	parser.add_argument("training_iterations", help = "How many traning iterations are there?", type=int)
	parser.add_argument("testing_iterations", help = "How many traning iterations are there?", type=int)
	parser.add_argument("learning_rate", help = "What is the learning rate?", type=float)
	parser.add_argument("batch_size", help = "What is the batch size?", type=int)
	args = parser.parse_args()
	run_model(args.algorithm, args.training_timesteps,args.testing_timesteps, args.training_iterations, args.testing_iterations, args.learning_rate, args.batch_size)


if __name__ == '__main__':
	main()