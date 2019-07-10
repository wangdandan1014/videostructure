package com.sensing.core.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysOrgObject;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface ISysOrgObjectService {

	public abstract SysOrgObject saveNewSysOrgObject(SysOrgObject sysOrgObject)  throws Exception;

	public SysOrgObject updateSysOrgObject(SysOrgObject sysOrgObject)  throws Exception;

	public abstract SysOrgObject findSysOrgObjectById(java.lang.String uuid) throws Exception;

	public abstract void removeSysOrgObject(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public Map<String,Object> query(String objectType,String objectId,String orgId,Integer ownLevel) throws Exception;
	
	public void saveSysOrgObject(JSONObject json,String orgId,int level) throws Exception;

	
}