#!/usr/bin/env python

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
from stable_baselines.common.policies import FeedForwardPolicy
from stable_baselines.bench import Monitor
from stable_baselines.results_plotter import load_results, ts2xy
from stable_baselines.ddpg import AdaptiveParamNoiseSpec
from hdqn import HDQN

import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning) 

# Directory 
hostname = socket.gethostname()
# Path 
path = os.path.join(sys.path[0], hostname) 
# os.mkdir(path) 
path_for_kill_file = os.path.join(sys.path[0], "kill.sh")

env = gym.make('RCRS-v2')
# The algorithms require a vectorized environment to run
env = DummyVecEnv([lambda: env]) 
# Automatically normalize the input features
env = VecNormalize(env, norm_obs=True, norm_reward=False, clip_obs=10.)


class CustomPolicy(FeedForwardPolicy):
    def __init__(self, *args, **kwargs):
        super(CustomPolicy, self).__init__(*args, **kwargs,
                                           net_arch=[dict(pi=[128, 64],
                                                          vf=[128, 64])], 
                                           feature_extraction="mlp")

def run_model(algorithm, training_timesteps, testing_timesteps, training_iterations, testing_iterations, learning_rate, batch_size):
	columns = ['Mean Rewards', 'Standard deviation'] 
	df = pd.DataFrame(columns=columns)
	if (algorithm == "PPO2"):
	    from stable_baselines.common.policies import MlpPolicy
	    model = PPO2(MlpPolicy, env, verbose=1, learning_rate=learning_rate, tensorboard_log = "./{}_rcrs_tensorboard/".format(hostname), n_steps = batch_size)
	else:
	    from stable_baselines.deepq.policies import MlpPolicy
	    model = DQN(MlpPolicy, env, verbose=1, learning_rate=learning_rate, tensorboard_log = "./{}_rcrs_tensorboard/".format(hostname),  batch_size = batch_size)
	for k in range(training_iterations):
		# Train the agent
		model.learn(total_timesteps=int(training_timesteps))
		# Saving the model 
		model.save("{}_{}_{}_{}".format("rcrs_wgts", k, algorithm, hostname))
		subprocess.Popen(path_for_kill_file, shell=True)

	for j in range(testing_iterations):
	    # Load the trained agent
	    if (algorithm == "PPO2"):
	    	model = PPO2.load("{}_{}_{}_{}".format("rcrs_wgts", j, algorithm, hostname))
	    else:
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
	    df.to_csv("{}_{}_{}".format(algorithm, hostname, "MeanAndStdReward.csv", sep=',',index=True))
	    
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
