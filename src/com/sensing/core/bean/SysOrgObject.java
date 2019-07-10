package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
public class SysOrgObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String orgId;
	private String objectType;
	private String objectId;
	private Integer level;
	private String createUser;
	private Long createTime;
	private String modifyUser;
	private Long modifyTime;
	private String operOrgId;

	
	public SysOrgObject(String orgId, String objectType, String objectId) {
		super();
		this.orgId = orgId;
		this.objectType = objectType;
		this.objectId = objectId;
	}
	public SysOrgObject(String orgId, String objectType, String objectId, Integer level, String createUser,
			Long createTime, String operOrgId) {
		super();
		this.orgId = orgId;
		this.objectType = objectType;
		this.objectId = objectId;
		this.level = level;
		this.createUser = createUser;
		this.createTime = createTime;
		this.operOrgId = operOrgId;
	}
	public SysOrgObject() {
		super();
	}
	public String getOperOrgId() {
		return operOrgId;
	}
	public void setOperOrgId(String operOrgId) {
		this.operOrgId = operOrgId;
	}
	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getOrgId(){
		return orgId;
	}
	public void setOrgId(String orgId){
		this.orgId=orgId;
	}
	public String getObjectType(){
		return objectType;
	}
	public void setObjectType(String objectType){
		this.objectType=objectType;
	}
	public String getObjectId(){
		return objectId;
	}
	public void setObjectId(String objectId){
		this.objectId=objectId;
	}
	public Integer getLevel(){
		return level;
	}
	public void setLevel(Integer level){
		this.level=level;
	}
	public String getCreateUser(){
		return createUser;
	}
	public void setCreateUser(String createUser){
		this.createUser=createUser;
	}
	public Long getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Long createTime){
		this.createTime=createTime;
	}
	public String getModifyUser(){
		return modifyUser;
	}
	public void setModifyUser(String modifyUser){
		this.modifyUser=modifyUser;
	}
	public Long getModifyTime(){
		return modifyTime;
	}
	public void setModifyTime(Long modifyTime){
		this.modifyTime=modifyTime;
	}
}