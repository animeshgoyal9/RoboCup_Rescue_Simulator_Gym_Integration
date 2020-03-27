package AnimFireChalBuilding;


import AnimFireChalBuilding.AnimFireChalBuildingGrpc.AnimFireChalBuildingImplBase;
import AnimFireChalBuilding.AnimFireChalProto.Building;
import AnimFireChalBuilding.AnimFireChalProto.BuildingInfo;
import AnimFireChalBuilding.AnimFireChalProto.Empty;
import AnimFireChalBuilding.AnimFireChalProto.Reward;
import io.grpc.stub.StreamObserver;

public class AnimFireChal extends AnimFireChalBuildingImplBase {
	
	@Override
	public void getBuildingInfo(Empty request, StreamObserver<BuildingInfo> responseObserver) {
		BuildingInfo.Builder reps = BuildingInfo.newBuilder();
		
		String[] b2 = Resources.getB().split("\\|");
		for(int i=0; i<b2.length; i++) {
			String[] bdng = b2[i].split(",");
			Building building = Building.newBuilder().setFieryness(Integer.parseInt(bdng[0])).setTemperature(Double.parseDouble(bdng[1]))
					.setBuildingId(Integer.parseInt(bdng[2])).build();
			reps.addBuildings(building);
		}			
		responseObserver.onNext(reps.build());
		responseObserver.onCompleted();
	}

	@Override
	public void getRewards(Empty request, StreamObserver<Reward> responseObserver) {
		Reward.Builder resp = Reward.newBuilder().setReward(Resources.getReward());
		
		responseObserver.onNext(resp.build());
		responseObserver.onCompleted();
	}
	
	
}