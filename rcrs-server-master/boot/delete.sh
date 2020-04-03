#! /bin/bash

#now=$(date +"%T")
#echo "Current time : $now"

#PID=`sh -c "echo $$; xvfb-run -a python testing.py PPO2 3000 3000 30 30 0.0045 64 20001 20002 20003"`
#echo $PID 


now=$(date +"%T")
echo "Current time : $now"

pid=$(pidof xterm $!)
echo "Current pid : $pid"

totalpid="$pid-$now"
echo "Total pid : $totalpid"

echo "First pid : $pid$1"

#echo $LOGDIR
#LOGDIR="$LOGDIR-$HOSTNAME"
