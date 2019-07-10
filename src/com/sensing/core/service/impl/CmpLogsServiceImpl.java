package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.CmpLogs;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.ICmpLogsService;
import com.sensing.core.dao.ICmpLogsDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author mingxingyu
 */
@Service
public class CmpLogsServiceImpl implements ICmpLogsService{


	private static final Log log = LogFactory.getLog(ICmpLogsService.class);

	@Resource
	public ICmpLogsDAO cmpLogsDAO;

	public CmpLogsServiceImpl() {
		super();
	}

	@Override
	public CmpLogs saveNewCmpLogs(CmpLogs cmpLogs)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			cmpLogs.setUuid(id);
			cmpLogsDAO.saveCmpLogs(cmpLogs);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return cmpLogs;
	}

	@Override
	public CmpLogs updateCmpLogs(CmpLogs cmpLogs)  throws Exception{
		cmpLogsDAO.updateCmpLogs(cmpLogs);
		return cmpLogs;
	}

	@Override
	public CmpLogs findCmpLogsById(java.lang.String uuid) throws Exception{
		try {
			return cmpLogsDAO.getCmpLogs(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeCmpLogs(String uuid) throws Exception{
		try {
		cmpLogsDAO.removeCmpLogs(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<CmpLogs> list = cmpLogsDAO.queryList(pager);
			int totalCount = cmpLogsDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}