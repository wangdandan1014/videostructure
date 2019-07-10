package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.JobsCap;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IJobsCapDAO {
    public int saveJobsCap(JobsCap jobsCap);

    public int saveJobsCapBetch(JobsCap jobsCap);

    public JobsCap getJobsCap(java.lang.String uuid);

    public int removeJobsCap(java.lang.String uuid);

    public int updateJobsCap(JobsCap jobsCap);

    public List<JobsCap> queryList(Pager pager);

    public int selectCount(Pager pager);

}
