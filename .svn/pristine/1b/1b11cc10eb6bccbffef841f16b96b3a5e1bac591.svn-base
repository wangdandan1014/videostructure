package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface ISysSubscribeService {

	public abstract SysSubscribe saveNewSysSubscribe(SysSubscribe sysSubscribe)  throws Exception;

	public SysSubscribe updateSysSubscribe(SysSubscribe sysSubscribe)  throws Exception;

	public abstract SysSubscribe findSysSubscribeById(java.lang.String uuid) throws Exception;

	public abstract void removeSysSubscribe(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public int cancleSub(SysSubscribe SysSubscribe) throws Exception;
	
	public List<SysSubscribe> queryByParam(String uid,String jobId,Integer subType) throws Exception;
	
	public int removeSubscribe(String uid,String jobId,Integer subType) throws Exception;
	
	public void updateSubscribeByJob(String jobId,Integer state) throws Exception;
	
	/**
	 * 查询统计数量
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年1月7日 下午3:53:38
	 */
	public int queryCount(Pager pager) throws Exception;

	/**
	 * 根据用户的角色自动订阅告警或是报警
	 * @param uid 用户的uid
	 * @param jobId 任务的id
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年12月11日 下午3:09:45
	 */
//	public void saveByUserRole(String uid,String jobId) throws Exception;
}