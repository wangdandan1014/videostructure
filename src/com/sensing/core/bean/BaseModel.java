package com.sensing.core.bean;


/**
 * 
 * com.sensing.core.bean
 * 基础Bean类
 * @author haowenfeng
 * @date 2018年4月21日 下午2:30:14
 */
public class BaseModel {
	
	
	private String orgId;
	private String orgId1;
	private String orgId2;
	private String orgId3;
	private String orgId4;
	private String orgId5;
	private String createUser;
	private String modifyUser;
	private int isPrivate = 0;
	private String token;//令牌
	
	public int getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgId1() {
		return orgId1;
	}
	public void setOrgId1(String orgId1) {
		this.orgId1 = orgId1;
	}
	public String getOrgId2() {
		return orgId2;
	}
	public void setOrgId2(String orgId2) {
		this.orgId2 = orgId2;
	}
	public String getOrgId3() {
		return orgId3;
	}
	public void setOrgId3(String orgId3) {
		this.orgId3 = orgId3;
	}
	public String getOrgId4() {
		return orgId4;
	}
	public void setOrgId4(String orgId4) {
		this.orgId4 = orgId4;
	}
	public String getOrgId5() {
		return orgId5;
	}
	public void setOrgId5(String orgId5) {
		this.orgId5 = orgId5;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
