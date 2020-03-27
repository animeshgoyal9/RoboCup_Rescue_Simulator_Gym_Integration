#!/usr/bin/env ruby

# This script automates runs of games of a sim-3d robosoccer team against a
# pool of other teams.
#
# Usage: condor_run.rb <experiment_path> <numGames> [config_file_path]
#
#  * <experiment_path>:  where to store results and logs of the experiment.  
#                        will be created if it doesn't exist.
#  * <num_games>:        total number of games to play between each opponent 
#                        and agent.
#  * [config_file_path]: optional path to config parameter file
#
# Author: patmac@cs.utexas.edu (Patrick MacAlpine)

# Parse command line arguments 

if (ARGV.size < 2)
  STDERR.print "Usage: #{$0} <experiment_path> <numGames> [config_file_path]\n"
  exit(1)
end

$experimentbase = File.expand_path(ARGV[0])

# number of games
$numGames = ARGV[1].to_i 

if (ARGV.size >= 3)
  $configpath = File.expand_path(ARGV[2])
else
  $configpath = File.dirname(__FILE__) + "/localconfig.rb"
end


# Some parameters:

# Read in user or experiment-specific variables
if (File.exists?($configpath)) 
  require ($configpath)
else
  STDERR.print "Could not find the configuration file #{$configpath}.  Exiting!\n"
  exit(1)
end

config_parameters = ["$run_path", "$opponents_dir", "$opponents", "$logEnabled"]

config_parameters.each do |var|
  if (eval("defined?(#{var})").nil?)
    STDERR.print "Could not find variable #{var} in the configuration file #{$configpath}.  Exiting!\n"
    exit(1)
  end
end

# condor user
$user = `whoami`.strip


# Hostname is used to export the display on the remote machine back to the
# master node
require 'socket'
$hostname = Socket.gethostname

# ---------------------------------------

def run_on_condor(opponent, run, agentSide) 

  valueFile = $experimentbase + "/results/#{opponent}_#{run}_#{agentSide}.txt"
  if (File.exists?(valueFile))
    $numResults = $numResults-1
    return
  end
  condorContents = <<END_OF_CONDORFILE;

Executable = #{$run_path}
Universe = vanilla
Environment = ONCONDOR=true
Getenv = true
Requirements = ARCH == "X86_64" && !GPU
Rank = -SlotId-1*InMastodon

+Group = "GRAD"
+Project = "AI_ROBOTICS"
+ProjectDescription = "Robosoccer Sim-3D Experiments"

Input = /dev/null


END_OF_CONDORFILE
	
    condorContents = condorContents + "\n"

    if ($logEnabled)
       condorContents = condorContents + 
       "Error = #{$experimentbase}/process/#{opponent}_#{run}_#{agentSide}.err\n" +
       "Output = #{$experimentbase}/process/#{opponent}_#{run}_#{agentSide}.out\n" + 
       "Log = #{$experimentbase}/process/#{opponent}_#{run}_#{agentSide}.log\n"
    else 
       condorContents = condorContents +
      "Error = /dev/null\n" + 
      "Output = /dev/null\n" + 
      "Log = /dev/null\n"
    end
    opponent_dir = "#{$opponents_dir}/#{opponent}"
    condorContents = condorContents + "arguments = #{valueFile} #{opponent_dir} #{agentSide}\n" +
      "Queue 1";

    
  #submit the job:
  $runningProcesses = true
  print "Submitting job for #{opponent}_#{run}_#{agentSide}\n"
  condorSubmitPipe = open("|condor_submit", 'w');
  condorSubmitPipe.write(condorContents)
  condorSubmitPipe.close
  #sleep 1
end

#make sure that the 'process' and 'results' directories exist under $experimentbase
Dir.mkdir($experimentbase) unless File.exists?($experimentbase)

Dir.mkdir($experimentbase + "/process") unless File.exists?($experimentbase + "/process")
Dir.mkdir($experimentbase + "/results") unless File.exists?($experimentbase + "/results")
# Directory for storing data about self collisions
#Dir.mkdir($experimentbase + "/sc") unless File.exists?($experimentbase + "/sc")

def run()
  #First, let's clear off existing condor jobs
  system("condor_rm #{$user}")

  # Clean up any status files from a previous run
  Dir.chdir("#{$experimentbase}/process") do
    system("rm -f __*")
  end

  Dir.chdir("#{$experimentbase}/results") do
    # Remove files with missing agents                            
    print "Removing results with missing agents...\n"
    system("for i in `grep -l missing_ *.txt`; do rm ${i}; done")
    # Remove files with late agents
    print "Removing results with late agents...\n"
    system("for i in `grep -l late_ *.txt`; do rm ${i}; done")
    # Remove files with bad situations
    print "Removing results with bad situations...\n"
    system("for i in `grep -l bad_ *.txt`; do rm ${i}; done")
    # Remove files with no score
    print "Removing results with no score...\n"
    system("for i in `grep -L \"score = \" *.txt`; do rm ${i}; done")
    # Remove files with all crashed agents
    print "Removing results with all crashed agents...\n"
    system("for i in `grep -l all_crashed_ *.txt`; do rm ${i}; done")
    # Remove files with crashed agents      
    #print "Removing results with crashed agents...\n"
    #system("for i in `grep -l crash_opp magma*.txt`; do for j in `grep -l dubble-bubble ${i}`; do rm ${j}; done; done")
  end

  sleep 2

  $numResults = $numGames*2*$opponents.size

  $opponents.each do |opponent|
    (0..$numGames-1).each do |game|
      run_on_condor(opponent, game, "left")
      run_on_condor(opponent, game, "right")
    end
  end


  numRunning = Dir.glob("#{$experimentbase}/process/__running_*").size
  lastNumWaitingFor = $numResults-numRunning
  failueToQueueTimeoutSecs = 900
  queueingTimeoutSecs = failueToQueueTimeoutSecs
  while ($numResults > numRunning)
    #print "numRunning = #{$numRunning}, numResults = #{numResults}\n"
    print "Waiting for #{$numResults-numRunning} processes to start running...\n"
    sleep 5
    expToRerun = Dir.glob("#{$experimentbase}/process/__rerun_*")
    if (expToRerun.size > 0) 
      print "Rerunning #{expToRerun.size} processes...\n"
    end
    expToRerun.each do |rerun|
      system("rm -f #{rerun}")
      rerun = File.basename(rerun)
      rerun["__rerun_"] = ""
      args = rerun.split(/_/)
      run_on_condor(args[0], args[1], args[2])
    end
    numRunning = Dir.glob("#{$experimentbase}/process/__running_*").size

    if ($numResults-numRunning == lastNumWaitingFor)
      queueingTimeoutSecs = queueingTimeoutSecs-5
      if (queueingTimeoutSecs == 0)
        #Something has hung up in the system so lots get out of here
        $timeToWaitMins = 0
        break
      end
    else
      lastNumWaitingFor = $numResults-numRunning
      queueingTimeoutSecs = failueToQueueTimeoutSecs
    end
  end 

  # Clean up status files
  if (! $logEnabled)
    Dir.chdir("#{$experimentbase}/process") do
      system("rm -f __*")
    end
  end 

  if ($runningProcesses)
    if ($numResults > numRunning)
      print "Timed out while queueing processes...rerunning system\n" 
    else
      print "All processes have started running!\n"
    end
  else
    print "All results are in!\n"
  end 
end

$runningProcesses = true
waitToCheckReturnedResults = 22
$timeToWaitMins = waitToCheckReturnedResults
while ($runningProcesses)
  $runningProcesses = false
  $timeToWaitMins = waitToCheckReturnedResults
  run()
  while ($timeToWaitMins > 0 && $runningProcesses)
    print "Will check if all results are recorded in #{$timeToWaitMins} minutes...\n"
    #if ($timeToWaitMins == 10) 
    #  print "Removing all non-running jobs\n"
    #  system("condor_rm -constraint 'JobStatus =!= 2' #{$user}")
    #end
    $secondsLeftInMinute = 60
    while ($secondsLeftInMinute > 0)
      condorQ = open("|condor_q #{$user}", 'r')
      lastLine = condorQ.readlines.last
      lastLine =~ /([0-9]+) jobs/
      jobsRemainRead = $1.to_i
      if (jobsRemainRead == 0)
        $timeToWaitMins = 0
        break
      end
      sleep 5
      $secondsLeftInMinute = $secondsLeftInMinute-5
    end
    $timeToWaitMins = $timeToWaitMins-1
  end 
end
   

