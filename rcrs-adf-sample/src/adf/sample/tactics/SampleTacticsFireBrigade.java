package adf.sample.tactics;

import adf.agent.action.Action;
import adf.agent.action.common.ActionMove;
import adf.agent.action.common.ActionRest;
import adf.agent.communication.MessageManager;
import adf.agent.communication.standard.bundle.centralized.CommandScout;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.debug.WorldViewLauncher;
import adf.component.centralized.CommandExecutor;
import adf.component.communication.CommunicationMessage;
import adf.component.extaction.ExtAction;
import adf.component.module.complex.Search;
import adf.sample.tactics.utils.MessageTool;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.EntityID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import AnimFireChalAgent.ActionBean;
import AnimFireChalAgent.AgentBean;
import AnimFireChalAgent.AgentResources;
import AnimFireChalAgent.AnimFireChalAgent;
import adf.agent.action.fire.ActionExtinguish;
import adf.agent.action.fire.ActionRefill;
import adf.agent.communication.standard.bundle.centralized.CommandFire;
import adf.agent.communication.standard.bundle.information.MessageFireBrigade;
import adf.component.module.complex.BuildingDetector;
import adf.component.tactics.TacticsFireBrigade;

public class SampleTacticsFireBrigade extends TacticsFireBrigade
{
    private BuildingDetector buildingDetector;
    private Search search;

    private ExtAction actionFireFighting;
    private ExtAction actionExtMove;

    private CommandExecutor<CommandFire> commandExecutorFire;
    private CommandExecutor<CommandScout> commandExecutorScout;

    private MessageTool messageTool;

    private CommunicationMessage recentCommand;

	private Boolean isVisualDebug;
	private Server server;
	
    @Override
    public void initialize(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData developData)
    {
        messageManager.setChannelSubscriber(moduleManager.getChannelSubscriber("MessageManager.PlatoonChannelSubscriber", "adf.sample.module.comm.SampleChannelSubscriber"));
        messageManager.setMessageCoordinator(moduleManager.getMessageCoordinator("MessageManager.PlatoonMessageCoordinator", "adf.sample.module.comm.SampleMessageCoordinator"));

        worldInfo.indexClass(
                StandardEntityURN.ROAD,
                StandardEntityURN.HYDRANT,
                StandardEntityURN.BUILDING,
                StandardEntityURN.REFUGE,
                StandardEntityURN.GAS_STATION,
                StandardEntityURN.AMBULANCE_CENTRE,
                StandardEntityURN.FIRE_STATION,
                StandardEntityURN.POLICE_OFFICE
        );

        this.messageTool = new MessageTool(scenarioInfo, developData);

        this.isVisualDebug = (scenarioInfo.isDebugMode()
                            && moduleManager.getModuleConfig().getBooleanValue("VisualDebug", false));

        this.recentCommand = null;
        // init Algorithm Module & ExtAction
        switch  (scenarioInfo.getMode())
        {
            case PRECOMPUTATION_PHASE:
            case PRECOMPUTED:
                this.search = moduleManager.getModule("TacticsFireBrigade.Search", "adf.sample.module.complex.SampleSearch");
                this.buildingDetector = moduleManager.getModule("TacticsFireBrigade.BuildingDetector", "adf.sample.module.complex.SampleBuildingDetector");
                this.actionFireFighting = moduleManager.getExtAction("TacticsFireBrigade.ActionFireFighting", "adf.sample.extaction.ActionFireFighting");
                this.actionExtMove = moduleManager.getExtAction("TacticsFireBrigade.ActionExtMove", "adf.sample.extaction.ActionExtMove");
                this.commandExecutorFire = moduleManager.getCommandExecutor("TacticsFireBrigade.CommandExecutorFire", "adf.sample.centralized.CommandExecutorFire");
                this.commandExecutorScout = moduleManager.getCommandExecutor("TacticsFireBrigade.CommandExecutorScout", "adf.sample.centralized.CommandExecutorScout");
                break;
            case NON_PRECOMPUTE:
                this.search = moduleManager.getModule("TacticsFireBrigade.Search", "adf.sample.module.complex.SampleSearch");
                this.buildingDetector = moduleManager.getModule("TacticsFireBrigade.BuildingDetector", "adf.sample.module.complex.SampleBuildingDetector");
                this.actionFireFighting = moduleManager.getExtAction("TacticsFireBrigade.ActionFireFighting", "adf.sample.extaction.ActionFireFighting");
                this.actionExtMove = moduleManager.getExtAction("TacticsFireBrigade.ActionExtMove", "adf.sample.extaction.ActionExtMove");
                this.commandExecutorFire = moduleManager.getCommandExecutor("TacticsFireBrigade.CommandExecutorFire", "adf.sample.centralized.CommandExecutorFire");
                this.commandExecutorScout = moduleManager.getCommandExecutor("TacticsFireBrigade.CommandExecutorScout", "adf.sample.centralized.CommandExecutorScout");
                break;
        }

        registerModule(this.buildingDetector);
        registerModule(this.search);
        registerModule(this.actionFireFighting);
        registerModule(this.actionExtMove);
        registerModule(this.commandExecutorFire);
        registerModule(this.commandExecutorScout);
        
		server = ServerBuilder.forPort(20001).addService(new AnimFireChalAgent()).build();
		try {
			server.start();
			System.out.println("Server started at " + server.getPort());	
//			server.awaitTermination();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

    @Override
    public void precompute(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData developData)
    {
        modulesPrecompute(precomputeData);
    }

    @Override
    public void resume(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData developData)
    {
        modulesResume(precomputeData);

        if (isVisualDebug)
        {
            WorldViewLauncher.getInstance().showTimeStep(agentInfo, worldInfo, scenarioInfo);
        }
    }
    

    @Override
    public void preparate(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, DevelopData developData)
    {
        modulesPreparate();

        if (isVisualDebug)
        {
            WorldViewLauncher.getInstance().showTimeStep(agentInfo, worldInfo, scenarioInfo);
        }
    }

    @Override
    public Action think(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData developData)
    {
    	// System.out.println("Check 1 -----------------");
    	this.messageTool.reflectMessage(agentInfo, worldInfo, scenarioInfo, messageManager);
        this.messageTool.sendRequestMessages(agentInfo, worldInfo, scenarioInfo, messageManager);
        this.messageTool.sendInformationMessages(agentInfo, worldInfo, scenarioInfo, messageManager);

        modulesUpdateInfo(messageManager);

        if (isVisualDebug)
        {
            WorldViewLauncher.getInstance().showTimeStep(agentInfo, worldInfo, scenarioInfo);
        }
        	
        FireBrigade agent = (FireBrigade) agentInfo.me();
        EntityID agentID = agentInfo.getID();
        // command
        for (CommunicationMessage message : messageManager.getReceivedMessageList(CommandScout.class))
        {
            CommandScout command = (CommandScout) message;
            if (command.isToIDDefined() && Objects.requireNonNull(command.getToID()).getValue() == agentID.getValue())
            {
                this.recentCommand = command;
                this.commandExecutorScout.setCommand(command);
            }
        }

        for (CommunicationMessage message : messageManager.getReceivedMessageList(CommandFire.class))
        {
            CommandFire command = (CommandFire) message;
            if (command.isToIDDefined() && Objects.requireNonNull(command.getToID()).getValue() == agentID.getValue())
            {
                this.recentCommand = command;
                this.commandExecutorFire.setCommand(command);
            }
        }
        if (this.recentCommand != null)
        {
            Action action = null;
            if (this.recentCommand.getClass() == CommandFire.class)
            {
                action = this.commandExecutorFire.calc().getAction();
            }
            else if (this.recentCommand.getClass() == CommandScout.class)
            {
                action = this.commandExecutorScout.calc().getAction();
            }

            if (action != null)
            {
                this.sendActionMessage(messageManager, agent, action, agentInfo);
                return action;
            }
        }
        // autonomous
        
        EntityID target = this.buildingDetector.calc().getTarget();
        
        Action action = this.actionFireFighting.setTarget(target).calc().getAction();
        // System.out.println("Check 4 -----------------" + action);
        if (action != null)
        {
            this.sendActionMessage(messageManager, agent, action, agentInfo);
            return action;
        }
        // System.out.println("Check 5 -----------------");
        target = this.search.calc().getTarget();
        // System.out.println("Check 6 -----------------");
        action = this.actionExtMove.setTarget(target).calc().getAction();
        if (action != null)
        {
            this.sendActionMessage(messageManager, agent, action, agentInfo);
            return action;
        }

        messageManager.addMessage(
                new MessageFireBrigade(true, agent, MessageFireBrigade.ACTION_REST,  agent.getPosition())
        );
        return new ActionRest();
    }

    private void sendActionMessage(MessageManager messageManager, FireBrigade agent, Action action, AgentInfo agentInfo)
    {
    	// System.out.println("Check 2 -----------------");
    	// System.out.println(((FireBrigade) agentInfo.me()).getHP());
    	
    	Class<? extends Action> actionClass = action.getClass();
        int actionIndex = -1;
        EntityID target = null;
        
        FireBrigade agent1 = (FireBrigade) agentInfo.me();
		
		AgentBean agentDetails = AgentResources.getAgent((int) agentInfo.getID().getValue());
		
		if(agentDetails == null) {
			agentDetails = new AgentBean();
		}
		
		agentDetails.setAgent_id((int) agentInfo.getID().getValue());
		agentDetails.setHp(agent1.getHP());
		agentDetails.setWater(agent1.getWater());
		agentDetails.setX(agentInfo.getX());
		agentDetails.setY(agentInfo.getY());
		
		AgentResources.setAgents(agentDetails);
		
        if (actionClass == ActionMove.class)
        {
            actionIndex = MessageFireBrigade.ACTION_MOVE;
            List<EntityID> path = ((ActionMove) action).getPath();
            if (path.size() > 0)
            {	
                target = path.get(path.size() - 1);
                System.out.println("***************Start Moving***********************");
     
            }
        }
        else if (actionClass == ActionExtinguish.class)
        {
            actionIndex = MessageFireBrigade.ACTION_EXTINGUISH;
            target = ((ActionExtinguish)action).getTarget();
            System.out.println("***************Start Extinguishing***********************");
        }
        else if (actionClass == ActionRefill.class)
        {
            actionIndex = MessageFireBrigade.ACTION_REFILL;
            target = agent.getPosition();
            System.out.println("***************Start Refilling***********************");
        }
        else if (actionClass == ActionRest.class)
        {
            actionIndex = MessageFireBrigade.ACTION_REST;
            target = agent.getPosition();
            System.out.println("***************Rest Now***********************");
        }

        if (actionIndex != -1)
        {
            messageManager.addMessage(new MessageFireBrigade(true, agent, actionIndex, target));
        }
    }
}
