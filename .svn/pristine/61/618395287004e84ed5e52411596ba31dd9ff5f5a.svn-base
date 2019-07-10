package com.sensing.core.service;

import com.sensing.core.thrift.cap.bean.CapChannelConfig;
import com.sensing.core.thrift.cap.bean.CapFacesSet;
import com.sensing.core.thrift.cap.bean.CapReturn;
import com.sensing.core.thrift.cap.bean.CapSceneImageResult;

public interface CaptureThriftService {

	 	public CapReturn AddChannel(CapChannelConfig cfg) throws org.apache.thrift.TException;

	    public CapReturn DelChannel(java.lang.String strChannelID) throws org.apache.thrift.TException;

	    public CapReturn DelChannelByRegionID(int iRegionID) throws org.apache.thrift.TException;

	    public CapReturn ModifyChannel(CapChannelConfig cfg) throws org.apache.thrift.TException;

	    public java.util.List<CapFacesSet> DetectFaces(java.util.List<java.nio.ByteBuffer> lstImgs) throws org.apache.thrift.TException;

	    public CapReturn OpenCloseChannels(java.util.List<java.lang.String> lstChnls, int iOpenFlag, int iAllFlag) throws org.apache.thrift.TException;

	    public CapReturn GetVideoProgress(java.lang.String strChannelID) throws org.apache.thrift.TException;

	    public CapReturn NotifyMessage(int iMsg, java.lang.String strNotifyJson) throws org.apache.thrift.TException;

	    public CapReturn ReceiveMessage(int iMsg, java.lang.String strReceiveJson) throws org.apache.thrift.TException;

	    public CapSceneImageResult GetSceneImage(java.lang.String strChannelID, java.lang.String strCapID) throws org.apache.thrift.TException;
}
