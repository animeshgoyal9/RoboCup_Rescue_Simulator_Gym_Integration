#! /bin/bash
# rmdconfuser - remove dconf user

# xvfb-run --auto-servernum --server-num=1 python testing.py $1 $2 $3 $4 $5 $6 $7
xvfb-run -a python testing.py $1 $2 $3 $4 $5 $6 $7
# xvfb-run python testing.py $1 $2 $3 $4 $5 $6 $7
# python testing.py $1 $2 $3 $4 $5 $6 $7