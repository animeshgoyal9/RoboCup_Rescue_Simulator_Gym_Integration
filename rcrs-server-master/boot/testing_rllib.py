from __future__ import absolute_import
from __future__ import division
from __future__ import print_function


import argparse
import gym
import ray
from ray import tune
from ray.rllib.env.multi_agent_env import MultiAgentEnv
from ray.tune.registry import register_env
from ray.rllib.agents.a3c.a3c import A3CTrainer
from ray.rllib.agents.a3c.a3c_tf_policy import A3CTFPolicy
from ray.tune.logger import pretty_print

parser = argparse.ArgumentParser()

parser.add_argument('--num-agents', type=int, default=1)
parser.add_argument('--num-policies', type=int, default=1)
parser.add_argument('--num-iters', type=int, default=100000)
parser.add_argument('--simple', action='store_true')

num_agents = 1

class RCRS_rllib(MultiAgentEnv):
	def __init__(self, num_agents=1):
		import RCRS_gym
		self.env = gym.make('RCRS-v2')
		self.action_space = gym.spaces.Discrete(self.env.action_space.nvec[1])
		self.observation_space = self.env.observation_space
		# gym.spaces.Box(
		#     low=self.env.observation_space.low,
		#     high=self.env.observation_space.high,
		#     dtype=self.env.observation_space.dtype)
		self.num_agents = num_agents

	def reset(self):
	    original_obs = self.env.reset()
	    obs = {}
	    for x in range(self.num_agents):
	      if self.num_agents > 1:
	        obs['agent_%d' % x] = original_obs[x]
	      else:
	        obs['agent_%d' % x] = original_obs
	    return obs

	def step(self, action_dict):
	    actions = []
	    for key, value in sorted(action_dict.items()):
	    	actions.append(value)
	    o, r, d, i = self.env.step(actions)
	    rewards = {}
	    obs = {}
	    infos = {}
	    for pos, key in enumerate(sorted(action_dict.keys())):
	    	infos[key] = i
	    	if self.num_agents > 1:
	        	rewards[key] = r[pos]
	        	obs[key] = o[pos]
	      	else:
	        	rewards[key] = r
	        	obs[key] = o
	    dones = {'__all__': d}
	    return obs, rewards, dones, infos

if __name__ == '__main__':
	args = parser.parse_args()
	ray.init(num_cpus=1)
	# Simple environment with `num_agents` independent players
	# register_env('gfootball', lambda _: RllibGFootball(args.num_agents))
	register_env('rcrsgymrllib', lambda _: RCRS_rllib(1))
	single_env = RCRS_rllib(1)
	obs_space = single_env.observation_space
	act_space = single_env.action_space
	
	def gen_policy(_):
		return (None, obs_space, act_space, {})
	# Setup PPO with an ensemble of `num_policies` different policies
	policies = {
	  'policy_{}'.format(i): gen_policy(i) for i in range(1)
	}
	
	
	policy_ids = list(policies.keys())
	
	# tune.run(
	#   'PPO',
	#   stop={'training_iteration': 10000},
	#   checkpoint_freq=50,
	#   config={
	#       'env': 'rcrsgymrllib',
	#       'lambda': 0.95,
	#       'kl_coeff': 0.2,
	#       'clip_rewards': False,
	#       'vf_clip_param': 10.0,
	#       'entropy_coeff': 0.01,
	#       'train_batch_size': 2000,
	#       'sample_batch_size': 100,
	#       'sgd_minibatch_size': 500,
	#       'num_sgd_iter': 10,
	#       'num_workers': 1,
	#       'num_envs_per_worker': 1,
	#       'batch_mode': 'truncate_episodes',
	#       'observation_filter': 'NoFilter',
	#       'vf_share_layers': 'true',
	#       'num_gpus': 0,
	#       'lr': 2.5e-4,
	#       'log_level': 'DEBUG',
	#       'simple_optimizer': args.simple,
	#       'multiagent': {
	#           'policies': policies,
	#           'policy_mapping_fn': tune.function(
	#               lambda agent_id: random.choice(["policy_0"])),
	#       },
	#   },
 #  )

	trainer = A3CTrainer(env="rcrsgymrllib", 
		config={
	"multiagent": {
	    "policies": policies,
	    "policy_mapping_fn": tune.function(
	          lambda agent_id: random.choice(["policy_0"])),  # Traffic lights are always controlled by this policy
	},
	"lr": 0.0001,
	})
    
	while True:
	    result = trainer.train()
	    print(pretty_print(result))















