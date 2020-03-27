package AnimFireChalAgent;

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
    comments = "Source: AgentInfo.proto")
public final class AnimFireChalAgentGrpc {

  private AnimFireChalAgentGrpc() {}

  public static final String SERVICE_NAME = "AnimFireChalAgent.AnimFireChalAgent";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AnimFireChalAgent.AnimFireChalProto.ActionInfo,
      AnimFireChalAgent.AnimFireChalProto.AgentInfo> getGetAgentInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAgentInfo",
      requestType = AnimFireChalAgent.AnimFireChalProto.ActionInfo.class,
      responseType = AnimFireChalAgent.AnimFireChalProto.AgentInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AnimFireChalAgent.AnimFireChalProto.ActionInfo,
      AnimFireChalAgent.AnimFireChalProto.AgentInfo> getGetAgentInfoMethod() {
    io.grpc.MethodDescriptor<AnimFireChalAgent.AnimFireChalProto.ActionInfo, AnimFireChalAgent.AnimFireChalProto.AgentInfo> getGetAgentInfoMethod;
    if ((getGetAgentInfoMethod = AnimFireChalAgentGrpc.getGetAgentInfoMethod) == null) {
      synchronized (AnimFireChalAgentGrpc.class) {
        if ((getGetAgentInfoMethod = AnimFireChalAgentGrpc.getGetAgentInfoMethod) == null) {
          AnimFireChalAgentGrpc.getGetAgentInfoMethod = getGetAgentInfoMethod =
              io.grpc.MethodDescriptor.<AnimFireChalAgent.AnimFireChalProto.ActionInfo, AnimFireChalAgent.AnimFireChalProto.AgentInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAgentInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalAgent.AnimFireChalProto.ActionInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AnimFireChalAgent.AnimFireChalProto.AgentInfo.getDefaultInstance()))
              .setSchemaDescriptor(new AnimFireChalAgentMethodDescriptorSupplier("getAgentInfo"))
              .build();
        }
      }
    }
    return getGetAgentInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnimFireChalAgentStub newStub(io.grpc.Channel channel) {
    return new AnimFireChalAgentStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnimFireChalAgentBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AnimFireChalAgentBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnimFireChalAgentFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AnimFireChalAgentFutureStub(channel);
  }

  /**
   */
  public static abstract class AnimFireChalAgentImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAgentInfo(AnimFireChalAgent.AnimFireChalProto.ActionInfo request,
        io.grpc.stub.StreamObserver<AnimFireChalAgent.AnimFireChalProto.AgentInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAgentInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAgentInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AnimFireChalAgent.AnimFireChalProto.ActionInfo,
                AnimFireChalAgent.AnimFireChalProto.AgentInfo>(
                  this, METHODID_GET_AGENT_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class AnimFireChalAgentStub extends io.grpc.stub.AbstractStub<AnimFireChalAgentStub> {
    private AnimFireChalAgentStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalAgentStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalAgentStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalAgentStub(channel, callOptions);
    }

    /**
     */
    public void getAgentInfo(AnimFireChalAgent.AnimFireChalProto.ActionInfo request,
        io.grpc.stub.StreamObserver<AnimFireChalAgent.AnimFireChalProto.AgentInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAgentInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AnimFireChalAgentBlockingStub extends io.grpc.stub.AbstractStub<AnimFireChalAgentBlockingStub> {
    private AnimFireChalAgentBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalAgentBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalAgentBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalAgentBlockingStub(channel, callOptions);
    }

    /**
     */
    public AnimFireChalAgent.AnimFireChalProto.AgentInfo getAgentInfo(AnimFireChalAgent.AnimFireChalProto.ActionInfo request) {
      return blockingUnaryCall(
          getChannel(), getGetAgentInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AnimFireChalAgentFutureStub extends io.grpc.stub.AbstractStub<AnimFireChalAgentFutureStub> {
    private AnimFireChalAgentFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AnimFireChalAgentFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimFireChalAgentFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AnimFireChalAgentFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AnimFireChalAgent.AnimFireChalProto.AgentInfo> getAgentInfo(
        AnimFireChalAgent.AnimFireChalProto.ActionInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAgentInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_AGENT_INFO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AnimFireChalAgentImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AnimFireChalAgentImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_AGENT_INFO:
          serviceImpl.getAgentInfo((AnimFireChalAgent.AnimFireChalProto.ActionInfo) request,
              (io.grpc.stub.StreamObserver<AnimFireChalAgent.AnimFireChalProto.AgentInfo>) responseObserver);
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

  private static abstract class AnimFireChalAgentBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnimFireChalAgentBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AnimFireChalAgent.AnimFireChalProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnimFireChalAgent");
    }
  }

  private static final class AnimFireChalAgentFileDescriptorSupplier
      extends AnimFireChalAgentBaseDescriptorSupplier {
    AnimFireChalAgentFileDescriptorSupplier() {}
  }

  private static final class AnimFireChalAgentMethodDescriptorSupplier
      extends AnimFireChalAgentBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AnimFireChalAgentMethodDescriptorSupplier(String methodName) {
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
      synchronized (AnimFireChalAgentGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnimFireChalAgentFileDescriptorSupplier())
              .addMethod(getGetAgentInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
