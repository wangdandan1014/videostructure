package com.sensing.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.dao.ISysSubscribeDAO;
import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.service.ISysSubscribeService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;

/**
 *@author wenbo
 */
@Service
public class SysSubscribeServiceImpl implements ISysSubscribeService{


	private static final Log log = LogFactory.getLog(ISysSubscribeService.class);

	@Resource
	public ISysSubscribeDAO sysSubscribeDAO;
	@Resource
	public ISysUserDAO sysUserDAO;

	public SysSubscribeServiceImpl() {
		super();
	}

	@Override
	public SysSubscribe saveNewSysSubscribe(SysSubscribe sysSubscribe)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			sysSubscribe.setUuid(id);
			sysSubscribe.setSubTime(new Date());
//			Jobs job = jobsDAO.getJobs(sysSubscribe.getJobId());
//			if(job!=null){
//				sysSubscribe.setState(job.getState());
//			}else{
//				sysSubscribe.setState(0);
//			}
			sysSubscribeDAO.saveSysSubscribe(sysSubscribe);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysSubscribe;
	}

	@Override
	public SysSubscribe updateSysSubscribe(SysSubscribe sysSubscribe)  throws Exception{
//		Jobs job = jobsDAO.getJobs(sysSubscribe.getJobId());
//		if(job!=null){
//			sysSubscribe.setState(job.getState());
//		}else{
//			sysSubscribe.setState(0);
//		}
		sysSubscribeDAO.updateSysSubscribe(sysSubscribe);
		return sysSubscribe;
	}

	@Override
	public SysSubscribe findSysSubscribeById(java.lang.String uuid) throws Exception{
		try {
			return sysSubscribeDAO.getSysSubscribe(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeSysSubscribe(String uuid) throws Exception{
		try {
			sysSubscribeDAO.removeSysSubscribe(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<SysSubscribe> list = sysSubscribeDAO.queryList(pager);
			int totalCount = sysSubscribeDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}
	
	/**
	 * 查询统计数量
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年1月7日 下午3:53:38
	 */
	public int queryCount(Pager pager) throws Exception{
		try {
			int totalCount = sysSubscribeDAO.selectCount(pager);
			return totalCount;
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}
	/**
	 * 取消告警/报警订阅
	 */
	public int cancleSub(SysSubscribe SysSubscribe) throws Exception{
		try {
			return sysSubscribeDAO.removeSysSubscribeByUidAndJobId(SysSubscribe);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public int removeSubscribe(String uid, String jobId,Integer subType)throws Exception {
		try {
			return sysSubscribeDAO.removeSubscribe(uid, jobId,subType);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	/**
	 * 根据条件查找订阅信息
	 * @param paramSysSubscribe
	 * @return
	 * @throws Exception       
	 * @author dongsl
	 * @Date 2017年8月23日下午1:51:08
	 */
	@Override
	public List<SysSubscribe> queryByParam(String uid,String jobId,Integer subType)throws Exception {
		return sysSubscribeDAO.queryByParam(uid,jobId,subType);
	}
	
	/**
	 * @Description: 修改任务状态时，更新订阅信息 
	 * @param jobId
	 * @param state
	 * @return
	 * @throws Exception       
	 * @author dongsl
	 * @Date 2017年10月20日下午4:44:36
	 */
	public void updateSubscribeByJob(String jobId,Integer state) throws Exception{
		List<SysSubscribe> list = sysSubscribeDAO.queryByParam(null, jobId, null);
		int s = 0;
		if(state == Constants.JOBS_STATE_CLOSED){	//任务关闭，则将订阅状态置为0
			s = Constants.JOBS_STATE_CLOSED;
		}else{
			s = Constants.JOBS_STATE_USE;
		}
		try{
			for(SysSubscribe ss : list){
				ss.setState(s);
				sysSubscribeDAO.updateSysSubscribe(ss);
			}
		}catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}
	
	/**
	 * 根据用户的角色自动订阅告警或是报警  
	 * @param uid 用户的uid
	 * @param jobId 任务的id
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年12月11日 下午3:09:45
	 */
//	public void saveByUserRole(String uid,String jobId) throws Exception{
//		try {
//			//查询用户
//	    	SysUser user = sysUserDAO.getSysUser(uid);
//	    	Date date = new Date();
//	    	if( user != null ){
//	    		//管理组的用户自动订阅告警
//	    		//操作员、管理员、超级管理员
//	    		if ( user.getRoleId() == Constants.SYS_ROLE_OPERATOR || user.getRoleId() == Constants.SYS_ROLE_ADMIN || user.getRoleId() == Constants.SYS_ROLE_SUPERADMIN ) {
//	    			SysSubscribe originalSub = new SysSubscribe();
//	    			originalSub.setUuid(UuidUtil.getUuid());
//	    			originalSub.setJobId(jobId);
//	    			originalSub.setState(Constants.JOBS_STATE_USE);
//	    			originalSub.setSubTime(date);
//	    			originalSub.setSubType(Constants.SUBSCRIBE_TYPE_ORIGINAL);
//	    			originalSub.setUid(uid);
//	    			sysSubscribeDAO.saveSysSubscribe(originalSub);
//				}
//	    		// 普通用户的角色自动订阅报警
//	    		if ( user.getRoleId() == Constants.SYS_ROLE_COMMON ) {
//	    			SysSubscribe secondSub = new SysSubscribe();
//	    			secondSub.setUuid(UuidUtil.getUuid());
//	    			secondSub.setJobId(jobId);
//	    			secondSub.setState(Constants.JOBS_STATE_USE);
//	    			secondSub.setSubTime(date);
//	    			secondSub.setSubType(Constants.SUBSCRIBE_TYPE_SEC);
//	    			secondSub.setUid(uid);
//	    			sysSubscribeDAO.saveSysSubscribe(secondSub);
//				}
//	    	}
//		} catch (Exception e) {
//			log.error("根据用户角色信息自动订阅告警或是报警发生异常！"+e.getMessage());
//			e.printStackTrace();
//			throw new BussinessException(e);
//		}
//
//	}

}