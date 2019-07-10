package com.sensing.core.bean;

import java.io.Serializable;

import com.sensing.core.bean.Task;
import com.sensing.core.utils.Constants;

public class StaticTask extends Task  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String channelAddr;
	private String channelDescription;
	private String channelRtmp;//视频rtmp流地址
	private String channelId;
	private String createUserStr;
	public String getChannelAddr() {
		return channelAddr;
	}
	public void setChannelAddr(String channelAddr) {
		this.channelAddr = channelAddr;
	}
	public String getChannelDescription() {
		return channelDescription;
	}
	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}
	public String getChannelRtmp() {
		return channelRtmp;
	}
	public void setChannelRtmp(String channelRtmp) {
		this.channelRtmp = channelRtmp;
	}
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCreateUserStr() {
		return createUserStr;
	}
	public void setCreateUserStr(String createUserStr) {
		this.createUserStr = createUserStr;
	}
}
