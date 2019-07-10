package com.sensing.core.service.impl;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;

import com.sensing.core.service.CaptureServer.Client;
import com.sensing.core.service.CaptureThriftService;
import com.sensing.core.thrift.cap.bean.CapChannelConfig;
import com.sensing.core.thrift.cap.bean.CapFacesSet;
import com.sensing.core.thrift.cap.bean.CapReturn;
import com.sensing.core.thrift.cap.bean.CapSceneImageResult;
import com.sensing.core.utils.props.RemoteInfoUtil;

@Service
public class CaptureThriftServiceImpl implements CaptureThriftService{

	@Override
	public CapReturn AddChannel(CapChannelConfig cfg) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.AddChannel(cfg);;
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapReturn DelChannel(String strChannelID) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.DelChannel(strChannelID);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapReturn DelChannelByRegionID(int iRegionID) throws TException {
		return null;
	}

	@Override
	public CapReturn ModifyChannel(CapChannelConfig cfg) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.ModifyChannel(cfg);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public List<CapFacesSet> DetectFaces(List<ByteBuffer> lstImgs)throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		List<CapFacesSet> list = client.DetectFaces(lstImgs);
		if(transport.isOpen()){
			transport.close();
		}
		return list;
	}

	@Override
	public CapReturn OpenCloseChannels(List<String> lstChnls, int iOpenFlag,int iAllFlag) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.OpenCloseChannels(lstChnls, iOpenFlag, iAllFlag);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapReturn GetVideoProgress(String strChannelID) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.GetVideoProgress(strChannelID);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapReturn NotifyMessage(int iMsg, String strNotifyJson)throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.NotifyMessage(iMsg, strNotifyJson);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapReturn ReceiveMessage(int iMsg, String strReceiveJson)throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapReturn capReturn = client.ReceiveMessage(iMsg, strReceiveJson);
		if(transport.isOpen()){
			transport.close();
		}
		return capReturn;
	}

	@Override
	public CapSceneImageResult GetSceneImage(String strChannelID,String strCapID) throws TException {
		Client client = null;
		TTransport transport = RemoteInfoUtil.getTTransport(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
		TProtocol protocol = new  TBinaryProtocol(transport);
		client = new Client(protocol);
		CapSceneImageResult csir = client.GetSceneImage(strChannelID, strCapID);
		if(transport.isOpen()){
			transport.close();
		}
		return csir;
	}

}
