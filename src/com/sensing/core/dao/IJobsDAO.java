package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.Jobs;
import com.sensing.core.bean.JobsTemplateDb;
import com.sensing.core.bean.Person;
import com.sensing.core.bean.job.req.UpdateJobReq;
import com.sensing.core.bean.job.req.UpdateOperateReq;
import com.sensing.core.bean.job.resp.JobListResp;
import com.sensing.core.bean.job.resp.JobRatifyListResp;
import com.sensing.core.utils.Pager;
import org.apache.ibatis.annotations.Param;

/**
 * @author wenbo
 */
public interface IJobsDAO {
	public int saveJobs(Jobs jobs);

	public Jobs getJobs(java.lang.String uuid);

	public int removeJobs(java.lang.String uuid);

	public int updateCommon(Jobs jobs);

	public List<JobListResp> jobList(Pager pager);

	public int jobListCount(Pager pager);

	public List<JobListResp> jobListByName(Pager pager);

	public int jobListByNameCount(Pager pager);

	/**
	 * 查询布控列表简单属性
	 *
	 * @return
	 */
	public List<Jobs> jobListCommon();

	public JobListResp info(UpdateJobReq pager);

	public List<JobRatifyListResp> ratifyList(Pager pager);

	public int ratifyListCount(Pager pager);

	public int selectTemplatedbIdCount(Integer templatedbId);

	public int updateOperate(UpdateOperateReq req);

	public List<JobListResp> getJobsChannelByUuid(UpdateJobReq req);

	public int getJobsByJobName(@Param("name") String name, @Param("uuid") String uuid);

	public int saveJobsTempleteBetch(List<JobsTemplateDb> list);

	public int removeTempleteChannel(java.lang.String uuid);

	public int ratifyJob(UpdateJobReq req);

	public List<Jobs> getUpdateStateJob(List<Integer> list);

	public int updateStateBetch(List<Jobs> runningList);

	public int selectTemplatedbIdCountByObjUuid(String objUuid);

	public String getObjUuidByJobsUuid(String jobsUuid);

    public List<Map<String, String>> getPlateNoByJobUuid(@Param("jobsUuids")List<String> jobsUuids);
}
