package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.SysOrg;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.ISysOrgService;
import com.sensing.core.dao.ISysOrgDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author wenbo
 */
@Service
public class SysOrgServiceImpl implements ISysOrgService{


	private static final Log log = LogFactory.getLog(ISysOrgService.class);

	@Resource
	public ISysOrgDAO sysOrgDAO;

	public SysOrgServiceImpl() {
		super();
	}

	@Override
	public SysOrg saveNewSysOrg(SysOrg sysOrg)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			sysOrg.setUuid(id);
			sysOrgDAO.saveSysOrg(sysOrg);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysOrg;
	}

	@Override
	public SysOrg updateSysOrg(SysOrg sysOrg)  throws Exception{
		sysOrgDAO.updateSysOrg(sysOrg);
		return sysOrg;
	}

	@Override
	public SysOrg findSysOrgById(java.lang.String uuid) throws Exception{
		try {
			return sysOrgDAO.getSysOrg(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeSysOrg(String uuid) throws Exception{
		try {
		sysOrgDAO.removeSysOrg(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<SysOrg> list = sysOrgDAO.queryList(pager);
			int totalCount = sysOrgDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}