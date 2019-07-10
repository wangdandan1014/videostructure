package com.sensing.core.thrift.cap.service.impl;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.thrift.TException;

import com.sensing.core.service.CaptureServer.Iface;
import com.sensing.core.thrift.cap.bean.CapChannelConfig;
import com.sensing.core.thrift.cap.bean.CapFacesSet;
import com.sensing.core.thrift.cap.bean.CapReturn;
import com.sensing.core.thrift.cap.bean.CapSceneImageResult;

public class CaptureServiceImpl implements Iface {

	@Override
	public CapReturn AddChannel(CapChannelConfig cfg) throws TException {
		return null;
	}

	@Override
	public CapReturn DelChannel(String strChannelID) throws TException {
		return null;
	}

	@Override
	public CapReturn DelChannelByRegionID(int iRegionID) throws TException {
		return null;
	}

	@Override
	public CapReturn ModifyChannel(CapChannelConfig cfg) throws TException {
		return null;
	}

	@Override
	public List<CapFacesSet> DetectFaces(List<ByteBuffer> lstImgs)
			throws TException {
		return null;
	}

	@Override
	public CapReturn OpenCloseChannels(List<String> lstChnls, int iOpenFlag,
			int iAllFlag) throws TException {
		return null;
	}

	@Override
	public CapReturn GetVideoProgress(String strChannelID) throws TException {
		return null;
	}

	@Override
	public CapReturn NotifyMessage(int iMsg, String strNotifyJson)
			throws TException {
		return null;
	}

	@Override
	public CapReturn ReceiveMessage(int iMsg, String strReceiveJson)
			throws TException {
		return null;
	}

	@Override
	public CapSceneImageResult GetSceneImage(String strChannelID,
			String strCapID) throws TException {
		return null;
	}
}