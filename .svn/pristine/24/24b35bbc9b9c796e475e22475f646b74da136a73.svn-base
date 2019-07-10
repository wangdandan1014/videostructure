package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.JobsStateLog;

/**
 * @author mingxingyu
 */
public interface IJobsStateLogDAO {

	public int saveJobsStateLog(JobsStateLog JobsStateLog) throws Exception;

	public JobsStateLog getJobsStateLog(String uuid) throws Exception;

	public List<JobsStateLog> getJobsStateLogs(String uuid) throws Exception;

	public JobsStateLog getJobsStateLogByStateAndUuid(@Param("state") Integer state, @Param("uuid") String uuid)
			throws Exception;

	public int updateJobsStateLog(JobsStateLog JobsStateLog) throws Exception;

	public int removeJobsStateLog(Long uuid) throws Exception;

}
