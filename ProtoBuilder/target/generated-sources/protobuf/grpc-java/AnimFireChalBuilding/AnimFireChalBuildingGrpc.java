package AnimFireChalBuilding;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.24.0)",
    comments = "Source: BuildingInfo.proto")
public final class AnimFireChalBuildingGrpc {

  private AnimFireChalBuildingGrpc() {}

  public static final String SERVICE_NAME = "AnimFireChalBuilding.AnimFireChalBuilding";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty,
      AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> getGetBuildingInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBuildingInfo",
      requestType = AnimFireChalBuilding.AnimFireChalProto.Empty.class,
      responseType = AnimFireChalBuilding.AnimFireChalProto.BuildingInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty,
      AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> getGetBuildingInfoMethod() {
    io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty, AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> getGetBuildingInfoMethod;
    if ((getGetBuildingInfoMethod = AnimFireChalBuildingGrpc.getGetBuildingInfoMethod) == null) {
      synchronized (AnimFireChalBuildingGrpc.class) {
        if ((getGetBuildingInfoMethod = AnimFireChalBuildingGrpc.getGetBuildingInfoMethod) == null) {
          AnimFireChalBuildingGrpc.getGetBuildingInfoMethod = getGetBuildingInfoMethod =
              io.grpc.MethodDescriptor.<AnimFireChalBuilding.AnimFireChalProto.Empty, AnimFireChalBuilding.AnimFireChalProto.BuildingInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBuildingInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalBuilding.AnimFireChalProto.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalBuilding.AnimFireChalProto.BuildingInfo.getDefaultInstance()))
              .setSchemaDescriptor(new AnimFireChalBuildingMethodDescriptorSupplier("getBuildingInfo"))
              .build();
        }
      }
    }
    return getGetBuildingInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty,
      AnimFireChalBuilding.AnimFireChalProto.Reward> getGetRewardsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getRewards",
      requestType = AnimFireChalBuilding.AnimFireChalProto.Empty.class,
      responseType = AnimFireChalBuilding.AnimFireChalProto.Reward.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty,
      AnimFireChalBuilding.AnimFireChalProto.Reward> getGetRewardsMethod() {
    io.grpc.MethodDescriptor<AnimFireChalBuilding.AnimFireChalProto.Empty, AnimFireChalBuilding.AnimFireChalProto.Reward> getGetRewardsMethod;
    if ((getGetRewardsMethod = AnimFireChalBuildingGrpc.getGetRewardsMethod) == null) {
      synchronized (AnimFireChalBuildingGrpc.class) {
        if ((getGetRewardsMethod = AnimFireChalBuildingGrpc.getGetRewardsMethod) == null) {
          AnimFireChalBuildingGrpc.getGetRewardsMethod = getGetRewardsMethod =
              io.grpc.MethodDescriptor.<AnimFireChalBuilding.AnimFireChalProto.Empty, AnimFireChalBuilding.AnimFireChalProto.Reward>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getRewards"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalBuilding.AnimFireChalProto.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalBuilding.AnimFireChalProto.Reward.getDefaultInstance()))
              .setSchemaDescriptor(new AnimFireChalBuildingMethodDescriptorSupplier("getRewards"))
              .build();
        }
      }
    }
    return getGetRewardsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnimFireChalBuildingStub newStub(io.grpc.Channel channel) {
    return new AnimFireChalBuildingStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnimFireChalBuildingBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AnimFireChalBuildingBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnimFireChalBuildingFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AnimFireChalBuildingFutureStub(channel);
  }

  /**
   */
  public static abstract class AnimFireChalBuildingImplBase implements io.grpc.BindableService {

    /**
     */
    public void getBuildingInfo(AnimFireChalBuilding.AnimFireChalProto.Empty request,
        io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBuildingInfoMethod(), responseObserver);
    }

    /**
     */
    public void getRewards(AnimFireChalBuilding.AnimFireChalProto.Empty request,
        io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.Reward> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRewardsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetBuildingInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AnimFireChalBuilding.AnimFireChalProto.Empty,
                AnimFireChalBuilding.AnimFireChalProto.BuildingInfo>(
                  this, METHODID_GET_BUILDING_INFO)))
          .addMethod(
            getGetRewardsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AnimFireChalBuilding.AnimFireChalProto.Empty,
                AnimFireChalBuilding.AnimFireChalProto.Reward>(
                  this, METHODID_GET_REWARDS)))
          .build();
    }
  }

  /**
   */
  public static final class AnimFireChalBuildingStub extends io.grpc.stub.AbstractStub<AnimFireChalBuildingStub> {
    private AnimFireChalBuildingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalBuildingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalBuildingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalBuildingStub(channel, callOptions);
    }

    /**
     */
    public void getBuildingInfo(AnimFireChalBuilding.AnimFireChalProto.Empty request,
        io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBuildingInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRewards(AnimFireChalBuilding.AnimFireChalProto.Empty request,
        io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.Reward> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRewardsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AnimFireChalBuildingBlockingStub extends io.grpc.stub.AbstractStub<AnimFireChalBuildingBlockingStub> {
    private AnimFireChalBuildingBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalBuildingBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalBuildingBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalBuildingBlockingStub(channel, callOptions);
    }

    /**
     */
    public AnimFireChalBuilding.AnimFireChalProto.BuildingInfo getBuildingInfo(AnimFireChalBuilding.AnimFireChalProto.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetBuildingInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public AnimFireChalBuilding.AnimFireChalProto.Reward getRewards(AnimFireChalBuilding.AnimFireChalProto.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetRewardsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AnimFireChalBuildingFutureStub extends io.grpc.stub.AbstractStub<AnimFireChalBuildingFutureStub> {
    private AnimFireChalBuildingFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalBuildingFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalBuildingFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalBuildingFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AnimFireChalBuilding.AnimFireChalProto.BuildingInfo> getBuildingInfo(
        AnimFireChalBuilding.AnimFireChalProto.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBuildingInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AnimFireChalBuilding.AnimFireChalProto.Reward> getRewards(
        AnimFireChalBuilding.AnimFireChalProto.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRewardsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BUILDING_INFO = 0;
  private static final int METHODID_GET_REWARDS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AnimFireChalBuildingImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AnimFireChalBuildingImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BUILDING_INFO:
          serviceImpl.getBuildingInfo((AnimFireChalBuilding.AnimFireChalProto.Empty) request,
              (io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.BuildingInfo>) responseObserver);
          break;
        case METHODID_GET_REWARDS:
          serviceImpl.getRewards((AnimFireChalBuilding.AnimFireChalProto.Empty) request,
              (io.grpc.stub.StreamObserver<AnimFireChalBuilding.AnimFireChalProto.Reward>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AnimFireChalBuildingBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnimFireChalBuildingBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AnimFireChalBuilding.AnimFireChalProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnimFireChalBuilding");
    }
  }

  private static final class AnimFireChalBuildingFileDescriptorSupplier
      extends AnimFireChalBuildingBaseDescriptorSupplier {
    AnimFireChalBuildingFileDescriptorSupplier() {}
  }

  private static final class AnimFireChalBuildingMethodDescriptorSupplier
      extends AnimFireChalBuildingBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AnimFireChalBuildingMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AnimFireChalBuildingGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnimFireChalBuildingFileDescriptorSupplier())
              .addMethod(getGetBuildingInfoMethod())
              .addMethod(getGetRewardsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
