package com.sensing.core.alarm;


/**
 * 
 * <p>Title: AlarmInfo</p>
 * <p>Description:告警对象</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月19日
 * @version 1.0
 */
public class AlarmInfo {
	
	//告警的uuid
	private String uuid;
	//已保存的告警的分数
	private Double highestsScore;
	// 告警时间
	private Long alarmTime;
	// 任务的uuid
	private String jobsUuid;
	//任务的名称
	private String jobName;
	//布控任务的级别
	private Integer jobLevel;
	//布控任务的声音
	private Integer alarmSound;
	//出现次数
	private Integer matchedCount;
	//是否告警的标记
	private boolean alarmFlag = false;
	//告警的状态
	private int state;//10未核查,20已确认,30已排除,40核查待定
	//告警id 序列号
	private String alarmId; 
	
	public String getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Double getHighestsScore() {
		return highestsScore;
	}
	public void setHighestsScore(Double highestsScore) {
		this.highestsScore = highestsScore;
	}
	public Long getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Long alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getJobsUuid() {
		return jobsUuid;
	}
	public void setJobsUuid(String jobsUuid) {
		this.jobsUuid = jobsUuid;
	}
	public boolean isAlarmFlag() {
		return alarmFlag;
	}
	public void setAlarmFlag(boolean alarmFlag) {
		this.alarmFlag = alarmFlag;
	}
	public Integer getMatchedCount() {
		return matchedCount;
	}
	public void setMatchedCount(Integer matchedCount) {
		this.matchedCount = matchedCount;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Integer getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}
	public Integer getAlarmSound() {
		return alarmSound;
	}
	public void setAlarmSound(Integer alarmSound) {
		this.alarmSound = alarmSound;
	}
	
}
