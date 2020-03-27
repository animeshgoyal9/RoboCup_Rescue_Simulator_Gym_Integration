import logging
from gym.envs.registration import register

logger = logging.getLogger(__name__)

register(
    id='RCRS-v2',
    entry_point='RCRS_gym.envs:RCRSenv',
)

# register(
#     id='RCRS-v2',
#     entry_point='RCRS_gym.envs:RCRSVecEnv',
# )
