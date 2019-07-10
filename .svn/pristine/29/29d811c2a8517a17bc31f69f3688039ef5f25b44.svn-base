package com.sensing.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 布控任务状态操作记录
 * <p>Title: JobsStateLog</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年4月16日
 * @version 1.0
 */
public class JobsStateLog implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Long id;//id
	private String  title;//标题名称
	private String jobsUuid;//任务的uuid
	private Integer jobsState;//任务的状态
	private String memo;//备注
	private String createUser;//创建的用户
	private Date createTime;//创建时间
	public Long getUuid() {
		return id;
	}
	public void setUuid(Long uuid) {
		this.id = uuid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJobsUuid() {
		return jobsUuid;
	}
	public void setJobsUuid(String jobsUuid) {
		this.jobsUuid = jobsUuid;
	}
	public Integer getJobsState() {
		return jobsState;
	}
	public void setJobsState(Integer jobsState) {
		this.jobsState = jobsState;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public JobsStateLog() {
		super();
	}
	public JobsStateLog(Long uuid, String title, String jobsUuid,
			Integer jobsState, String memo, String createUser, Date createTime) {
		super();
		this.id = uuid;
		this.title = title;
		this.jobsUuid = jobsUuid;
		this.jobsState = jobsState;
		this.memo = memo;
		this.createUser = createUser;
		this.createTime = createTime;
	}
}