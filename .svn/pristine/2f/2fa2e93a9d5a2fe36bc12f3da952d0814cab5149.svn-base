package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
public class TaskChannel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String taskUuid;
	private String channelUuid;
	private String createUser;
	private java.util.Date createTime;
	private String createTimeStr;
	private String modifyUser;
	private java.util.Date modifyTime;
	private String modifyTimeStr;

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getTaskUuid(){
		return taskUuid;
	}
	public void setTaskUuid(String taskUuid){
		this.taskUuid=taskUuid;
	}
	public String getChannelUuid(){
		return channelUuid;
	}
	public void setChannelUuid(String channelUuid){
		this.channelUuid=channelUuid;
	}
	public String getCreateUser(){
		return createUser;
	}
	public void setCreateUser(String createUser){
		this.createUser=createUser;
	}
	public java.util.Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime){
		this.createTime=createTime;
	}
	public String getCreateTimeStr(){
		if(createTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(createTime);
		}else{
			return "";
		}
	}
	public void setCreateTimeStr(String createTimeStr) throws Exception{
		if(createTimeStr!=null&&!createTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			this.createTime = sdf.parse(createTimeStr);
		}else
			this.createTime = null;
	}
	public String getModifyUser(){
		return modifyUser;
	}
	public void setModifyUser(String modifyUser){
		this.modifyUser=modifyUser;
	}
	public java.util.Date getModifyTime(){
		return modifyTime;
	}
	public void setModifyTime(java.util.Date modifyTime){
		this.modifyTime=modifyTime;
	}
	public String getModifyTimeStr(){
		if(modifyTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(modifyTime);
		}else{
			return "";
		}
	}
	public void setModifyTimeStr(String modifyTimeStr) throws Exception{
		if(modifyTimeStr!=null&&!modifyTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			this.modifyTime = sdf.parse(modifyTimeStr);
		}else
			this.modifyTime = null;
	}
}