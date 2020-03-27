import numpy as np

import gym
from gym import spaces

# class Rescaleaction(gym.ActionWrapper):

# 	def __init__(self, env, action_set_list):
# 		super(Rescaleaction, self).__init__(env)
# 		# self.action_space = spaces.Discrete(len(action_set_list))
# 		# self.action_space = spaces.MultiDiscrete([len(action_set_list), len(action_set_list)])
# 		self.i = 0 

# 	def action(self, actions):
# 		if (self.i==0):
# 			action1 = actions
# 			self.i = 1
# 		else:
# 			action2 = actions
# 			self.i = 0 
# 		return np.asarray([action1, action2]) 


# def run_adf(bid):
#     global flag
#     # NOTE(gRPC Python Team): .close() is possible on a channel and should be
#     # used in circumstances in which the with statement does not fit the needs
#     # of the code.
    
#     with grpc.insecure_channel('localhost:3401') as channel:
#         stub = AgentInfo_pb2_grpc.AnimFireChalAgentStub(channel)
#     # # print for PPO2
#         # print("Current action_1 for 210552869: ", action_set_list[bid[0]])
#         # print("Current action_2 for 1962675462: ", action_set_list[bid[1]])
#         # print("-----------------------------------")
#     # print for DQN
#         # print("Current action_1 for 210552869: ", action_set_list[bid])
#         # print("Current action_2 for 1962675462: ", action_set_list[len(action_set_list)-1-bid])
#         # print("-----------------------------------")
#         response = stub.getAgentInfo(AgentInfo_pb2.ActionInfo(actions = [
#             AgentInfo_pb2.Action(agent_id = 210552869, building_id=action_set_list[bid[0]]), AgentInfo_pb2.Action(agent_id = 1962675462, building_id=action_set_list[bid[1]])]))
#             # AgentInfo_pb2.Action(agent_id = 2090075220, building_id=action_set_list[bid[0]]), AgentInfo_pb2.Action(agent_id = 1618773504, building_id=action_set_list[bid[1]])]))
#             # AgentInfo_pb2.Action(agent_id = 2090075220, building_id=action_set_list[bid]), AgentInfo_pb2.Action(agent_id = 1618773504, building_id=action_set_list[1425-bid])]))
#             # AgentInfo_pb2.Action(agent_id = 210552869, building_id=action_set_list[bid]), AgentInfo_pb2.Action(agent_id = 1962675462, building_id=action_set_list[len(action_set_list)-1-bid])]))
#     agent_state_info = []

#     for i in response.agents:
#         agent_state_info.append(i.agent_id)
#         agent_state_info.append(i.x)
#         agent_state_info.append(i.y)
#         agent_state_info.append(i.water)
#         agent_state_info.append(i.hp)
#         agent_state_info.append(i.idle)
#     return agent_state_info