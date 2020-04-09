# Problem 1 - No two simulations are running in parallel

## The following script will run 1 simulation on the condor cluster

### To start a condor job on the cluster, navigate to the boot folder in rcrs-server-master and run the following command

$ condor_submit testing_condor.sub

### To analyze the progress of the work, run the following command

$ condor_q

### In case, the jobs go in idle condition, run the following command to analyze the problem

$ condor_q  -better-analyze

## To run multiple simulations, open the `testing_condor.sub` file and uncomment the last lines `arguments` and `queue` 