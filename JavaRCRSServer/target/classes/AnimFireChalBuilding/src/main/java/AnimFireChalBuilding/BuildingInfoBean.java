package AnimFireChalBuilding;

public class BuildingInfoBean {
	int fieryness; 
	int temperature; 
	int building_id;
	
	public BuildingInfoBean() {
	
	}
	
	public BuildingInfoBean(int fieryness, int temperature, int building_id) {
		this.fieryness = fieryness;
		this.temperature = temperature;
		this.building_id = building_id;
	}
	
	public int getFieryness() {
		return fieryness;
	}
	public void setFieryness(int fieryness) {
		this.fieryness = fieryness;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(int building_id) {
		this.building_id = building_id;
	}

	@Override
	public String toString() {
		return "BuildingInfoBean [fieryness=" + fieryness + ", temperature=" + temperature + ", building_id="
				+ building_id + "]";
	}
}