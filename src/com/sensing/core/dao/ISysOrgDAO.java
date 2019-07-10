package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.SysOrg;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ISysOrgDAO {
	public int saveSysOrg(SysOrg sysOrg) throws Exception;

	public SysOrg getSysOrg(java.lang.String uuid) throws Exception;
	
	public SysOrg getSysOrgByName(java.lang.String orgName) throws Exception;

	public int removeSysOrg(java.lang.String uuid) throws Exception;

	public int updateSysOrg(SysOrg sysOrg) throws Exception;

	public List<SysOrg> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public List<SysOrg> getLowerSysOrg(List<String> parentId) throws Exception;
	
	public List<String> getChildList(String orgId) throws Exception;
	
	public List<String> getAllList() throws Exception;
}
