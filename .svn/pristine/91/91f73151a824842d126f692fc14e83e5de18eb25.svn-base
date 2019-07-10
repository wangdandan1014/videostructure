package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
@SuppressWarnings("all")
public class SysSubscribe implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String uid;// 用户uuid
	private String jobId;// jobuuid
	private Integer state;
	private java.util.Date subTime;
	private String subTimeStr;
	
	private Integer subType;//订阅类型

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getUid(){
		return uid;
	}
	public void setUid(String uid){
		this.uid=uid;
	}
	public String getJobId(){
		return jobId;
	}
	public void setJobId(String jobId){
		this.jobId=jobId;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public java.util.Date getSubTime(){
		return subTime;
	}
	public void setSubTime(java.util.Date subTime){
		this.subTime=subTime;
	}
	public String getSubTimeStr(){
		if(subTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(subTime);
		}else{
			return "";
		}
	}
	public void setSubTimeStr(String subTimeStr) throws Exception{
		if(subTimeStr!=null&&!subTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.subTime = sdf.parse(subTimeStr);
		}else
			this.subTime = null;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	
	
}