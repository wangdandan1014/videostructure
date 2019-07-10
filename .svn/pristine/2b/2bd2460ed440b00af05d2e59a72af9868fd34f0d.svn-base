package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.JobsChannel;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface IJobsChannelService {

	public abstract JobsChannel saveNewJobsChannel(JobsChannel jobsChannel)  throws Exception;

	public JobsChannel updateJobsChannel(JobsChannel jobsChannel)  throws Exception;

	public abstract JobsChannel findJobsChannelById(java.lang.String uuid) throws Exception;

	public abstract void removeJobsChannel(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public List<JobsChannel> getJobsChannelByChannelUuid(java.lang.String channelUuid);

}