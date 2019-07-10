package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.AlarmCmpLogs;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.IAlarmCmpLogsService;
import com.sensing.core.dao.IAlarmCmpLogsDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author mingxingyu
 */
@Service
public class AlarmCmpLogsServiceImpl implements IAlarmCmpLogsService{


	private static final Log log = LogFactory.getLog(IAlarmCmpLogsService.class);

	@Resource
	public IAlarmCmpLogsDAO alarmCmpLogsDAO;

	public AlarmCmpLogsServiceImpl() {
		super();
	}

	@Override
	public AlarmCmpLogs saveNewAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			alarmCmpLogs.setUuid(id);
			alarmCmpLogsDAO.saveAlarmCmpLogs(alarmCmpLogs);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return alarmCmpLogs;
	}

	@Override
	public AlarmCmpLogs updateAlarmCmpLogs(AlarmCmpLogs alarmCmpLogs)  throws Exception{
		alarmCmpLogsDAO.updateAlarmCmpLogs(alarmCmpLogs);
		return alarmCmpLogs;
	}

	@Override
	public AlarmCmpLogs findAlarmCmpLogsById(java.lang.String uuid) throws Exception{
		try {
			return alarmCmpLogsDAO.getAlarmCmpLogs(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeAlarmCmpLogs(String uuid) throws Exception{
		try {
		alarmCmpLogsDAO.removeAlarmCmpLogs(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<AlarmCmpLogs> list = alarmCmpLogsDAO.queryList(pager);
			int totalCount = alarmCmpLogsDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}