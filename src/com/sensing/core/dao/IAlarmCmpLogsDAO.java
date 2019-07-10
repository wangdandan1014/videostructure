package com.sensing.core.dao;

import java.util.List;
import com.sensing.core.bean.AlarmCmpLogs;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface IAlarmCmpLogsDAO {
	public int saveAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs) throws Exception;

	public AlarmCmpLogs getAlarmCmpLogs(java.lang.String uuid) throws Exception;

	public int removeAlarmCmpLogs(java.lang.String uuid) throws Exception;

	public int updateAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs) throws Exception;

	public List<AlarmCmpLogs> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

}
