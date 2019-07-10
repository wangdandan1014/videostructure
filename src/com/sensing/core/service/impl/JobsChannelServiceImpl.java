package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.JobsChannel;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.IJobsChannelService;
import com.sensing.core.dao.IJobsChannelDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wenbo
 */
@Service
public class JobsChannelServiceImpl implements IJobsChannelService {

	private static final Log log = LogFactory.getLog(IJobsChannelService.class);

	@Resource
	public IJobsChannelDAO jobsChannelDAO;

	public JobsChannelServiceImpl() {
		super();
	}

	@Override
	public JobsChannel saveNewJobsChannel(JobsChannel jobsChannel) throws Exception {
		try {
			String uuid = UuidUtil.getUuid();
			jobsChannel.setUuid(uuid);
			jobsChannelDAO.saveJobsChannel(jobsChannel);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return jobsChannel;
	}

	@Override
	public JobsChannel updateJobsChannel(JobsChannel jobsChannel) throws Exception {
		jobsChannelDAO.updateJobsChannel(jobsChannel);
		return jobsChannel;
	}

	@Override
	public JobsChannel findJobsChannelById(java.lang.String uuid) throws Exception {
		try {
			return jobsChannelDAO.getJobsChannel(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeJobsChannel(String uuid) throws Exception {
		try {
			jobsChannelDAO.removeJobsChannel(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception {
		try {
			List<JobsChannel> list = jobsChannelDAO.queryList(pager);
			int totalCount = jobsChannelDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	@Override
	public List<JobsChannel> getJobsChannelByChannelUuid(String channelUuid) {
		List<JobsChannel> list = jobsChannelDAO.getJobsChannelByChannelUuid(channelUuid);
		return list;
	}

}