package com.sensing.core.bean;

import java.io.Serializable;

import com.sensing.core.utils.Constants;
/**
 *@author wenbo
 */
@SuppressWarnings("all")
public class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String username;//用户账号名称
	private String password;
	private String realname;//用户姓名
	private String email;
	private String mobile;
	private String addUid;
	private String addUname;
	private java.util.Date addTime;
	private String addTimeStr;
	private java.util.Date lastLoginTime;
	private String lastLoginTimeStr;
	private Integer loginCount;
	private Integer state;	//用户状态 0停用 1启用
	private String[] roleId;
	private String roleIdStr;
	private String description;
	private String stateTag;
	private String orgId;
	private String orgName;
	private Integer isAdmin;
	private String token;//令牌
	private String videoIP;
	private Integer videoPort;
	private String dbHost;
	private String dbName;
	private String dbUser;
	private String dbPasswd;
	private String thriftIp;
	private Integer thriftPort;
	private String ftpUrl;
	private String ftpUserName;
	private String ftpPassword;
	private String loginIp;
	private Short isDeleted;

    public String getRoleIdStr() {
        return roleIdStr;
    }

    public void setRoleIdStr(String roleIdStr) {
        this.roleIdStr = roleIdStr;
    }

    public Short getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getStateTag() {
		return stateTag;
	}
	public void setStateTag(String stateTag) {
		if(state!=null && state==Constants.USER_STATE_USE){
			this.stateTag = "启用";
		}else{
			this.stateTag = "未启用";
		}
		
	}
	
	public String[] getRoleId() {
		return roleId;
	}
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	private String roleName;
	
	private String ip;	//登录ip
	private String port; //端口号

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getRealname(){
		return realname;
	}
	public void setRealname(String realname){
		this.realname=realname;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getAddUid(){
		return addUid;
	}
	public void setAddUid(String addUid){
		this.addUid=addUid;
	}
	public java.util.Date getAddTime(){
		return addTime;
	}
	public void setAddTime(java.util.Date addTime){
		this.addTime=addTime;
	}
	public String getAddTimeStr(){
		if(addTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(addTime);
		}else{
			return "";
		}
	}
	public void setAddTimeStr(String addTimeStr) throws Exception{
		if(addTimeStr!=null&&!addTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.addTime = sdf.parse(addTimeStr);
		}else
			this.addTime = null;
	}
	public java.util.Date getLastLoginTime(){
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime){
		this.lastLoginTime=lastLoginTime;
	}
	public String getLastLoginTimeStr(){
		if(lastLoginTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(lastLoginTime);
		}else{
			return "";
		}
	}
	public void setLastLoginTimeStr(String lastLoginTimeStr) throws Exception{
		if(lastLoginTimeStr!=null&&!lastLoginTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.lastLoginTime = sdf.parse(lastLoginTimeStr);
		}else
			this.lastLoginTime = null;
	}
	public Integer getLoginCount(){
		return loginCount;
	}
	public void setLoginCount(Integer loginCount){
		this.loginCount=loginCount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
		if(state!=null && state==Constants.USER_STATE_USE){
			this.stateTag = "启用";
		}else{
			this.stateTag = "未启用";
		}
	}
	public String getAddUname() {
		return addUname;
	}
	public void setAddUname(String addUname) {
		this.addUname = addUname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVideoIP() {
		return videoIP;
	}
	public void setVideoIP(String videoIP) {
		this.videoIP = videoIP;
	}
	public Integer getVideoPort() {
		return videoPort;
	}
	public void setVideoPort(Integer videoPort) {
		this.videoPort = videoPort;
	}
	public String getDbHost() {
		return dbHost;
	}
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPasswd() {
		return dbPasswd;
	}
	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}
	public String getThriftIp() {
		return thriftIp;
	}
	public void setThriftIp(String thriftIp) {
		this.thriftIp = thriftIp;
	}
	public Integer getThriftPort() {
		return thriftPort;
	}
	public void setThriftPort(Integer thriftPort) {
		this.thriftPort = thriftPort;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	@Override
	public String toString() {
		return "SysUser [uuid=" + uuid + ", username=" + username + ", password=" + password + ", realname=" + realname
				+ ", email=" + email + ", mobile=" + mobile + ", addUid=" + addUid + ", addUname=" + addUname
				+ ", addTime=" + addTime + ", addTimeStr=" + addTimeStr + ", lastLoginTime=" + lastLoginTime
				+ ", lastLoginTimeStr=" + lastLoginTimeStr + ", loginCount=" + loginCount + ", state=" + state
				+ ", roleId=" + roleId + ", description=" + description + ", stateTag=" + stateTag + ", orgId=" + orgId
				+ ", orgName=" + orgName + ", isAdmin=" + isAdmin + ", token=" + token + ", videoIP=" + videoIP
				+ ", videoPort=" + videoPort + ", dbHost=" + dbHost + ", dbName=" + dbName + ", dbUser=" + dbUser
				+ ", dbPasswd=" + dbPasswd + ", thriftIp=" + thriftIp + ", thriftPort=" + thriftPort + ", ftpUrl="
				+ ftpUrl + ", ftpUserName=" + ftpUserName + ", ftpPassword=" + ftpPassword + ", roleName=" + roleName
				+ ", ip=" + ip + ", port=" + port + "]";
	}
	
}