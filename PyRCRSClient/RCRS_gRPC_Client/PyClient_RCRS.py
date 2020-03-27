from __future__ import print_function
import logging
import time

import grpc

import AgentInfo_pb2
import AgentInfo_pb2_grpc
import BuildingInfo_pb2
import BuildingInfo_pb2_grpc


def run_adf(bid):
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    with grpc.insecure_channel('localhost:9090') as channel:
        stub = AgentInfo_pb2_grpc.AnimFireChalAgentStub(channel)
        response = stub.getAgentInfo(AgentInfo_pb2.ActionInfo(actions = [
        	AgentInfo_pb2.Action(agent_id = 210552869, building_id=bid), AgentInfo_pb2.Action(agent_id = 2, building_id=2)]))
    # print(response.agents)
    agent_state_info = []

    for i in response.agents:
        agent_state_info.append(i.agent_id)
        agent_state_info.append(i.x)
        agent_state_info.append(i.y)
        agent_state_info.append(i.water)
        agent_state_info.append(i.hp)
    return agent_state_info

def run_reward():
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    with grpc.insecure_channel('localhost:2212') as channel:
        stub = BuildingInfo_pb2_grpc.AnimFireChalBuildingStub(channel)
        # response = stub.getBuildingInfo(BuildingInfo_pb2.BuildingInfo(buildings = [
        	# BuildingInfo_pb2.Building(fieryness = 1, temperature=1, building_id = 1), BuildingInfo_pb2.Building(fieryness = 2, temperature=2, building_id = 2)]))
        response_reward = stub.getRewards(BuildingInfo_pb2.Empty())
    print(response_reward.reward)

def run_server():
    # NOTE(gRPC Python Team): .close() is possible on a channel and should be
    # used in circumstances in which the with statement does not fit the needs
    # of the code.
    with grpc.insecure_channel('localhost:4007') as channel:
        stub = BuildingInfo_pb2_grpc.AnimFireChalBuildingStub(channel)
        # response = stub.getBuildingInfo(BuildingInfo_pb2.BuildingInfo(buildings = [
            # BuildingInfo_pb2.Building(fieryness = 1, temperature=1, building_id = 1), BuildingInfo_pb2.Building(fieryness = 2, temperature=2, building_id = 2)]))
        response = stub.getBuildingInfo(BuildingInfo_pb2.Empty())
    building_state_info = []

    for i in response.buildings:
        building_state_info.append(i.fieryness)
        building_state_info.append(i.temperature)
        building_state_info.append(i.building_id)
    # print(fieryness_values
    return building_state_info
    print("*******************************************")

if __name__ == '__main__':
    logging.basicConfig()
    state_info = []
    while True:
        run_adf(253)
        run_reward()
        run_server()
        state_info.append(run_server())
        # state_info.append(run_adf(249))
        flat_list = [item for sublist in state_info for item in sublist]
        print(flat_list)
        time.sleep(4)
        run_adf(298)
        run_reward()
        run_server()
        state_info.append(run_server())
        state_info.append(run_adf(298))
        flat_list = [item for sublist in state_info for item in sublist]
        print(flat_list)
        time.sleep(4)
        run_adf(250)
        run_reward()
        run_server()
        state_info.append(run_server())
        state_info.append(run_adf(250))
        flat_list = [item for sublist in state_info for item in sublist]
        print(flat_list)
        time.sleep(4)
        run_adf(254)
        run_reward()
        run_server()
        state_info.append(run_server())
        state_info.append(run_adf(254))
        flat_list = [item for sublist in state_info for item in sublist]
        print(flat_list)
        time.sleep(4)


