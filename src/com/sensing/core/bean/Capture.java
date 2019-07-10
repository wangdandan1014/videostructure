package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author mingxingyu
 */
public class Capture implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String deviceId;
	private String identityId;
	private Short capType;
	private Long capTime;
	private String capImgUrl;
	private String deviceArea;

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
	public String getIdentityId(){
		return identityId;
	}
	public void setIdentityId(String identityId){
		this.identityId=identityId;
	}
	public Short getCapType(){
		return capType;
	}
	public void setCapType(Short capType){
		this.capType=capType;
	}
	public Long getCapTime(){
		return capTime;
	}
	public void setCapTime(Long capTime){
		this.capTime=capTime;
	}
	public String getCapImgUrl(){
		return capImgUrl;
	}
	public void setCapImgUrl(String capImgUrl){
		this.capImgUrl=capImgUrl;
	}
	public String getDeviceArea(){
		return deviceArea;
	}
	public void setDeviceArea(String deviceArea){
		this.deviceArea=deviceArea;
	}
}