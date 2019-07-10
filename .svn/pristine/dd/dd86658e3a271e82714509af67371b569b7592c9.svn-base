package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysProps;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ISysPropsDAO {
	public int saveSysProps(SysProps sysProps) throws Exception;

	public SysProps getSysProps(java.lang.String uuid) throws Exception;

	public int removeSysProps(java.lang.String uuid) throws Exception;

	public int updateSysProps(SysProps sysProps) throws Exception;

	public List<SysProps> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;
	
	//查询所有服务模块
	public List<String> queryModuleList() throws Exception;
	
	//查询某模块所有文件
	public List<SysProps> queryModuleFilesList(String module) throws Exception;
	//根据服务模块、文件名称、属性名称查询配置信息
	public SysProps queryPropByModuleAndFileNameAndKey(@Param("module")String module,@Param("fileName")String fileName,@Param("key")String key) throws Exception;

}
