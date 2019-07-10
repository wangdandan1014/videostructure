package com.sensing.core.service;

import com.sensing.core.bean.AlarmCmpLogs;
import com.sensing.core.utils.Pager;

/**
 *@author mingxingyu
 */
public interface IAlarmCmpLogsService {

	public abstract AlarmCmpLogs saveNewAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs)  throws Exception;

	public AlarmCmpLogs updateAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs)  throws Exception;

	public abstract AlarmCmpLogs findAlarmCmpLogsById(java.lang.String uuid) throws Exception;

	public abstract void removeAlarmCmpLogs(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

}