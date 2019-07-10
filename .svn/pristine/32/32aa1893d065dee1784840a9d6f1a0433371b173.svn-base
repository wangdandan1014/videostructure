package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.JobsChannel;
import com.sensing.core.bean.JobsChannelTemp;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IJobsChannelDAO {

    public int saveJobsChannel(JobsChannel jobsChannel);

    public int saveJobsChannelBetch(List<JobsChannel> list);

    public JobsChannel getJobsChannel(java.lang.String uuid);
   
    public List<JobsChannel> getJobsChannelByChannelUuid(java.lang.String channelUuid);

    public List<JobsChannelTemp> getJobsChannelByJobUuid(List<String> list);

    public int removeJobsChannel(java.lang.String uuid);

    public int removeJobsChannelByJobUuid(java.lang.String uuid);

    public int updateJobsChannel(JobsChannel jobsChannel);

    public List<JobsChannel> queryList(Pager pager);

    public int selectCount(Pager pager);

}
