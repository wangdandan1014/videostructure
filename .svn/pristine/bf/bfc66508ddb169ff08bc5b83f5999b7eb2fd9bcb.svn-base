package com.sensing.core.utils;


/**
 * 系统登录验证类，生成token
 * <p>Title: LoginToken</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年3月21日
 * @version 1.0
 */
public class LoginToken {
	private String userId;//用户的uuid
	private long loginTime;//登录的时间
	
	
	public LoginToken(String userId,long loginTime){
		this.userId =userId;
		this.loginTime = loginTime;
	}
	
	/**
	 * 构造函数，用解密后的String构造
	 * @param token
	 */
	public LoginToken(String token){
		String[] str = token.split(";");
		this.userId = str[0];
		this.loginTime = new Long(str[3]);
	}

	
	public String toString(){
		String tokenStr =  userId + ";" + loginTime;
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
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
}
