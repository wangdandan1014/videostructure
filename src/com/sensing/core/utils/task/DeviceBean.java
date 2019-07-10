package com.sensing.core.utils.task;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务的通道对象
 * <p>Title: DeviceBean</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年1月18日
 * @version 1.0
 */
public class DeviceBean {

	private String uuid;
	private List<Integer> taskCapTypeList = new ArrayList<Integer>();
	private List<Integer> jobCapTypeList = new ArrayList<Integer>();
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public List<Integer> getTaskCapTypeList() {
		return taskCapTypeList;
	}
	public void setTaskCapTypeList(List<Integer> taskCapTypeList) {
		this.taskCapTypeList = taskCapTypeList;
	}
	public List<Integer> getJobCapTypeList() {
		return jobCapTypeList;
	}
	public void setJobCapTypeList(List<Integer> jobCapTypeList) {
		this.jobCapTypeList = jobCapTypeList;
	}
}
