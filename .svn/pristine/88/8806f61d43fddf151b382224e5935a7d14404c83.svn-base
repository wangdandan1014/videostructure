package com.sensing.core.utils;


/**
 * 
 * com.sensing.core.utils
 * 系统权限认证类
 * 提供生成token/权限校验等方法
 * @author haowenfeng
 * @date 2018年4月23日 下午4:21:37
 */
public class AuthorizationToken {
	private String userId;
	private String userName;
	private String isAdmin;
	private String orgId;
	private String[] orgIdArr;		//当前组织，1级组织，2级组织，3级组织，4级组织，5级组织
	private long loginTime;
	
	
	public AuthorizationToken(String userId,String userName,String isAdmin,long loginTime,String orgId,String[] orgIdArr){
		this.userId =userId;
		this.userName = userName;
		this.isAdmin = isAdmin;
		this.loginTime = loginTime;
		this.orgId = orgId;
		this.orgIdArr = orgIdArr;
	}
	
	/**
	 * 构造函数，用解密后的String构造
	 * @param token
	 */
	public AuthorizationToken(String token){
		String[] str = token.split(";");
		this.userId = str[0];
		this.userName = str[1];
		this.isAdmin = str[2];
		this.loginTime = new Long(str[3]);
		this.orgId = str[4];
		this.orgIdArr = new String[]{str[5],str[6],str[7],str[8],str[9],str[10]};
	}

	
	public String toString(){
		String tokenStr =  userId + ";" 
				+ userName + ";" 
				+ isAdmin + ";"
				+ loginTime + ";"
				+ orgId + ";" ;
		for(int i=0;i<orgIdArr.length;i++){
			tokenStr = tokenStr + orgIdArr[i] + ";";
		}
		tokenStr = tokenStr.substring(0,tokenStr.length()-1);
		return tokenStr;
	}
	
	/**
	 * 生成token加密串
	 * @return
	 * @throws Exception 
	 */
	public String encode() throws Exception{
		String tokenStr = EncodeUtil.encode(this.toString()); 
		return tokenStr; 
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String[] getOrgIdArr() {
		return orgIdArr;
	}
	public void setOrgIdArr(String[] orgIdArr) {
		this.orgIdArr = orgIdArr;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
}
