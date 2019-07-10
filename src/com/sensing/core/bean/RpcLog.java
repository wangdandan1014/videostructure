package com.sensing.core.bean;

import java.io.Serializable;
import java.util.Date;

import com.sensing.core.utils.ExcelVOAttribute;
import com.sensing.core.utils.StringUtils;

/**
 *@author wenbo
 */
@SuppressWarnings("all")
public class RpcLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@ExcelVOAttribute(name = "日期时间", column = "B", isExport = true)
	private java.util.Date callTime;
	private String callTimeStr;
	private String mode;
	@ExcelVOAttribute(name = "模块", column = "C", isExport = true)
	private String module;
	@ExcelVOAttribute(name = "内容", column = "D", isExport = true)
	private String todo;
	private String name;
	private String ip;
	private Integer port;
	private String rpcType;
	private Integer ms;
	private String result;
	private String memo;
	private String errorMsg;
	@ExcelVOAttribute(name = "用户", column = "E", isExport = true)
	private String userName;//用户名
	private Integer type;//日志类型，1:登录；2：操作日志；3：运行日志
	private String createUser;//uuid
	private String callTimeFrom;
	private String callTimeEnd;
	private String roleName;
	@ExcelVOAttribute(name = "序号", column = "A", isExport = true)
	private String num;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCallTimeFrom() {
		return callTimeFrom;
	}
	public void setCallTimeFrom(String callTimeFrom) {
		this.callTimeFrom = callTimeFrom;
	}
	public String getCallTimeEnd() {
		return callTimeEnd;
	}
	public void setCallTimeEnd(String callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public java.util.Date getCallTime(){
		return callTime;
	}
	public void setCallTime(java.util.Date callTime){
		this.callTime=callTime;
	}
	public String getCallTimeStr(){
		if(callTime!=null){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(callTime);
		}else{
			return "";
		}
	}
	public void setCallTimeStr(String callTimeStr) throws Exception{
		if(callTimeStr!=null&&!callTimeStr.trim().equals("")){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.callTime = sdf.parse(callTimeStr);
		}else
			this.callTime = null;
	}
	public String getMode(){
		return mode;
	}
	public void setMode(String mode){
		this.mode=mode;
	}
	public String getModule(){
		return module;
	}
	public void setModule(String module){
		this.module=module;
	}
	public String getTodo(){
		return todo;
	}
	public void setTodo(String todo){
		this.todo=todo;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	public Integer getPort(){
		return port;
	}
	public void setPort(Integer port){
		this.port=port;
	}
	public String getRpcType(){
		return rpcType;
	}
	public void setRpcType(String rpcType){
		this.rpcType=rpcType;
	}
	public Integer getMs(){
		return ms;
	}
	public void setMs(Integer ms){
		this.ms=ms;
	}
	public String getResult(){
		return result;
	}
	public void setResult(String result){
		this.result=result;
	}
	public String getMemo(){
        if (StringUtils.isNotEmpty(memo) && memo.length() >= 2000) {
            memo = memo.substring(0, 1999);
        }
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getErrorMsg() {
        if (StringUtils.isNotEmpty(errorMsg) && errorMsg.length() >= 2000) {
            errorMsg = errorMsg.substring(0, 1999);
        }
        return errorMsg;
    }
	public void setErrorMsg(String errorMsg){
		this.errorMsg=errorMsg;
	}
	public RpcLog(){}
	
	public RpcLog(Date callTime, String mode, String module, String todo,
			String name, String ip, Integer port, String rpcType, Integer ms,
			String result, String memo, String errorMsg,Integer type) {
		super();
		this.callTime = callTime;
		this.mode = mode;
		this.module = module;
		this.todo = todo;
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.rpcType = rpcType;
		this.ms = ms;
		this.result = result;
		this.memo = memo;
		this.errorMsg = errorMsg;
		this.type=type;
	}

	public RpcLog(String todo, String result, String errorMsg, Integer type) {
		this.todo = todo;
		this.result = result;
		this.errorMsg = errorMsg;
		this.type = type;
	}

	@Override
    public String toString() {
        return "RpcLog{" +
                "id=" + id +
                ", callTime=" + callTime +
                ", callTimeStr='" + callTimeStr + '\'' +
                ", mode='" + mode + '\'' +
                ", module='" + module + '\'' +
                ", todo='" + todo + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", rpcType='" + rpcType + '\'' +
                ", ms=" + ms +
                ", result='" + result + '\'' +
                ", memo='" + memo + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", createUser='" + createUser + '\'' +
                ", callTimeFrom='" + callTimeFrom + '\'' +
                ", callTimeEnd='" + callTimeEnd + '\'' +
                ", roleName='" + roleName + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}