package AnimFireChalAgent;

import java.util.HashMap;
import java.util.Map;

public class AgentResources {
	static ActionBean actions[] = new ActionBean[1];
	static AgentBean agents[] = new AgentBean[2];
	static Map<Integer, AgentBean> agentMap = new HashMap<Integer, AgentBean>();
	
	public static ActionBean[] getActions() {
		return actions;
	}
	public static void setActions(ActionBean[] actions) {
		AgentResources.actions = actions;
	}
	public static AgentBean[] getAgents() {
//		System.out.println(agentMap.values().toArray(new AgentBean[0]));
		return agentMap.values().toArray(new AgentBean[0]);
	}
	public static AgentBean getAgent(int id) {
//		System.out.println(agentMap.values().toArray(new AgentBean[0]));
		return agentMap.get(id);
	}
	public static void setAgents(AgentBean agent) {
		agentMap.put(agent.getAgent_id(), agent);
	}
}
