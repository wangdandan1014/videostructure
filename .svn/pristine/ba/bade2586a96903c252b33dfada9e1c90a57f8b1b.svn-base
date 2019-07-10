package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysOrgObject;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ISysOrgObjectDAO {
	public int saveSysOrgObject(SysOrgObject sysOrgObject) throws Exception;

	public SysOrgObject getSysOrgObject(java.lang.String uuid) throws Exception;

	public int removeSysOrgObject(java.lang.String uuid) throws Exception;

	public int updateSysOrgObject(SysOrgObject sysOrgObject) throws Exception;

	public List<SysOrgObject> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public Map<String,Object> qryOwnOrgObject(@Param("objectType")String objectType,@Param("objectId") String objectId,@Param("orgId") String orgId) throws Exception;
	
	public List<Map<String,Object>> qryChildOrgObject(@Param("objectType")String objectType,@Param("objectId") String objectId,@Param("orgId") String orgId) throws Exception;
	
	public int getObjectAuthLevel(Pager pager) throws Exception;
	
	/*查询自动获取的下级组织创建的资源的只读权限，用于补充getObjectAuthLevel*/
	public int getAuthFromChildAuto(Pager pager) throws Exception;
	
	public SysOrgObject query(SysOrgObject sysOrgObject) throws Exception;
	
	public List<SysOrgObject> queryListSysOrgObject(@Param("list")List<String> orgIds,@Param("objectType")String objectType,@Param("objectId") String objectId) throws Exception;

	public int deleteList(@Param("list")List<SysOrgObject> sysOrgObjectList)throws Exception;
	
	public List<Map<String,String>> qryJobIdbyCNorDB(SysOrgObject sysOrgObject) throws Exception;
}
