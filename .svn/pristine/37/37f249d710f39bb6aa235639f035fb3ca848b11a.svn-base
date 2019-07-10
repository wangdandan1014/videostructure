package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.Alarm;
import com.sensing.core.bean.alarm.req.AlarmReq;
import com.sensing.core.bean.alarm.resp.AlarmDetailResp;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface IAlarmService {

	/**
	 * 统计告警数量 单日告警的数量和未审核的告警数量
	 * 
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月10日 下午6:28:25
	 */
	public Map<String, Object> queryAlarmStatistics() throws Exception;

	/**
	 * 首页的最新四条告警信息查询
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2018年12月7日 下午4:31:07
	 */
	public List<Map<String, Object>> queryAlarmHomePage(String userUuid, Integer pageRows) throws Exception;

	public abstract Alarm saveNewAlarm(Alarm alarm) throws Exception;

	public Alarm updateAlarm(Alarm alarm);

	public abstract Alarm findAlarmById(java.lang.String uuid) throws Exception;

	public abstract void removeAlarm(java.lang.String uuid) throws Exception;

	public AlarmReq queryPage(AlarmReq pager) throws Exception;

	public abstract AlarmDetailResp queryByUuid(String uuid);

	public AlarmReq queryAlarmData(AlarmReq alarmReq);

	public List<String> queryCapUuids(AlarmReq alarmReq);

	public String nextUuid(AlarmReq pager);

	public String prevUuid(AlarmReq pager);

	public AlarmReq queryByAlarmId(AlarmReq pager) throws Exception;

}