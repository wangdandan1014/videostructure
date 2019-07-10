package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.JobsCap;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.IJobsCapService;
import com.sensing.core.dao.IJobsCapDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author wenbo
 */
@Service
public class JobsCapServiceImpl implements IJobsCapService{


	private static final Log log = LogFactory.getLog(IJobsCapService.class);

	@Resource
	public IJobsCapDAO jobsCapDAO;

	public JobsCapServiceImpl() {
		super();
	}

	@Override
	public JobsCap saveNewJobsCap(JobsCap jobsCap)  throws Exception{
		try {
			String uuid = UuidUtil.getUuid();
			jobsCap.setUuid(uuid);
			jobsCapDAO.saveJobsCap(jobsCap);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return jobsCap;
	}

	@Override
	public JobsCap updateJobsCap(JobsCap jobsCap)  throws Exception{
		jobsCapDAO.updateJobsCap(jobsCap);
		return jobsCap;
	}

	@Override
	public JobsCap findJobsCapById(java.lang.String uuid) throws Exception{
		try {
			return jobsCapDAO.getJobsCap(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeJobsCap(String uuid) throws Exception{
		try {
		jobsCapDAO.removeJobsCap(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<JobsCap> list = jobsCapDAO.queryList(pager);
			int totalCount = jobsCapDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}