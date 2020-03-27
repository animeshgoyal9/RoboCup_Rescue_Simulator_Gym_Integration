#!/usr/bin/env python

import subprocess, sys, argparse, socket, os, time
import gym
from subprocess import *

import gym, time
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
# from stable_baselines.deepq.policies import FeedForwardPolicy
from stable_baselines.bench import Monitor
from stable_baselines.results_plotter import load_results, ts2xy
from stable_baselines.ddpg import AdaptiveParamNoiseSpec

string_for_launch_file = "python3" + " " + sys.path[0] + "/launch_file.py"
path_for_kill_file = os.path.join(sys.path[0], "kill.sh")
print("Check 1-----------------------------------------------------------")
# subprocess.Popen(["xterm", "-e", string_for_launch_file])
subprocess.Popen([sys.path[0] + "/launch_file.py"])
time.sleep(20)
print("Check 2-----------------------------------------------------------")
# subprocess.Popen(path_for_kill_file, shell=True)
# subprocess.Popen([sys.path[0], "/kill.sh"])
# time.sleep(2)
# print("Check 3-----------------------------------------------------------")
# # subprocess.Popen(["xterm", "-e", string_for_launch_file])
# subprocess.Popen([sys.path[0] + "/launch_file.py"])
# time.sleep(30)
# print("Check 4-----------------------------------------------------------")
# # subprocess.Popen(path_for_kill_file, shell=True)
# subprocess.Popen([sys.path[0], "/kill.sh"])
# time.sleep(2)
# print("Check 5-----------------------------------------------------------")




# subprocess.Popen(["xterm", "-e", string_for_launch_file])
# time.sleep(30)
# print("Check 4-----------------------------------------------------------")
# subprocess.Popen(path_for_kill_file, shell=True)
# print("Check 5-----------------------------------------------------------")
# print("this is working--------------------------------------------")
# path_for_start_file = os.path.join(sys.path[0], "start-comprun.sh -m ../maps/gml/berlin/map -c ../maps/gml/berlin/config")
# path_for_launch_file = os.path.join(sys.path[0], "../../rcrs-adf-sample/launch.sh '-all'")
# path_for_kill_file = os.path.join(sys.path[0], "kill.sh")

# subprocess.Popen(path_for_start_file, shell=True)
# time.sleep(5)
# subprocess.Popen(path_for_launch_file, shell=True)
# time.sleep(5)
# print("Process is complete---------------------------------------")
# subprocess.Popen(path_for_kill_file, shell=True)



# print("Gym is working")
# def run(algorithm):
# 	print(algorithm)
# 	# columns = ['Mean Rewards', 'Standard deviation'] 
# 	# df = pd.DataFrame(columns=columns)
# 	# df.to_csv("{}_{}_{}".format(algorithm, hostname, "MeanAndStdReward.csv", sep=',',index=True))

# def main():
# 	parser = argparse.ArgumentParser()
# 	parser.add_argument("algorithm", help = 'Which algorithm are you using', type= str)
# 	args = parser.parse_args()
# 	run(args.algorithm)

# if __name__ == '__main__':
# 	main()