#!/usr/bin/python

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
import logging
import re

from stable_baselines.common.vec_env import DummyVecEnv, VecNormalize, VecEnv
from stable_baselines import PPO2, A2C, DDPG
from stable_baselines import results_plotter
from stable_baselines.common.policies import FeedForwardPolicy
from stable_baselines.bench import Monitor
from stable_baselines.results_plotter import load_results, ts2xy
from stable_baselines.ddpg import AdaptiveParamNoiseSpec

import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning)


FORMAT = "%(levelname)-8s -- [%(filename)s:%(lineno)s - %(funcName)15s()] %(message)s"
logging.basicConfig(format=FORMAT, level=logging.INFO)
logger = logging.getLogger(__name__)
# Directory 
hostname = socket.gethostname()

# gym_env_id = 'RCRS-v2'
# algorithm = 'ppo2'
# total_timesteps = 1e6
saving_interval = 20
evaluate_timesteps = 1e4
best_mean_reward, n_episodes = 0, 0

current_time_string = datetime.now().strftime('%Y-%m-%d-%H-%M-%S')
log_dir = "./rcrs-results/{}_rcrs_tensorboard/".format(hostname)
pickle_dir = log_dir + "pickles/"
os.makedirs(log_dir, exist_ok=True)
os.makedirs(pickle_dir, exist_ok=True)

monitor_file_path = log_dir + current_time_string + "-monitor.csv"
env = gym.make('RCRS-v2')
env = Monitor(env, monitor_file_path, allow_early_resets=True)
env = DummyVecEnv([lambda: env])
env = VecNormalize(env, norm_obs=True, norm_reward=False, clip_obs=10.)

def remove_files_with_pattern(dir, pattern):
    for f in os.listdir(dir):
        if re.search(pattern, f):
            os.remove(os.path.join(dir, f))


def rename_files(dir, pattern_to_search, old_pattern, new_pattern):
    for f in os.listdir(dir):
        if re.search(pattern_to_search, f):
            new_name = re.sub(old_pattern, new_pattern, f)
            os.rename(os.path.join(dir, f), os.path.join(dir, new_name))


def get_files_with_pattern(dir, pattern):
    files = []
    for f in os.listdir(dir):
        if re.search(pattern, f):
            files.append(os.path.join(dir,f))
    return files


def load_ppo_model(env, learning_rate, batch_size, algorithm):
    from stable_baselines.common.policies import MlpPolicy
    model = None
    existing_pickle_files = get_files_with_pattern(pickle_dir, 'ppo2_recent_model.pkl')
    
    for file_name in existing_pickle_files:
        search = re.search('ppo2_recent_model.pkl', file_name)
        if search:
            model = PPO2.load(file_name, env=env, verbose=0, tensorboard_log=log_dir)
            logger.info("Loading existing pickle file for environment {} with algorithm {} and policy '{}'.".format(env, algorithm, model.policy))
            return model
    
    logger.debug("No pickle was found for environment {}. Creating new model with algorithm {} and policy 'MlpPolicy'...".format(env, algorithm))
    model = PPO2(policy='MlpPolicy', env=env, verbose=0, tensorboard_log=log_dir, learning_rate=learning_rate, n_steps = batch_size)
    return model  

def load_dqn_model(env, learning_rate, batch_size, algorithm):
    from stable_baselines.deepq.policies import MlpPolicy
    model = None
    existing_pickle_files = get_files_with_pattern(pickle_dir, 'dqn_recent_model.pkl')
    
    for file_name in existing_pickle_files:
        search = re.search('dqn_recent_model.pkl', file_name)
        if search:
            model = DQN.load(file_name, env=env, verbose=0, tensorboard_log=log_dir)
            logger.info("Loading existing pickle file for environment {} with algorithm {} and policy '{}'.".format(env, algorithm, model.policy))
            return model
    
    logger.debug("No pickle was found for environment {}. Creating new model with algorithm {} and policy 'MlpPolicy'...".format(env, algorithm))
    model = DQN(policy='MlpPolicy', env=env, verbose=0, tensorboard_log=log_dir, learning_rate=learning_rate, batch_size = batch_size)
    return model  

def load_a2c_model(env, learning_rate, batch_size, algorithm):
    from stable_baselines.common.policies import MlpPolicy
    model = None
    existing_pickle_files = get_files_with_pattern(pickle_dir, 'ppo2_recent_model.pkl')
    
    for file_name in existing_pickle_files:
        search = re.search('ppo2_recent_model.pkl', file_name)
        if search:
            model = A2C.load(file_name, env=env, verbose=0, tensorboard_log=log_dir)
            logger.info("Loading existing pickle file for environment {} with algorithm {} and policy '{}'.".format(env, algorithm, model.policy))
            return model
    
    logger.debug("No pickle was found for environment {}. Creating new model with algorithm {} and policy 'MlpPolicy'...".format(env, algorithm))
    model = A2C(policy='MlpPolicy', env=env, verbose=0, tensorboard_log=log_dir, learning_rate=learning_rate, n_steps = batch_size)
    return model  


def load_model(env, learning_rate, batch_size, algorithm):
	if (algorithm == 'PPO2'):
		model = load_ppo_model(env, learning_rate, batch_size, algorithm)
	elif (algorithm == 'DQN'):
		model = load_dqn_model(env, learning_rate, batch_size, algorithm)
	elif (algorithm == 'A2C'):
		model = load_a2c_model(env, learning_rate, batch_size, algorithm)
	return model

def train(env, algorithm, total_timesteps, learning_rate, batch_size):   
    global best_mean_reward
 
    model = load_model(env, learning_rate, batch_size, algorithm)
    print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ --- Training started")
    f = open('mean_reward.txt', 'a+')
    f.close()
    with open('mean_reward.txt', 'r+') as f:
        lines = f.read().splitlines()
        last_line = lines[-1] if lines else None
        best_mean_reward = float(last_line.split()[4]) if isinstance(last_line, str) else -float('inf')

    logger.info("Starting train.")
    model.learn(int(total_timesteps), callback=callback)
    print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ --- Learning finished")
    kill_file()
    print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ --- File killed")
    return model


def callback(_locals, _globals):
    """
    Callback called after n steps
    :param _locals: (dict)
    :param _globals: (dict)
    """
    global best_mean_reward, n_episodes, saving_interval

    n_episodes += 1
    if n_episodes % saving_interval == 0:
        x, y = ts2xy(load_results(log_dir), 'episodes')
        if len(x) > 0:
            mean_reward = np.mean(y[-int(saving_interval):])
            logger.info("{}: Best mean reward: {:.2f} - Last mean reward per episode: {:.2f}\n".format(x[-1], best_mean_reward, mean_reward))

            with open("mean_reward.txt", "a") as text_file:
                print("{}: Best mean reward: {:.2f} - Last mean reward per episode: {:.2f}".format(x[-1], best_mean_reward, mean_reward), file=text_file)

            _locals['self'].save(pickle_dir + 'ppo2_recent_model.pkl')
            if mean_reward >= best_mean_reward:
                best_mean_reward = mean_reward
                logger.debug("Saving new best model")
                _locals['self'].save(pickle_dir + 'ppo2_best_model.pkl')

    return True


def evaluate(env, num_steps, algorithm, learning_rate, batch_size):
    """
    Evaluate a RL agent
    :param model: (BaseRLModel object) the RL Agent
    :param num_steps: (int) number of timesteps to evaluate it
    """
    print("******************************************************************************************************************** --- Testing started")
    model = load_model(env, learning_rate, batch_size, algorithm)
    episode_rewards = [0.0]
    obs = env.reset()
    for i in range(int(num_steps)):
        # _states are only useful when using LSTM policies
        action, _states = model.predict(obs)
        obs, rewards, dones, info = env.step(action)
        
        # Stats
        episode_rewards[-1] += rewards[0]
        if dones[0]:
            obs = env.reset()
            episode_rewards.append(0.0)
    print("******************************************************************************************************************** --- Testing completed")
    mean_reward = round(np.mean(episode_rewards), 2)
    std_reward = round(np.std(episode_rewards), 2)
    logger.info("Mean reward: {}, Num episodes: {}, Standard deviation: {}".format(mean_reward, len(episode_rewards), std_reward))
    kill_file()


def kill_file():
	# Path 
	path = os.path.join(sys.path[0], hostname) 
	# os.mkdir(path) 
	path_for_kill_file = os.path.join(sys.path[0], "kill.sh")

	subprocess.Popen(path_for_kill_file, shell=True)


def moving_average(values, window):
    """
    Smooth values by doing a moving average
    :param values: (numpy array)
    :param window: (int)
    :return: (numpy array)
    """
    weights = np.repeat(1.0, window) / window
    return np.convolve(values, weights, 'valid')


def plot_results(log_folder, title='Learning Curve'):
    """
    plot the results
    :param log_folder: (str) the save location of the results to plot
    :param title: (str) the title of the task to plot
    """
    x, y = ts2xy(load_results(log_folder), 'episodes')
    y = moving_average(y, window=1)
    x = x[len(x) - len(y):]

    fig = plt.figure(title)
    plt.plot(x, y)
    plt.xlabel('Number of Episodes')
    plt.ylabel('Rewards')
    plt.title(title + " Smoothed")
    plt.show()


def plot_rewards():
    """Plot the rewards that the agent got in the callback of the training."""
    steps = []
    rewards = []
    with open('mean_reward.txt','r') as f:
        for line in f:
            step, *middle, reward = line.split()
            step = step[:-1]
            steps.append(float(step))
            rewards.append(float(reward))

    plt.plot(steps, rewards)
    plt.xlabel('Number of Timesteps')
    plt.ylabel('Rewards')
    plt.title("Learning Curve")
    plt.xticks(np.arange(int(min(steps)), int(max(steps)+1), 2e5))
    plt.ticklabel_format(style='sci', axis='x', scilimits=(4,5))
    plt.show()

def run_model(algorithm, training_timesteps, testing_timesteps, training_iterations, testing_iterations, learning_rate, batch_size):
	# env = make_env(gym_env_id)
	train(env, algorithm, training_timesteps, learning_rate, batch_size)
	evaluate(env, testing_timesteps, algorithm, learning_rate, batch_size)
	plot_results(log_dir)
	plot_rewards()

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
    kill_file()
    # env.close()



	
