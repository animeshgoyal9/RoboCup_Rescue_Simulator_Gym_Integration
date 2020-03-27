#!/lusr/bin/bash

# This script runs the simspark soccer simulator and an agent 

#export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/projects/agents2/villasim/rcss3D/server/lib/simspark

is_locked()
{
  #semCheck=`find /tmp/simspark-condor* -type f -mmin -21 -exec ls -1t "{}" + 2> /dev/null`
  semCheck=`find /tmp/simspark-condor* -type f -mmin -21 2> /dev/null`
  for s in $semCheck
  do
    semPID=${s##*_}
    # Check that we have a valid pid for the process holding the semaphore file
    # and if so check if that process is still running
    if ! [ "$semPID" -eq "$semPID" &> /dev/null ] || ps -p $semPID &> /dev/null
    then
      return 0
    fi
  done
  
  return 1
}

# Flag that we're running an optimization
export SPARK_OPT=true
export SPARK_SAVE_LOG=false


SCRIPT=$(readlink -f $0)
SCRIPT_DIR=`dirname $SCRIPT`

OUTPUT_FILE=$1
OPPONENT_AGENT_DIR=$2
AGENT_SIDE=$3
CONDOR_CONTENTS=$4

PROCESS_DIR="${OUTPUT_FILE%\/results*}/process"

SC_DIR="${OUTPUT_FILE%\/results*}/sc"
OUTPUT_SERVER="$SC_DIR/`basename $OUTPUT_FILE`.server"

echo -n "It is: "
date
echo -n "and I am on: "
hostname

HOST=`hostname`

rerunFile="$PROCESS_DIR/__rerun_`basename $OUTPUT_FILE`"
rerunFile=${rerunFile%\.*}

# Still need semaphors as not all agents support choosing which port to connect on

if is_locked
then 
  echo "Process already running on machine...mark for rerun and exit"
  # Create marker file so that we rerun process
  touch $rerunFile
  # Hold this place for a bit so hopefully other processes will be assigned
  # a different spot on a different machine
  #sleep 60 

  while is_locked
  do
    sleep 5
    # Comment out exit below for faster throughput at the expense of lots of 
    # idle jobs running 
    exit
  done
  exit
fi  

(umask a+w; touch /tmp/simspark-condor)

# Second level stronger semaphore
sleep 1
semFile="/tmp/simspark-condor_`date +%s`_`basename $OUTPUT_FILE`_$$"
mv /tmp/simspark-condor $semFile

start_time=`date +%s`

sleep 1
if [ ! -f $semFile ]
then 
  echo "Another process just started running on machine...mark for rerun and exit"
  # Create marker file so that we rerun process
  touch $rerunFile
  #aFile="$PROCESS_DIR/__SEM_`basename $OUTPUT_FILE`"
  #aFile=${aFile%\.*}
  #touch $aFile
  # Hold this place for a bit so hopefully other processes will be assigned
  # a different spot on a different machine
  #sleep 60 

  while is_locked
  do
    sleep 5
    # Comment out exit below for faster throughput at the expense of lots of 
    # idle jobs running
    exit
  done
  exit
fi 


# Create marker file that process is running
#runFile="$PROCESS_DIR/__running_`hostname`_`basename $OUTPUT_FILE`"
runFile="$PROCESS_DIR/__running_`basename $OUTPUT_FILE`"
runFile=${runFile%\.*}
touch $runFile

# Can't currently randomize ports as some agents don't allow for port selection
# Set the agent and monitor port randomly, to allow for multiple agents per machine
# Note: $RANDOM returns a value from 0 to 32767, ports <= 1024 are reserved for root 
#export SPARK_SERVERPORT=$[$RANDOM + 1025]
#export SPARK_AGENTPORT=$[$RANDOM + 1025]
#echo "Agent port: $SPARK_AGENTPORT, Monitor port: $SPARK_SERVERPORT"

killall -9 rcssserver3d

# Start server
cd /tmp
/projects/agents2/villasim/rcss3D/server/bin/rcssserver3d &
# For recording output of server for self collisions
#/projects/agents2/villasim/rcss3D/server/bin/rcssserver3d 1> $OUTPUT_SERVER &

PID=$!

sleep 5

#cd $SCRIPT_DIR                                                                
#./gameMonitor.py $OUTPUT_FILE $HOST &


if [ "$AGENT_SIDE" == "right" ]
then 
  cd $OPPONENT_AGENT_DIR
  ./start.sh 127.0.0.1
  sleep 1
fi

# TODO: temporary fix!! get rid of it - because relative path dependency of skills
cd $SCRIPT_DIR
./start-stats.sh $HOST $OUTPUT_FILE
#./competition-start-stats.sh $HOST $OUTPUT_FILE

if [ "$AGENT_SIDE" != "right" ] # Should be left
then 
  sleep 1
  cd $OPPONENT_AGENT_DIR
  ./start.sh 127.0.0.1
fi


sleep 10
perl $SCRIPT_DIR/kickoff.pl # kickoff to start 


while [ ! -f $OUTPUT_FILE ] && [ `expr \`date +%s\` - $start_time` -lt 1245 ]
do 
  if ! kill -0 $PID &> /dev/null
  then
      echo "Server died so shutting down."
      break
  fi 
  sleep 5
done 

if [ ! -f $OUTPUT_FILE ]
then
  #timeoutFile="$PROCESS_DIR/__timeout_`basename $OUTPUT_FILE`"
  #timeoutFile=${timeoutFile%\.*}
  #touch $timeoutFile
  rm $runFile
  touch $rerunFile
  echo "Timed out while waiting for game to complete, current wait time is `expr \`date +%s\` - $start_time` seconds."
else
  #timeFile="$PROCESS_DIR/`basename $OUTPUT_FILE`"
  #timeFile=${timeFile%\.*}
  #timeFile=$timeFile"_"
  #timeFile="$timeFile$total_wait_time"
  #touch $timeFile
  echo "Completed with a wait time of `expr \`date +%s\` - $start_time` seconds."
fi

sleep 1

echo "Killing agent team"
cd $SCRIPT_DIR/..
./kill.sh

echo "Killing opponent team"
cd $OPPONENT_AGENT_DIR
./kill.sh

echo "Killing Simulator"
kill -s 2 $PID

sleep 2
echo "Finished"

find /tmp/simspark-condor* -type f ! -newer $semFile -exec rm -f {} \;
