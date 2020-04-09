#!/bin/bash

# rmdconfuser - remove dconf user

# xvfb-run --auto-servernum --server-num=1 python testing.py $1 $2 $3 $4 $5 $6 $7
# unset http_proxy; unset https_proxy; xvfb-run -a python testing.py $1 $2 $3 $4 $5 $6 $7

# . ./xvfb_helper.sh 

# create_xvfb

# echo $DISPLAY
# echo $XVFB_PID
# ps -efw | grep Xvfb

xvfb-run -a python testing.py $1 $2 $3 $4 $5 $6 $7 $8 $9 $10
