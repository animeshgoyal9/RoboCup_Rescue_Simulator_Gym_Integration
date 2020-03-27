package AnimFireChalAgent;

public class ActionBean {
	int agent_id;
	int building_id;

	public ActionBean() {
		
	}

	public ActionBean(int agent_id, int building_id) {
		this.agent_id = agent_id;
		this.building_id = building_id;
	}
	
	public int getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}
	public int getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(int building_id) {
		this.building_id = building_id;
	}

	@Override
	public String toString() {
		return "ActionBean [agent_id=" + agent_id + ", building_id=" + building_id + "]";
	}
}
