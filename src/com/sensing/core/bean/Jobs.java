package com.sensing.core.bean;

import com.sensing.core.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenbo
 */
public class Jobs implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String name; //任务名称
    private Integer type;//任务类型，保留字段
    private Integer state;// 布控状态 10:待启动 20:布控中  30:暂停中 40:休息中 50:已撤控  60:撤控中 70:已完成
    private String jobsType;// 布控类型 1：行人   3：机动车   4：非机动车
    private String capPeople; //抓拍行人属性
    private String capNonmotor;//抓拍非机动车属性
    private String capMotor;//抓拍机动车属性
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private java.util.Date beginDate;//开始日期
    private String beginDateStr;//
    private java.util.Date endDate;//结束日期
    private String endDateStr;//
    private Integer alarmType;//报警方式
    private Double score;//阈值
    private String memo;//任务描述
    private Integer ratifyResult;//审批结果
    private Integer ratifyType;//审批类型  审批类型 0：布控  1：撤控
    private String ratifyUser;//审批人
    private String ratifyMemo;//审批说明
    private java.util.Date ratifyTime;//审批时间
    private String ratifyTimeStr;//
    private Integer isDeleted;//删除标记
    private String createUser;
    private java.util.Date createTime;//创建时间
    private String createTimeStr;
    private String modifyUser;
    private java.util.Date modifyTime;
    private String modifyTimeStr;
    private String runWeek;//运行周期
    private Integer level;//布控级别
    private MotorVehicle motorVehicleObj;
    private List<String> channelUuIds;//关联通道id
    private List<Integer> templatedbIds;//关联模板库id
    private Integer newState;
    private String plateNo;//单一布控的车辆号
    private String obj_uuid;//单目标库的车辆id
    private String cancelJobReason;//申请撤控的理由
    private Integer isPreState;// 为1 将改任务设置为停止
    private Integer alarmSound;//报警的声音

    public Integer getAlarmSound() {
		return alarmSound;
	}

	public void setAlarmSound(Integer alarmSound) {
		this.alarmSound = alarmSound;
	}

	public Integer getIsPreState() {
        return isPreState == null ? 0 : isPreState;
    }

    public void setIsPreState(Integer isPreState) {
        this.isPreState = isPreState;
    }
    public String getCancelJobReason() {
        return cancelJobReason;
    }

    public void setCancelJobReason(String cancelJobReason) {
        this.cancelJobReason = cancelJobReason;
    }

    public String getObj_uuid() {
        return obj_uuid;
    }

    public void setObj_uuid(String obj_uuid) {
        this.obj_uuid = obj_uuid;
    }

    public Integer getRatifyType() {
        return ratifyType == null ? 0 : ratifyType;
    }

    public void setRatifyType(Integer ratifyType) {
        this.ratifyType = ratifyType;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getNewState() {
        return newState;
    }

    public void setNewState(Integer newState) {
        this.newState = newState;
    }

    public List<Integer> getTemplatedbIds() {
        return templatedbIds;
    }

    public void setTemplatedbIds(List<Integer> templatedbIds) {
        this.templatedbIds = templatedbIds;
    }

    public String getRunWeek() {
        return StringUtils.orderString(runWeek);
    }

    public void setRunWeek(String runWeek) {
        this.runWeek = runWeek;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCapNonmotor() {
        return capNonmotor;
    }

    public void setCapNonmotor(String capNonmotor) {
        this.capNonmotor = capNonmotor;
    }

    public List<String> getChannelUuIds() {
        return channelUuIds;
    }

    public void setChannelUuIds(List<String> channelUuIds) {
        this.channelUuIds = channelUuIds;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getJobsType() {
        return StringUtils.orderString(jobsType);
    }

    public void setJobsType(String jobsType) {
        this.jobsType = jobsType;
    }

    public String getCapPeople() {
        return capPeople;
    }

    public void setCapPeople(String capPeople) {
        this.capPeople = capPeople;
    }

    public String getCapMotor() {
        return capMotor;
    }

    public void setCapMotor(String capMotor) {
        this.capMotor = capMotor;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public java.util.Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginDateStr() {
        if (beginDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(beginDate);
        } else {
            return "";
        }
    }

    public void setBeginDateStr(String beginDateStr) throws Exception {
        if (beginDateStr != null && !beginDateStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.beginDate = sdf.parse(beginDateStr);
        } else
            this.beginDate = null;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        if (endDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(endDate);
        } else {
            return "";
        }
    }

    public void setEndDateStr(String endDateStr) throws Exception {
        if (endDateStr != null && !endDateStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.endDate = sdf.parse(endDateStr);
        } else
            this.endDate = null;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getRatifyResult() {
        return ratifyResult == null ? 0 : ratifyResult;
    }

    public void setRatifyResult(Integer ratifyResult) {
        this.ratifyResult = ratifyResult;
    }

    public String getRatifyUser() {
        return ratifyUser;
    }

    public void setRatifyUser(String ratifyUser) {
        this.ratifyUser = ratifyUser;
    }

    public String getRatifyMemo() {
        return ratifyMemo;
    }

    public void setRatifyMemo(String ratifyMemo) {
        this.ratifyMemo = ratifyMemo;
    }

    public java.util.Date getRatifyTime() {
        return ratifyTime;
    }

    public void setRatifyTime(java.util.Date ratifyTime) {
        this.ratifyTime = ratifyTime;
    }

    public String getRatifyTimeStr() {
        if (ratifyTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(ratifyTime);
        } else {
            return "";
        }
    }

    public void setRatifyTimeStr(String ratifyTimeStr) throws Exception {
        if (ratifyTimeStr != null && !ratifyTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.ratifyTime = sdf.parse(ratifyTimeStr);
        } else
            this.ratifyTime = null;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(createTime);
        } else {
            return "";
        }
    }

    public void setCreateTimeStr(String createTimeStr) throws Exception {
        if (createTimeStr != null && !createTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.createTime = sdf.parse(createTimeStr);
        } else
            this.createTime = null;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public java.util.Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTimeStr() {
        if (modifyTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(modifyTime);
        } else {
            return "";
        }
    }

    public void setModifyTimeStr(String modifyTimeStr) throws Exception {
        if (modifyTimeStr != null && !modifyTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.modifyTime = sdf.parse(modifyTimeStr);
        } else
            this.modifyTime = null;
    }

    public MotorVehicle getMotorVehicleObj() {
        return motorVehicleObj;
    }

    public void setMotorVehicleObj(MotorVehicle motorVehicleObj) {
        this.motorVehicleObj = motorVehicleObj;
    }
}