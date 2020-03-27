package AnimFireChalAgent;

public class AgentBean {
	int agent_id;
	double x;
	double y;
	int water;
	int hp;
	int idle = 1;
	
	
	public AgentBean() {
		
	}	
	public int getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getWater() {
		return water;
	}
	public void setWater(int water) {
		this.water = water;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getIdle() {
		return idle;
	}
	public void setIdle(int idle) {
		this.idle = idle;
	}
	@Override
	public String toString() {
		return "AgentBean [agent_id=" + agent_id + ", x=" + x + ", y=" + y + ", water=" + water + ", hp=" + hp  + ", idle=" + idle + "]";
	}
}
