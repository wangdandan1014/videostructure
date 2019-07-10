package com.sensing.core.bean.alarm.req;

import com.sensing.core.utils.Pager;

public class AlarmReq extends Pager {

	private static final long serialVersionUID = 1L;
	private String[] jobUuids;
	private String[] deviceIds;
	private String[] uuids;// 报警uuids

	public String[] getUuids() {
		return uuids;
	}

	public void setUuids(String[] uuids) {
		this.uuids = uuids;
	}

	public String[] getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(String[] deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String[] getJobUuids() {
		return jobUuids;
	}

	public void setJobUuids(String[] jobUuids) {
		this.jobUuids = jobUuids;
	}

}
