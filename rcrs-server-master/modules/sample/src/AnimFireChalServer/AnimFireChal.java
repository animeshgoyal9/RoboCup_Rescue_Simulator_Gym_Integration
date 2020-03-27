package AnimFireChalServer;
import AnimFireChalServer.AnimFireChalServerGrpc.AnimFireChalServerImplBase;
import AnimFireChalServer.AnimFireChalServerProto.Building;
import AnimFireChalServer.AnimFireChalServerProto.BuildingInfo;
import AnimFireChalServer.AnimFireChalServerProto.Empty;
import AnimFireChalServer.AnimFireChalServerProto.Reward;
import io.grpc.stub.StreamObserver;

public class AnimFireChal extends AnimFireChalServerImplBase {

	@Override
	public void getBuildingInfo(Empty request, StreamObserver<BuildingInfo> responseObserver) {
		BuildingInfo.Builder reps = BuildingInfo.newBuilder();
		
		Building b1 = Building.newBuilder().setFieryness(5).setTemperature(6).setBuildingId(7).build();
		reps.addBuildings(b1);
		
		responseObserver.onNext(reps.build());
		responseObserver.onCompleted();
	}

	@Override
	public void getReward(Empty request, StreamObserver<Reward> responseObserver) {
		Reward reps = Reward.newBuilder().setReward(9).build();
		
		responseObserver.onNext(reps);
		responseObserver.onCompleted();
	}

	
}