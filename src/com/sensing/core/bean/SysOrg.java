package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
public class SysOrg implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String orgName;
	private String parentId;
	private Integer orgLevel;
	private Integer orde;
	private String capAddr;//抓拍地址

	public String getCapAddr() {
		return capAddr;
	}
	public void setCapAddr(String capAddr) {
		this.capAddr = capAddr;
	}
	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getOrgName(){
		return orgName;
	}
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	public Integer getOrgLevel(){
		return orgLevel;
	}
	public void setOrgLevel(Integer orgLevel){
		this.orgLevel=orgLevel;
	}
	public Integer getOrde(){
		return orde;
	}
	public void setOrde(Integer orde){
		this.orde=orde;
	}
}