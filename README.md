# Multi-Agent Deep Reinforcement learning for RoboCup Rescue Simulator

This project marks the application of Deep Reinforcement learning to RoboCup Rescue Simulator which is a perfect scenario for multiple agents coordinating with each other to achieve certain goals. 


(Linux) Instructions to download, build and run the RoboCup Rescue Simulator (RCRS) integrated with OpenAI Gym. OpenAI Gym is a toolkit for developing and comparing reinforcement learning algorithms.

## 1. Software Pre-Requisites

* Git
* Gradle 3.4+
* OpenJDK Java 11+
* Python 3.5+
* Openai Gym
* Stable Baselines
* Tensorboard 1.14 (Note: Stable baselines is not compatible with Tensorboard 2.0) 

## 2. Download project from GitHub

```bash

$ git clone https://github.com/animeshgoyal9/RoboCup-Gym.git

``` 

## 3. Compile

Open a terminal window, navigate to the `rcrs-server-master` root directory and compile 

```bash 

$ ./gradlew clean

$ ./gradlew completeBuild

```

Open another terminal window, navigate to the `rcrs-adf-sample` root directory and compile 

```bash 

$ sh clean.sh

$ sh compile.sh

```

Close the second terminal

## 4. Execute

On the first terminal, navigate to the `boot` folder in  `rcrs-server-master` directory and run the following python file 

```bash

$ testing.py

``` 

## 5. Visualization

To visualize the reward over time, losses etc, you can use tensorboard. 

Open a new terminal window and run the following bash command

```bash

$ tensorboard --logdir ./ppo2_RoboCupGym_tensorboard/

``` 

## 6. Code Structure

- `./rcrs-server-master/`: folder where simulation server resides
- `./rcrs-adf-sample/`   : folder where simulation client resides
- `./PyRCRSClient/`      : gRPC python and proto files for client side 
- `./JavaRCRSServer/`          : gRPC java and proto files for server side
- `./rcrs-server-master/boot/testing.py`: contains code for applying Deep Reinforcement learning to RCRS
- `./rcrs-server-master/boot/RCRS_gym/`: folder containing gym integration for RCRS
- `./rcrs-server-master/maps/`: maps that can be run in the simulation server
   - `..maps/gml/test/map` : default map 
   - `..maps/gml/test/config`: configuration file associated with the map

## 7. Results 


### PPO 
| <img src="/Images/GIFs/NoTrained.gif" width = "250"/>  |  <img src="/Images/GIFs/PartiallyTrained.gif" width = "250px" /> | <img src="/Images/GIFs/FullyTrained.gif" width="250px" /> |
|:---:|:---:|:---:|
| 5 episodes | 150 episodes | 400 episodes |


### DQN

| <img src="/Images/GIFs/DQN_Episode 5.gif" width = "250" />   | <img src="/Images/GIFs/DQN_Episode 150.gif" width = "250px" /> | <img src="/Images/GIFs/DQN_Episode 250.gif" width="250px" /> |
|:---:|:---:|:---:|
| 5 episodes| 150 episodes | 400 episodes |


##### Learning Curve

<img src="/Images/Graphs/LearningCurve_SmallMap.png" />

<img src="/Images/Graphs/LearningCurve_BigMap.png" />

##### Hyperparameters

<img src="/Images/Graphs/Hyperparameters.png" />




