package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.SysProps;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface ISysPropsService {

	public abstract SysProps saveNewSysProps(SysProps sysProps)  throws Exception;

	public SysProps updateSysProps(SysProps sysProps)  throws Exception;

	public abstract SysProps findSysPropsById(java.lang.String uuid) throws Exception;

	public abstract void removeSysProps(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public List<String> queryModuleList() throws Exception;
	
	//获取服务器某配置文件详细信息
	public Map<String,String> getRemoteProps(String ipAddr,String userName,String password,String path,String file) throws Exception;
	
	public String modifyProps(String module,String ipAddr,String userName,String password,String path,String file,Map kv) throws Exception;
	
	//查询某模块所有文件
	public List<SysProps> queryModuleFilesList(String module) throws Exception;
	
	//取指定ip服务器的文件存放到本地 
	public String getFile(String ipAddr,String userName,String password,String path,String file);
	
	//获取某服务器某服务模块的配置信息
	public Map<String,Map> getPropsFromModule(String module,String ip)throws Exception;
	
	public String setConfig(Map fileMapp,List<Map> mapList) throws Exception;
	

}