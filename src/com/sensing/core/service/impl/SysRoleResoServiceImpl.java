package com.sensing.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sensing.core.dao.ISysResourceDAO;
import com.sensing.core.dao.ISysUserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.SysRoleReso;
import com.sensing.core.dao.ISysRoleResoDAO;
import com.sensing.core.service.ISysRoleResoService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
@Service
public class SysRoleResoServiceImpl implements ISysRoleResoService {


    private static final Log log = LogFactory.getLog(ISysRoleResoService.class);

    @Resource
    public ISysResourceDAO sysResourceDAO;
    @Resource
    public ISysUserDAO sysUserDAO;

    @Resource
    public ISysRoleResoDAO sysRoleResoDAO;

    public SysRoleResoServiceImpl() {
        super();
    }

    @Override
    public SysRoleReso saveNewSysRoleReso(SysRoleReso sysRoleReso) throws Exception {
        try {
            sysRoleResoDAO.saveSysRoleReso(sysRoleReso);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
        return sysRoleReso;
    }

    @Override
    public SysRoleReso updateSysRoleReso(SysRoleReso sysRoleReso) throws Exception {
        try {
            sysRoleResoDAO.updateSysRoleReso(sysRoleReso);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
        return sysRoleReso;
    }

    @Override
    public SysRoleReso findSysRoleResoById(Integer id) throws Exception {
        try {
            return sysRoleResoDAO.getSysRoleReso(id);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
    }

    @Override
    public void removeSysRoleReso(Integer id) throws Exception {
        try {
            sysRoleResoDAO.removeSysRoleReso(id);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
    }

	@Override
	public Pager queryPage(Pager pager) {
		try {
			List<SysRoleReso> list = sysRoleResoDAO.queryList(pager);
			int totalCount = sysRoleResoDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}



}