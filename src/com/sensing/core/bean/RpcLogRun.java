package com.sensing.core.bean;

import java.io.Serializable;

import com.sensing.core.utils.ExcelVOAttribute;
/**
 *@author wenbo
 */
@SuppressWarnings("all")
public class RpcLogRun implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	@ExcelVOAttribute(name = "日期时间", column = "B", isExport = true)
	private java.util.Date callTime;
	@ExcelVOAttribute(name = "类型", column = "C", isExport = true)
	private String todo;
	@ExcelVOAttribute(name = "内容", column = "D", isExport = true)
	private String errorMsg;
	@ExcelVOAttribute(name = "序号", column = "A", isExport = true)
	private String num;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Date getCallTime() {
		return callTime;
	}

	public void setCallTime(java.util.Date callTime) {
		this.callTime = callTime;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}