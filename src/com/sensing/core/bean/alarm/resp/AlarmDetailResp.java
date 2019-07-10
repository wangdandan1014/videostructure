package com.sensing.core.bean.alarm.resp;

import java.util.Date;
import java.util.List;

public class AlarmDetailResp {
	private String uuid;// 报警uuid
	private String alarmId;// 报警id 序列号
	private List<CapImgResp> capImgList;
	private long alarmTime;// 报警时间
	private String aTime;// 报警时间
	private String jobsUuid;// 布控任务uuid
	private String jobsName;// 布控任务名称
	private Integer jobsType;// 布控类型 0：全部 1：行人 3：机动车 4：非机动车
	private Date startDate;// 布控起始日期
	private Date endDate;// 布控结束日期
	private String startTime;// 布控开始时间
	private String endTime;// 布控结束时间
	private String runWeek;// 布控周期
	private String jobsDate;// 布控时间
	private Integer level;// 布控等级
	private String levelTag;// 布控等级
	private Integer state;// 告警状态 10未核查 20已确认 30已排除 40核查待定
	private String stateTime;// 核查时间
	private String stateMemo;// 核查描述
	private String templateDbName;// 目标库名称
	private String cmpObjUuid;// 比对目标对象
	private String objUrl;// 目标图片
	private String plateNo;// 目标车牌号
	private Integer plateColor;// 车牌颜色
	private String plateColorTag;// 车牌颜色显示值
	private String vehicleColorTag;// 车辆颜色
	private String vehicleBrandTag;// 车辆品牌
	private String vehicleClassTag;// 车辆类型
	private String itemValue;// 报警库类型
	private String itemId;// 报警库类型id

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLevelTag() {
		return levelTag;
	}

	public void setLevelTag(String levelTag) {
		this.levelTag = levelTag;
	}

	public Integer getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(Integer plateColor) {
		this.plateColor = plateColor;
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getPlateColorTag() {
		return plateColorTag;
	}

	public void setPlateColorTag(String plateColorTag) {
		this.plateColorTag = plateColorTag;
	}

	public String getVehicleColorTag() {
		return vehicleColorTag;
	}

	public void setVehicleColorTag(String vehicleColorTag) {
		this.vehicleColorTag = vehicleColorTag;
	}

	public String getVehicleBrandTag() {
		return vehicleBrandTag;
	}

	public void setVehicleBrandTag(String vehicleBrandTag) {
		this.vehicleBrandTag = vehicleBrandTag;
	}

	public String getVehicleClassTag() {
		return vehicleClassTag;
	}

	public void setVehicleClassTag(String vehicleClassTag) {
		this.vehicleClassTag = vehicleClassTag;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getObjUrl() {
		return objUrl;
	}

	public void setObjUrl(String objUrl) {
		this.objUrl = objUrl;
	}

	public String getCmpObjUuid() {
		return cmpObjUuid;
	}

	public void setCmpObjUuid(String cmpObjUuid) {
		this.cmpObjUuid = cmpObjUuid;
	}

	public String getJobsUuid() {
		return jobsUuid;
	}

	public void setJobsUuid(String jobsUuid) {
		this.jobsUuid = jobsUuid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRunWeek() {
		return runWeek;
	}

	public void setRunWeek(String runWeek) {
		this.runWeek = runWeek;
	}

	public String getaTime() {
		return aTime;
	}

	public void setaTime(String aTime) {
		this.aTime = aTime;
	}

	public String getTemplateDbName() {
		return templateDbName;
	}

	public void setTemplateDbName(String templateDbName) {
		this.templateDbName = templateDbName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<CapImgResp> getCapImgList() {
		return capImgList;
	}

	public void setCapImgList(List<CapImgResp> capImgList) {
		this.capImgList = capImgList;
	}

	public long getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(long alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getJobsName() {
		return jobsName;
	}

	public void setJobsName(String jobsName) {
		this.jobsName = jobsName;
	}

	public Integer getJobsType() {
		return jobsType;
	}

	public void setJobsType(Integer jobsType) {
		this.jobsType = jobsType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getJobsDate() {
		return jobsDate;
	}

	public void setJobsDate(String jobsDate) {
		this.jobsDate = jobsDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateTime() {
		return stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public String getStateMemo() {
		return stateMemo;
	}

	public void setStateMemo(String stateMemo) {
		this.stateMemo = stateMemo;
	}

}
