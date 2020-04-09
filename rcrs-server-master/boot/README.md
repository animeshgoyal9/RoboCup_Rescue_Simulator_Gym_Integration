#### Problem 1: Run parallel simulations on local machine

##### After compiling both 'rcrs-server-master' and 'rcrs-adf-sample' modules, please navigate to 'boot' folder in the 'rcrs-server-master' directory and run the following command:
 
```$ xvfb-run -a python testing.py PPO2 3000 3000 30 30 0.005 64 30000 30001 30002```

##### Here, the arguments are in the following order: 

```$ xvfb-run -a python testing.py [algorithm] [training timesteps] [testing timesteps] [training iterations] [testing iterations] [learning rate] [batch size] [building info port] [reward port] [agent info port]```


#### Problem 2 - Run parallel simulations on condor

##### The following script will run 1 simulation on the condor cluster

##### To start a condor job, navigate to the boot folder in rcrs-server-master and run the following command

```$ condor_submit testing_condor.sub```

##### To analyze the progress of the work, run the following command

```$ condor_q```

##### In case, the jobs go in idle condition, run the following command to analyze the problem

```$ condor_q  -better-analyze```

##### To run multiple simulations, open the `testing_condor.sub` file and uncomment the last lines `arguments` and `queue`. 




