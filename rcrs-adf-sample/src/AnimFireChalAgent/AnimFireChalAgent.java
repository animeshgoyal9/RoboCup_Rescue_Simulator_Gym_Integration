package AnimFireChalAgent;

import java.util.Arrays;

import AnimFireChalAgent.AnimFireChalAgentGrpc.AnimFireChalAgentImplBase;
import AnimFireChalAgent.AnimFireChalProto.Action;
import AnimFireChalAgent.AnimFireChalProto.ActionInfo;
import AnimFireChalAgent.AnimFireChalProto.Agent;
import AnimFireChalAgent.AnimFireChalProto.AgentInfo;
import io.grpc.stub.StreamObserver;

public class AnimFireChalAgent extends AnimFireChalAgentImplBase {

	@Override
	public void getAgentInfo(ActionInfo request, StreamObserver<AgentInfo> responseObserver) {
		Action[] actions = request.getActionsList().toArray(new Action[0]);
		ActionBean[] actionDetails = new ActionBean[actions.length];
		for (int i =0; i<actions.length; i++) {
			actionDetails[i] = new ActionBean(actions[i].getAgentId(),actions[i].getBuildingId());
		}
//		System.out.println("Action details-------------------");
//		System.out.println(actionDetails.toString());
		AgentResources.setActions(actionDetails);
		
		AgentInfo.Builder resp = AgentInfo.newBuilder();
		
		AgentBean[] agents = AgentResources.getAgents();
		for(int i=0; i<agents.length; i++) {
			System.out.println("-----------------------------------------------------");
			System.out.println(agents[i].toString());
			System.out.println("-----------------------------------------------------");
			Agent a = Agent.newBuilder().setAgentId(agents[i].getAgent_id()).setX(agents[i].getX()).setY(agents[i].getY()).setWater(agents[i].getWater()).setHp(agents[i].getHp()).setIdle(agents[i].getIdle()).build();
			resp.addAgents(a);
			agents[i].setIdle(1);
			AgentResources.setAgents(agents[i]);
			System.out.println("******************* When new Target *************************");
			System.out.println(agents[i]);
			System.out.println("********************************************");
		}
		
		responseObserver.onNext(resp.build());
		responseObserver.onCompleted();
	}

}
