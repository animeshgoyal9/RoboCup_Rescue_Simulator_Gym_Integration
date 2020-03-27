# path to script that runs simulator and agent
$run_path = "/u/yifengz/workspace/3dsim/agents/nao-agent/stats/condor-run.sh"

# path to directory where opponent binaries are
$opponents_dir = "/projects/agents2/villasim/opponents3D/"

# Comma delimited set of opponents to play against

# All opponents (ricaastmt doesn't currently run on condor)
$opponents = ["BahiaRT", "FCPortugal", "hfutengine", "ITAndroids", "magmaOffenburg", "UTAustinVilla", "WRIGHTOcean"]

# whether or not to store process data in the process directory.  This includes
# log, error, and output from the simulator and agents, and can be really, really big.
# WARNING: only enable this if you're debugging and are running very few agents (approximately 1)
#   and very few iterations (also, approximately 1).  Otherwise, you'll write gigabytes of
#   data in a few seconds, run out of quota, and be generally unhappy.
$logEnabled = true
