package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.TaskChannel;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.ITaskChannelService;
import com.sensing.core.dao.ITaskChannelDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author wenbo
 */
@Service
public class TaskChannelServiceImpl implements ITaskChannelService{


	private static final Log log = LogFactory.getLog(ITaskChannelService.class);

	@Resource
	public ITaskChannelDAO taskChannelDAO;

	public TaskChannelServiceImpl() {
		super();
	}

	@Override
	public TaskChannel saveNewTaskChannel(TaskChannel taskChannel)  throws Exception{
		try {
			String uuid = UuidUtil.getUuid();
			taskChannel.setUuid(uuid);
			taskChannelDAO.saveTaskChannel(taskChannel);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return taskChannel;
	}

	@Override
	public TaskChannel updateTaskChannel(TaskChannel taskChannel)  throws Exception{
		taskChannelDAO.updateTaskChannel(taskChannel);
		return taskChannel;
	}

	@Override
	public TaskChannel findTaskChannelById(java.lang.String uuid) throws Exception{
		try {
			return taskChannelDAO.getTaskChannel(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeTaskChannel(String uuid) throws Exception{
		try {
		taskChannelDAO.removeTaskChannel(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<TaskChannel> list = taskChannelDAO.queryList(pager);
			int totalCount = taskChannelDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	@Override
	public int deleteByChannelId(String uuid) throws Exception {
		int c=taskChannelDAO.deleteByChannelId(uuid);
		return c;
	}

	@Override
	public List<TaskChannel> getTaskChannelByChannelUuid(String channelUuid) {
		List<TaskChannel> list = taskChannelDAO.getTaskChannelByChannelUuid(channelUuid);
		return list;
	}

}