package com.sensing.core.utils;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class MyTServerEventHandler implements TServerEventHandler{

	@Override
	public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
		return null;
	}

	@Override
	public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		
	}

	@Override
	public void preServe() {
		
	}

	@Override
	public void processContext(ServerContext arg0, TTransport inputTransport,TTransport outputTransport) {
		TSocket socket = (TSocket)inputTransport;
//		System.out.println("客户端ip为:+++++++++++++++++" + socket.getSocket().getRemoteSocketAddress());
	}

}
