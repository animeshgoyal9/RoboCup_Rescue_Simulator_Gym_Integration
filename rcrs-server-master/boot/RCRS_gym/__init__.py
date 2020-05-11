import logging
from gym.envs.registration import register

logger = logging.getLogger(__name__)

register(
    id='RCRS-v2',
    entry_point='RCRS_gym.envs:RCRSenv',
)

# register(
# 	id='RCRS-v3',
# 	entry_point='RCRS_gym.envs:RCRS_env_rllib')

# register(
#     id='RCRS-v2',
#     entry_point='RCRS_gym.envs:RCRSVecEnv',
# )
