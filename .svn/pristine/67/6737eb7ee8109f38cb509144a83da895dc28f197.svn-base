package com.sensing.core.bean.job.resp;

import com.google.common.base.Joiner;
import com.sensing.core.bean.JobsStateLog;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.StringUtils;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JobListResp implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String jobName;
	private String memo;
	// 布控人名称
	private String createUsername;
	// 布控人uuid
	private String createUser;
	// 创建布控的时间
	private Date createTime;
	private String ratifyUsername;
	// 布控状态 10:待启动 20:布控中 30:暂停中 40:休息中 50:已撤控 60:撤控中 70:已完成
	private Integer state;
	private String stateStr;
	private String ratifyUser;// 审批人uuid
	private Integer ratifyResult;
	private String ratifyMemo;
	private String ratifyType;// 审批类型 0：布控 1：撤控
	private String ratifyTypeStr;// 审批类型 0：布控 1：撤控
	// 审批的时间
	private Date ratifyTime;
	private Integer isDeleted;
	private Integer level;
	private String levelStr;
	// 阈值
	private Integer score;
	// 通道名称
	private String channelname;
	private Integer channelCount;
	private String templatename;
	// 报警次数
	private Integer alarmCount;
	// 布控内容
	private String jobContent;

	private String runWeek;
	private String runWeekStr;
	private String beginTime;
	private String endTime;

	private Date beginDate;
	private String beginDateStr;
	private Date endDate;
	private String endDateStr;
	// 是否订阅
	private Integer subscribe;

	private List<String> channelUuIds;
	private List<Integer> templatedbIds;

	private String channelUuIdsStr;
	private String templatedbIdsStr;
	// 如果是单目标库，返回的是车辆的uuid
	private String obj_uuid;
	private String plateNo;
	private String cancelJobReason;
	private Integer alarmSound;// 报警声音
	private List<JobsStateLog> jobsStateLogs;

	
	public Integer getChannelCount() {
		return channelCount;
	}

	public void setChannelCount(Integer channelCount) {
		this.channelCount = channelCount;
	}

	public List<JobsStateLog> getJobsStateLogs() {
		return jobsStateLogs;
	}

	public void setJobsStateLogs(List<JobsStateLog> jobsStateLogs) {
		this.jobsStateLogs = jobsStateLogs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRatifyTime() {
		return ratifyTime;
	}

	public void setRatifyTime(Date ratifyTime) {
		this.ratifyTime = ratifyTime;
	}

	public Integer getAlarmSound() {
		return alarmSound;
	}

	public void setAlarmSound(Integer alarmSound) {
		this.alarmSound = alarmSound;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCancelJobReason() {
		return cancelJobReason;
	}

	public void setCancelJobReason(String cancelJobReason) {
		this.cancelJobReason = cancelJobReason;
	}

	public String getRatifyTypeStr() {
		if (StringUtils.isNotEmpty(ratifyType)) {
			int i = Integer.parseInt(ratifyType);
			if (i == 0) {
				return "布控";
			} else {
				return "撤控";
			}
		}
		return null;

	}

	public void setRatifyTypeStr(String ratifyTypeStr) {
		this.ratifyTypeStr = ratifyTypeStr;
	}

	public String getRunWeekStr() {

		return StringUtils.getWeekString(runWeek, ",");
	}

	public void setRunWeekStr(String runWeekStr) {
		this.runWeekStr = runWeekStr;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getObj_uuid() {
		return obj_uuid;
	}

	public void setObj_uuid(String obj_uuid) {
		this.obj_uuid = obj_uuid;
	}

	public String getChannelUuIdsStr() {
		return channelUuIdsStr;
	}

	public void setChannelUuIdsStr(String channelUuIdsStr) {
		this.channelUuIdsStr = channelUuIdsStr;
	}

	public String getTemplatedbIdsStr() {

		return templatedbIdsStr;
	}

	public void setTemplatedbIdsStr(String templatedbIdsStr) {
		this.templatedbIdsStr = templatedbIdsStr;
	}

	public String getRatifyUser() {
		return ratifyUser;
	}

	public void setRatifyUser(String ratifyUser) {
		this.ratifyUser = ratifyUser;
	}

	public List<String> getChannelUuIds() {
		if (StringUtils.isNotEmpty(channelUuIdsStr)) {
			channelUuIds = Arrays.asList(channelUuIdsStr.split(","));
		}
		return channelUuIds;
	}

	public void setChannelUuIds(List<String> channelUuIds) {
		this.channelUuIds = channelUuIds;
	}

	public List<Integer> getTemplatedbIds() {
		if (StringUtils.isNotEmpty(templatedbIdsStr)) {
			List<String> strings = Arrays.asList(templatedbIdsStr.split(","));
			templatedbIds = strings.stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
		}
		return templatedbIds;
	}

	public void setTemplatedbIds(List<Integer> templatedbIds) {
		this.templatedbIds = templatedbIds;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRatifyMemo() {
		return ratifyMemo;
	}

	public void setRatifyMemo(String ratifyMemo) {
		this.ratifyMemo = ratifyMemo;
	}

	public String getRatifyType() {
		return ratifyType;
	}

	public void setRatifyType(String ratifyType) {
		this.ratifyType = ratifyType;
	}

	public String getRatifyUsername() {
		return ratifyUsername;
	}

	public void setRatifyUsername(String ratifyUsername) {
		this.ratifyUsername = ratifyUsername;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getStateStr() {
		return Constants.JOB_STATE_MAP.get(state);
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getEndDateStr() {
		if (endDate != null) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(endDate);
		} else {
			return "";
		}
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getBeginDateStr() {
		if (beginDate != null) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(beginDate);
		} else {
			return "";
		}
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getRunWeek() {
		return runWeek;
	}

	public void setRunWeek(String runWeek) {
		this.runWeek = runWeek;
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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	public String getLevelStr() {
		if (level == null || level.intValue() == 1) {
			return "一级";
		} else if (level.intValue() == 2) {
			return "二级";
		} else if (level.intValue() == 3) {
			return "三级";
		} else {
			return "未知";
		}
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public Integer getAlarmCount() {
		return alarmCount == null ? 0 : alarmCount;
	}

	public void setAlarmCount(Integer alarmCount) {
		this.alarmCount = alarmCount;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getRatifyResult() {
		return ratifyResult;
	}

	public void setRatifyResult(Integer ratifyResult) {
		this.ratifyResult = ratifyResult;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
}
