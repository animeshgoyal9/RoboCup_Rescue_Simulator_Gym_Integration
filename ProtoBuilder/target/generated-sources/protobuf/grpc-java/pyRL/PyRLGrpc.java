package pyRL;

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
    comments = "Source: PyRL.proto")
public final class PyRLGrpc {

  private PyRLGrpc() {}

  public static final String SERVICE_NAME = "pyRL.PyRL";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pyRL.PyRLProto.Action,
      pyRL.PyRLProto.Obs> getGetObsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getObs",
      requestType = pyRL.PyRLProto.Action.class,
      responseType = pyRL.PyRLProto.Obs.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pyRL.PyRLProto.Action,
      pyRL.PyRLProto.Obs> getGetObsMethod() {
    io.grpc.MethodDescriptor<pyRL.PyRLProto.Action, pyRL.PyRLProto.Obs> getGetObsMethod;
    if ((getGetObsMethod = PyRLGrpc.getGetObsMethod) == null) {
      synchronized (PyRLGrpc.class) {
        if ((getGetObsMethod = PyRLGrpc.getGetObsMethod) == null) {
          PyRLGrpc.getGetObsMethod = getGetObsMethod =
              io.grpc.MethodDescriptor.<pyRL.PyRLProto.Action, pyRL.PyRLProto.Obs>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getObs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pyRL.PyRLProto.Action.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pyRL.PyRLProto.Obs.getDefaultInstance()))
              .setSchemaDescriptor(new PyRLMethodDescriptorSupplier("getObs"))
              .build();
        }
      }
    }
    return getGetObsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PyRLStub newStub(io.grpc.Channel channel) {
    return new PyRLStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PyRLBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PyRLBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PyRLFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PyRLFutureStub(channel);
  }

  /**
   */
  public static abstract class PyRLImplBase implements io.grpc.BindableService {

    /**
     */
    public void getObs(pyRL.PyRLProto.Action request,
        io.grpc.stub.StreamObserver<pyRL.PyRLProto.Obs> responseObserver) {
      asyncUnimplementedUnaryCall(getGetObsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetObsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                pyRL.PyRLProto.Action,
                pyRL.PyRLProto.Obs>(
                  this, METHODID_GET_OBS)))
          .build();
    }
  }

  /**
   */
  public static final class PyRLStub extends io.grpc.stub.AbstractStub<PyRLStub> {
    private PyRLStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PyRLStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PyRLStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PyRLStub(channel, callOptions);
    }

    /**
     */
    public void getObs(pyRL.PyRLProto.Action request,
        io.grpc.stub.StreamObserver<pyRL.PyRLProto.Obs> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetObsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PyRLBlockingStub extends io.grpc.stub.AbstractStub<PyRLBlockingStub> {
    private PyRLBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PyRLBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PyRLBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PyRLBlockingStub(channel, callOptions);
    }

    /**
     */
    public pyRL.PyRLProto.Obs getObs(pyRL.PyRLProto.Action request) {
      return blockingUnaryCall(
          getChannel(), getGetObsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PyRLFutureStub extends io.grpc.stub.AbstractStub<PyRLFutureStub> {
    private PyRLFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PyRLFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PyRLFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PyRLFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pyRL.PyRLProto.Obs> getObs(
        pyRL.PyRLProto.Action request) {
      return futureUnaryCall(
          getChannel().newCall(getGetObsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OBS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PyRLImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PyRLImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OBS:
          serviceImpl.getObs((pyRL.PyRLProto.Action) request,
              (io.grpc.stub.StreamObserver<pyRL.PyRLProto.Obs>) responseObserver);
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

  private static abstract class PyRLBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PyRLBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pyRL.PyRLProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PyRL");
    }
  }

  private static final class PyRLFileDescriptorSupplier
      extends PyRLBaseDescriptorSupplier {
    PyRLFileDescriptorSupplier() {}
  }

  private static final class PyRLMethodDescriptorSupplier
      extends PyRLBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PyRLMethodDescriptorSupplier(String methodName) {
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
      synchronized (PyRLGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PyRLFileDescriptorSupplier())
              .addMethod(getGetObsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
