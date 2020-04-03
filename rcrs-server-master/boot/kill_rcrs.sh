#! /bin/bash

now=$(date +"%T")
echo "Current time : $now"

pid=$(pidof xterm $!)
echo "Current pid : $pid"

totalpid="$pid-$now"
echo "Total pid : $totalpid"

pids=( $pid )
echo "**************************************"
echo "**************Killing Process*********"
echo "**************************************"
echo "First pid: ${pids[0]}"
echo "Second pid: ${pids[-1]}"

kill -9 ${pids[-1]}
#pkill -9 xterm 


# echo "testing.py Executed at `date` with the PID of: $!" >> my_PIDs.log
# exit 0"

# ps axf | grep "xterm" & $! | grep -v grep | awk '{print "kill -9 " $1}'
