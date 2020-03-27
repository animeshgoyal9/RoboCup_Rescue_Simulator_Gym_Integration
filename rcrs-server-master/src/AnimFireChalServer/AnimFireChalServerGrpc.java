package AnimFireChalServer;

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
public final class AnimFireChalServerGrpc {

  private AnimFireChalServerGrpc() {}

  public static final String SERVICE_NAME = "AnimFireChalServer.AnimFireChalServer";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.BuildingInfo,
      AnimFireChalServer.AnimFireChalServerProto.Empty> getGetBuildingInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBuildingInfo",
      requestType = AnimFireChalServer.AnimFireChalServerProto.BuildingInfo.class,
      responseType = AnimFireChalServer.AnimFireChalServerProto.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.BuildingInfo,
      AnimFireChalServer.AnimFireChalServerProto.Empty> getGetBuildingInfoMethod() {
    io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.BuildingInfo, AnimFireChalServer.AnimFireChalServerProto.Empty> getGetBuildingInfoMethod;
    if ((getGetBuildingInfoMethod = AnimFireChalServerGrpc.getGetBuildingInfoMethod) == null) {
      synchronized (AnimFireChalServerGrpc.class) {
        if ((getGetBuildingInfoMethod = AnimFireChalServerGrpc.getGetBuildingInfoMethod) == null) {
          AnimFireChalServerGrpc.getGetBuildingInfoMethod = getGetBuildingInfoMethod =
              io.grpc.MethodDescriptor.<AnimFireChalServer.AnimFireChalServerProto.BuildingInfo, AnimFireChalServer.AnimFireChalServerProto.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBuildingInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalServer.AnimFireChalServerProto.BuildingInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalServer.AnimFireChalServerProto.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new AnimFireChalServerMethodDescriptorSupplier("getBuildingInfo"))
              .build();
        }
      }
    }
    return getGetBuildingInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.Reward,
      AnimFireChalServer.AnimFireChalServerProto.Empty> getGetRewardMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getReward",
      requestType = AnimFireChalServer.AnimFireChalServerProto.Reward.class,
      responseType = AnimFireChalServer.AnimFireChalServerProto.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.Reward,
      AnimFireChalServer.AnimFireChalServerProto.Empty> getGetRewardMethod() {
    io.grpc.MethodDescriptor<AnimFireChalServer.AnimFireChalServerProto.Reward, AnimFireChalServer.AnimFireChalServerProto.Empty> getGetRewardMethod;
    if ((getGetRewardMethod = AnimFireChalServerGrpc.getGetRewardMethod) == null) {
      synchronized (AnimFireChalServerGrpc.class) {
        if ((getGetRewardMethod = AnimFireChalServerGrpc.getGetRewardMethod) == null) {
          AnimFireChalServerGrpc.getGetRewardMethod = getGetRewardMethod =
              io.grpc.MethodDescriptor.<AnimFireChalServer.AnimFireChalServerProto.Reward, AnimFireChalServer.AnimFireChalServerProto.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getReward"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalServer.AnimFireChalServerProto.Reward.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalServer.AnimFireChalServerProto.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new AnimFireChalServerMethodDescriptorSupplier("getReward"))
              .build();
        }
      }
    }
    return getGetRewardMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnimFireChalServerStub newStub(io.grpc.Channel channel) {
    return new AnimFireChalServerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnimFireChalServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AnimFireChalServerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnimFireChalServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AnimFireChalServerFutureStub(channel);
  }

  /**
   */
  public static abstract class AnimFireChalServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void getBuildingInfo(AnimFireChalServer.AnimFireChalServerProto.BuildingInfo request,
        io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBuildingInfoMethod(), responseObserver);
    }

    /**
     */
    public void getReward(AnimFireChalServer.AnimFireChalServerProto.Reward request,
        io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRewardMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetBuildingInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AnimFireChalServer.AnimFireChalServerProto.BuildingInfo,
                AnimFireChalServer.AnimFireChalServerProto.Empty>(
                  this, METHODID_GET_BUILDING_INFO)))
          .addMethod(
            getGetRewardMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AnimFireChalServer.AnimFireChalServerProto.Reward,
                AnimFireChalServer.AnimFireChalServerProto.Empty>(
                  this, METHODID_GET_REWARD)))
          .build();
    }
  }

  /**
   */
  public static final class AnimFireChalServerStub extends io.grpc.stub.AbstractStub<AnimFireChalServerStub> {
    private AnimFireChalServerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalServerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalServerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalServerStub(channel, callOptions);
    }

    /**
     */
    public void getBuildingInfo(AnimFireChalServer.AnimFireChalServerProto.BuildingInfo request,
        io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBuildingInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getReward(AnimFireChalServer.AnimFireChalServerProto.Reward request,
        io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRewardMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AnimFireChalServerBlockingStub extends io.grpc.stub.AbstractStub<AnimFireChalServerBlockingStub> {
    private AnimFireChalServerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalServerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalServerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public AnimFireChalServer.AnimFireChalServerProto.Empty getBuildingInfo(AnimFireChalServer.AnimFireChalServerProto.BuildingInfo request) {
      return blockingUnaryCall(
          getChannel(), getGetBuildingInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public AnimFireChalServer.AnimFireChalServerProto.Empty getReward(AnimFireChalServer.AnimFireChalServerProto.Reward request) {
      return blockingUnaryCall(
          getChannel(), getGetRewardMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AnimFireChalServerFutureStub extends io.grpc.stub.AbstractStub<AnimFireChalServerFutureStub> {
    private AnimFireChalServerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalServerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalServerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AnimFireChalServer.AnimFireChalServerProto.Empty> getBuildingInfo(
        AnimFireChalServer.AnimFireChalServerProto.BuildingInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBuildingInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AnimFireChalServer.AnimFireChalServerProto.Empty> getReward(
        AnimFireChalServer.AnimFireChalServerProto.Reward request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRewardMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BUILDING_INFO = 0;
  private static final int METHODID_GET_REWARD = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AnimFireChalServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AnimFireChalServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BUILDING_INFO:
          serviceImpl.getBuildingInfo((AnimFireChalServer.AnimFireChalServerProto.BuildingInfo) request,
              (io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty>) responseObserver);
          break;
        case METHODID_GET_REWARD:
          serviceImpl.getReward((AnimFireChalServer.AnimFireChalServerProto.Reward) request,
              (io.grpc.stub.StreamObserver<AnimFireChalServer.AnimFireChalServerProto.Empty>) responseObserver);
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

  private static abstract class AnimFireChalServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnimFireChalServerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AnimFireChalServer.AnimFireChalServerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnimFireChalServer");
    }
  }

  private static final class AnimFireChalServerFileDescriptorSupplier
      extends AnimFireChalServerBaseDescriptorSupplier {
    AnimFireChalServerFileDescriptorSupplier() {}
  }

  private static final class AnimFireChalServerMethodDescriptorSupplier
      extends AnimFireChalServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AnimFireChalServerMethodDescriptorSupplier(String methodName) {
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
      synchronized (AnimFireChalServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnimFireChalServerFileDescriptorSupplier())
              .addMethod(getGetBuildingInfoMethod())
              .addMethod(getGetRewardMethod())
              .build();
        }
      }
    }
    return result;
  }
}
