#!/bin/bash

now=$(date +"%T")
echo "Current time : $now"

pid=$(pidof xterm $!)
echo "Current pid : $pid"

# totalpid="$pid-$now"
# echo "Total pid : $totalpid"

PidArray=($pid)

# for ((i=0; i<${#PidArray[@]}; ++i)); do
#     echo "pid $i:${PidArray[$i]}"
# done

echo "Last Pid: ${PidArray[-1]}"

kill -9 ${PidArray[-1]}

#pkill -9 xterm 