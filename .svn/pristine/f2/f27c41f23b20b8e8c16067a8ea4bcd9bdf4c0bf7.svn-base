package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.TaskChannel;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ITaskChannelService {

	public abstract TaskChannel saveNewTaskChannel(TaskChannel taskChannel) throws Exception;

	public TaskChannel updateTaskChannel(TaskChannel taskChannel) throws Exception;

	public abstract TaskChannel findTaskChannelById(java.lang.String uuid) throws Exception;

	public abstract void removeTaskChannel(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

	public int deleteByChannelId(String uuid) throws Exception;

	public List<TaskChannel> getTaskChannelByChannelUuid(String channelUuid);

}