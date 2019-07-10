package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author mingxingyu
 */
public class CmpLogs implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String deviceId;
	private String captureUuid;
	private Long cmpTime;
	private Double cmpScore;

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getDeviceId(){
		return deviceId;
	}
	public void setDeviceId(String deviceId){
		this.deviceId=deviceId;
	}
	public String getCaptureUuid(){
		return captureUuid;
	}
	public void setCaptureUuid(String captureUuid){
		this.captureUuid=captureUuid;
	}
	public Long getCmpTime(){
		return cmpTime;
	}
	public void setCmpTime(Long cmpTime){
		this.cmpTime=cmpTime;
	}
	public Double getCmpScore(){
		return cmpScore;
	}
	public void setCmpScore(Double cmpScore){
		this.cmpScore=cmpScore;
	}
}