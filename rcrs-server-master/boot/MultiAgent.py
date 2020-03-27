
from __future__ import print_function
import logging

import grpc
import AgentInfo_pb2
import AgentInfo_pb2_grpc
import BuildingInfo_pb2
import BuildingInfo_pb2_grpc

import gym
from gym import error, spaces, utils
from gym.spaces import Discrete, Tuple
from gym.utils import seeding

import logging, random
import socket, pickle, json, subprocess, ast
import numpy as np
import threading
import time, math, os
import signal, sys
import threading
from subprocess import *
from numpy import inf

from ray.rllib.models import ModelCatalog
from ray.rllib.models.tf.tf_modelv2 import TFModelV2
from ray.rllib.models.tf.fcnet_v2 import FullyConnectedNetwork
from gym.spaces import Discrete, Box

import ray
from ray import tune
from ray.rllib.utils import try_import_tf
from ray.rllib.agents.ppo import PPOTrainer
from ray.tune import grid_search
from ray.rllib.agents.pg import PGTrainer
from ray.rllib.agents.dqn.dqn_policy import DQNTFPolicy
from ray.rllib.env.multi_agent_env import MultiAgentEnv
from ray.tune.registry import register_env
from ray.tune.logger import pretty_print
from RCRS_gym.envs import RCRS_env 
import ray.rllib.agents.ppo as ppo
from ray.rllib.agents.ppo import PPOTrainer

tf = try_import_tf()


import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning) 

MAX_TIMESTEP = 100

action_set_list = np.array([255, 960, 905, 934, 935, 936, 937, 298, 938, 939, 940, 941, 942, 943, 944, 945, 946, 947, 948, 949, 
                    950, 951, 247, 952, 248, 953, 249, 954, 250, 955, 251, 956, 957, 253, 958, 254, 959], dtype = object)

class RCRSMultienv(MultiAgentEnv):  
    current_action = 0
    def __init__(self):
        self.action_space = spaces.Discrete(37)
        low = np.array([-inf]*86)
        high = np.array([inf]*86)
        self.observation_space = spaces.Box(low, high, dtype=np.float32, shape=None)
        self.fb_1 = "fb_1"
        self.fb_2 = "fb_2"
        self.curr_episode = 0

    def reset(self):
        
        subprocess.call(['gnome-terminal', '-e', "python3 /u/animesh9/Documents/RoboCup-gRPC/rcrs-server-master/boot/launch_file.py"])
        time.sleep(11)

        self.curr_episode = 0
        
        reset_action = {self.fb_1: 0, self.fb_2: 0}
        reset = []
        reset.append(run_server())
        reset.append(run_adf(reset_action))

        flat_list_reset = [item for sublist in reset for item in sublist]

        self.state = flat_list_reset
        return {self.fb_1: np.array(self.state), self.fb_2: np.array(self.state)} 

    def step(self, action_dict):

        self.curr_episode += 1
        if (self.curr_episode <= (MAX_TIMESTEP - 1)):
            self.reward = 0
        else:
            self.reward = run_reward()
        
        actions_1 = action_dict[self.fb_1]
        actions_2 = action_dict[self.fb_2]
        actions_final = {self.fb_1: actions_1, self.fb_2: actions_2}
        state_info = []
        state_info.append(run_server())
        state_info.append(run_adf(actions_final))

        flat_list = [item for sublist in state_info for item in sublist]
        
        self.state = flat_list
        done = {
            "__all__": bool(self.curr_episode == MAX_TIMESTEP),
        }
        if done['__all__'] == True:
            subprocess.Popen("/u/animesh9/Documents/RoboCup-gRPC/rcrs-server-master/boot/kill.sh", shell=True)
        time.sleep(0.14)
        
        obs = {self.fb_1: np.array(self.state), self.fb_2: np.array(self.state)}
        rew = {self.fb_1: self.reward, self.fb_2: self.reward}
        return obs, rew, done , {}

def run_adf(bid):
    global flag
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    
    with grpc.insecure_channel('localhost:3400') as channel:
        stub = AgentInfo_pb2_grpc.AnimFireChalAgentStub(channel)
        print("Current action_1 for 210552869: ", action_set_list[list(bid.values())[0]])
        print("Current action_2 for 1962675462: ", action_set_list[list(bid.values())[1]])
        print("-----------------------------------")
        response = stub.getAgentInfo(AgentInfo_pb2.ActionInfo(actions = [
            AgentInfo_pb2.Action(agent_id = 210552869, building_id=action_set_list[list(bid.values())[0]]), AgentInfo_pb2.Action(agent_id = 1962675462, building_id=action_set_list[list(bid.values())[1]])]))
    agent_state_info = []

    for i in response.agents:
        agent_state_info.append(i.agent_id)
        agent_state_info.append(i.x)
        agent_state_info.append(i.y)
        agent_state_info.append(i.water)
        agent_state_info.append(i.hp)
        agent_state_info.append(i.idle)
    return agent_state_info

def run_reward():
    with grpc.insecure_channel('localhost:2212') as channel:
        stub = BuildingInfo_pb2_grpc.AnimFireChalBuildingStub(channel)
        response_reward = stub.getRewards(BuildingInfo_pb2.Empty())
    return response_reward.reward

def run_server():
    with grpc.insecure_channel('localhost:4007') as channel:
        stub = BuildingInfo_pb2_grpc.AnimFireChalBuildingStub(channel)
        response = stub.getBuildingInfo(BuildingInfo_pb2.Empty())
    building_state_info = []

    for i in response.buildings:
        building_state_info.append(i.fieryness)
        building_state_info.append(i.temperature)
        # building_state_info.append(i.building_id)
    return building_state_info



if __name__ == "__main__":
    ray.init()
    register_env("rcrs_env", lambda config: RCRSMultienv())
    single_env = gym.make("RCRS-v2")
    obs_space = single_env.observation_space
    act_space = single_env.action_space

    # tune.run(
    #     "PPO",
    #     stop={"training_iteration": 1},
    #     # stop={"timesteps_total": 1}, 
    #     # checkpoint_freq= 1,
    #     config={
    #         "env": "rcrs_env",
    #         "num_workers": 1,
    #         "multiagent": {
    #             "policies": {
    #                 "fb_1": (None, obs_space, act_space, {}),
    #                 "fb_2": (None, obs_space, act_space, {}),
    #             },
    #             "policy_mapping_fn":
    #                 lambda agent_id:
    #                     "fb_1"
    #                     if agent_id.startswith("fb_1_")
    #                     else random.choice(["fb_1", "fb_2"])
    #         },
    #     },
    # )

    trainer = PPOTrainer(env="rcrs_env", config={
            "env": "rcrs_env",
            "num_workers": 1,
            "multiagent": {
                "policies": {
                    "fb_1": (None, obs_space, act_space, {}),
                    "fb_2": (None, obs_space, act_space, {}),
                },
                "policy_mapping_fn":
                    lambda agent_id:
                        "fb_1"
                        if agent_id.startswith("fb_1_")
                        else random.choice(["fb_1", "fb_2"])
            },
        },
    )
    
    for i in range(2):
        result = trainer.train()
        print(pretty_print(result))
        if i % 1 == 0:
            checkpoint = trainer.save()
            print("checkpoint saved at", checkpoint)
    statess = trainer.save()
    trainer.stop()