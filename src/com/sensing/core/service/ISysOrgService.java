package com.sensing.core.service;

import com.sensing.core.bean.SysOrg;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface ISysOrgService {

	public abstract SysOrg saveNewSysOrg(SysOrg sysOrg)  throws Exception;

	public SysOrg updateSysOrg(SysOrg sysOrg)  throws Exception;

	public abstract SysOrg findSysOrgById(java.lang.String uuid) throws Exception;

	public abstract void removeSysOrg(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

}