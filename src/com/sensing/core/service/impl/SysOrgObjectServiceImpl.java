package com.sensing.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysOrg;
import com.sensing.core.bean.SysOrgObject;
import com.sensing.core.dao.ISysOrgDAO;
import com.sensing.core.dao.ISysOrgObjectDAO;
import com.sensing.core.dao.ISysSubscribeDAO;
import com.sensing.core.service.ISysOrgObjectService;
import com.sensing.core.service.ISysUserService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;

/**
 *@author wenbo
 */
@Service
public class SysOrgObjectServiceImpl implements ISysOrgObjectService{


	private static final Log log = LogFactory.getLog(ISysOrgObjectService.class);

	@Resource
	public ISysOrgObjectDAO sysOrgObjectDAO;
	@Resource
	private ISysUserService sysUserService;
	@Resource
	private ISysOrgDAO sysOrgDAO;
	@Resource
	private ISysSubscribeDAO sysSubscribeDAO;
	
	public SysOrgObjectServiceImpl() {
		super();
	}

	@Override
	public SysOrgObject saveNewSysOrgObject(SysOrgObject sysOrgObject)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			sysOrgObject.setUuid(id);
			sysOrgObjectDAO.saveSysOrgObject(sysOrgObject);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysOrgObject;
	}

	@Override
	public SysOrgObject updateSysOrgObject(SysOrgObject sysOrgObject)  throws Exception{
		try {
			sysOrgObjectDAO.updateSysOrgObject(sysOrgObject);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysOrgObject;
	}

	@Override
	public SysOrgObject findSysOrgObjectById(java.lang.String uuid) throws Exception{
		try {
			return sysOrgObjectDAO.getSysOrgObject(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeSysOrgObject(String uuid) throws Exception{
		try {
		sysOrgObjectDAO.removeSysOrgObject(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<SysOrgObject> list = sysOrgObjectDAO.queryList(pager);
			int totalCount = sysOrgObjectDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	@Override
	public Map<String, Object> query(String objectType, String objectId, String orgId, Integer ownLevel) throws Exception {
		Map<String,Object> ownOrgMap = new HashMap<String, Object>();
		//查询授权的权限级别
		ownOrgMap.put("ownLevel", ownLevel);
		/*if(ownLevel == 0){
			ownOrgMap.put("ownLevel", 1);
			SysOrgObject orgObject = sysOrgObjectDAO.query(new SysOrgObject(orgId,objectType,objectId));
			if(orgObject == null){
				ownOrgMap.put("ownLevel", 4);
			}
		}*/
		SysOrg sysOrg = sysOrgDAO.getSysOrg(orgId);
		if(sysOrg != null){
			ownOrgMap.put("ownOrgName",sysOrg.getOrgName());
		}else{
			ownOrgMap.put("ownOrgName","");
		}
		List<Map<String,Object>> childList = sysOrgObjectDAO.qryChildOrgObject(objectType, objectId, orgId);
		ownOrgMap.put("childList", childList);	
		return ownOrgMap;
	}

	@SuppressWarnings("unchecked")
	public void saveSysOrgObject(JSONObject json, String orgId,int ownLevel) throws Exception {
		if(ownLevel == 4){
			throw new Exception("您不能再次授权此数据");
		}
		List<Map<String,Object>> f = (List<Map<String, Object>>) json.get("childList");
		String objectType = json.getString("objectType");
		String objectId = json.getString("objectId");
		String uuid = json.getString("uuid"); 
		long time = System.currentTimeMillis();
		for(Map<String,Object> map : f){
			String childOrgId = map.get("orgId").toString();
			//String childOrgName = map.get("orgName").toString();
			int childLevel = (int)map.get("level");
			if(childLevel > ownLevel){
				throw new BussinessException("对下级授权不能超过本组织权限！");
			}else{
				SysOrgObject childOrgObject = sysOrgObjectDAO.query(new SysOrgObject(childOrgId,objectType,objectId));
				if(childOrgObject == null){
					if(childLevel == 0){
						continue;
					}else{
						SysOrgObject sysOrgObject = new SysOrgObject(childOrgId,objectType,objectId,childLevel,uuid,time,orgId);
						this.saveNewSysOrgObject(sysOrgObject);
						if("JB".equals(objectType)){
//							Jobs jobs = null;
//							try {
//								jobs = jobsDAO.getJobs(objectId);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							if(jobs == null){
//								log.error("找不到任务，任务ID是："+objectId);
//							}else{
////								String methodId = jobs.getMethodId();
//								SysOrgObject methodOrgObject = new SysOrgObject(childOrgId,"JM","",childLevel,uuid,time,orgId);
//								this.saveNewSysOrgObject(methodOrgObject);
//							}
						}
					}					
				}else{
					childOrgObject.setModifyUser(uuid);
					childOrgObject.setModifyTime(time);
					this.updateSysOrgObject(childOrgObject,childLevel);
				}
			}
		}
	}
	//1-->2
	//2-->1
	//2-->0 1--0
	public void updateSysOrgObject(SysOrgObject sysOrgObject,int newLevel)throws Exception {
		try{
			if(newLevel == 2){
				sysOrgObject.setLevel(newLevel);
				this.updateSysOrgObject(sysOrgObject);
			}else{
				//查出对该资源有权限的所有下级组织，包括本级
				//List<SysOrg> sysOrgList = sysUserService.qryUserOrg(sysOrgObject.getOrgId());
				List<String> orgIds = new ArrayList<String>();
//				for(SysOrg sysOrg :  sysOrgList){
//					orgIds.add(sysOrg.getUuid());
//				}
				if(orgIds.size()>0){
					List<SysOrgObject> sysOrgObjectList= sysOrgObjectDAO.queryListSysOrgObject(orgIds,sysOrgObject.getObjectType(),sysOrgObject.getObjectId());
					if (newLevel == 0) {					
						for (SysOrgObject childSysOrgObject : sysOrgObjectList) {
							/*if(childSysOrgObject.getObjectType().equals("CN") || childSysOrgObject.getObjectType().equals("DB")){
								List<Map<String,String>> jodIds = sysOrgObjectDAO.qryJobIdbyCNorDB(childSysOrgObject);
								for(Map<String,String> map : jodIds){
									//删除关系表
									if(childSysOrgObject.getObjectType().equals("CN")){
										jobChannelDAO.removeJobChannel(map.get("uuid"));
									}else{
										jobTemplateDbDAO.removeJobTemplateDb(map.get("uuid"));
									}
									//更新facejob的数据
									strategyService.updateStrategtData("更新任务","updateJobs");
									strategyService.notifySystem("jobs", 2, jobsDAO.getJobs(map.get("jobId")));
								}
							}*/
							if("JB".equals(childSysOrgObject.getObjectType())){
								sysSubscribeDAO.removeSysSubscribeByJobId(childSysOrgObject.getObjectId());
							}	
							sysOrgObjectDAO.deleteList(sysOrgObjectList);
						}

					}else if(newLevel == 1){
						for(SysOrgObject childSysOrgObject : sysOrgObjectList){
							if(childSysOrgObject.getLevel() == 2){
								childSysOrgObject.setModifyUser(sysOrgObject.getModifyUser());
								childSysOrgObject.setModifyTime(sysOrgObject.getModifyTime());
								childSysOrgObject.setLevel(newLevel);
								sysOrgObjectDAO.updateSysOrgObject(childSysOrgObject);
							}else{
								continue;
							}
							
						}
						
					}
				}
			}
		}catch(Exception e){
			throw new BussinessException(e);
		}

	}
}