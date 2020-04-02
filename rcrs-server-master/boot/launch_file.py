#!/usr/bin/env python

import subprocess
import time, os 
import signal, sys
import socket
import argparse

# path_for_start_file = os.path.join(sys.path[0], "start-comprun.sh -bp 5008 -rp 5011")
# path_for_start_file = os.path.join(sys.path[0], "start-comprun.sh -m ../maps/gml/berlin/map -c ../maps/gml/berlin/config -bp 20006 -rp 20007")
# path_for_launch_file = os.path.join(sys.path[0], "../../rcrs-adf-sample/launch.sh '-all'")

# subprocess.Popen(path_for_start_file, shell=True)
# time.sleep(10)
# subprocess.Popen(path_for_launch_file, shell=True)
# time.sleep(5000000)	

def launch_everything(building_port, reward_port, agent_port):
	path_for_start_file = os.path.join(sys.path[0], "start-comprun.sh -m ../maps/gml/berlin/map -c ../maps/gml/berlin/config -bp {} -rp {}".format(building_port,reward_port))
	path_for_launch_file = os.path.join(sys.path[0], "../../rcrs-adf-sample/launch.sh '-all'")
	subprocess.Popen(path_for_start_file, shell=True)
	time.sleep(10)
	subprocess.Popen(path_for_launch_file, shell=True)
	time.sleep(5000000)	 


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("building_port", help = 'What is the building_port', type= int)
	parser.add_argument("reward_port", help = "What is the reward_port?", type=int)
	parser.add_argument("agent_port", help = "What is the agent_port?", type=int)
	args = parser.parse_args()
	launch_everything(args.building_port, args.reward_port, args.agent_port)

if __name__ == '__main__':
	main()