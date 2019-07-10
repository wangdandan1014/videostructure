package com.sensing.core.service;

import com.sensing.core.bean.Jobs;
import com.sensing.core.bean.job.req.JobListCountReq;
import com.sensing.core.bean.job.req.UpdateJobReq;
import com.sensing.core.bean.job.req.UpdateOperateReq;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

/**
 * @author wenbo
 */
public interface IJobsService {

	public ResponseBean saveNewJobs(Jobs jobs);

	public ResponseBean updateCommon(Jobs jobs);

	public Jobs findJobsById(java.lang.String uuid);

	public void removeJobs(java.lang.String uuid);

	public ResponseBean updateOperate(UpdateOperateReq req);

	public Pager jobList(Pager pager);

	public ResponseBean jobListCommon();

	public int selectTemplatedbIdCount(Integer templatedbId);

	Pager ratifyList(Pager pager);

	ResponseBean ratifyJob(UpdateJobReq req);

	ResponseBean jobListCount(JobListCountReq req);

	ResponseBean info(UpdateJobReq req);

	ResponseBean getJobsUnReadMsgCount(String uuid);

	ResponseBean getJobsChannelByUuid(UpdateJobReq req);

	public int selectTemplatedbIdCountByObjUuid(String uuid);

	public String getObjUuidByJobsUuid(String jobsUuid);

}