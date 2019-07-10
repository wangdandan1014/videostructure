package com.sensing.core.alarm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.dao.IDataInitDAO;

/**
 * 
 * <p>Title: DataInitService</p>
 * <p>Description:缓存数据初始化</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月27日
 * @version 1.0
 */
@Service
public class DataInitService {

	@Resource
	public IDataInitDAO dataInitDAO;
	
	private Log log = LogFactory.getLog(DataInitService.class);
	
	
	/**
	 * 初始化入口
	 * 
	 * @author mingxingyu
	 * @date   2018年11月28日 上午10:44:41
	 */
	@PostConstruct
	public void startInit(){
		init();
		
		//开启定时任务删除过期的告警数据
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(new Runnable() {
			public void run() {
				updateAlarmInfoMap();
			}
		},0,AlarmCache.alarmCacheTime, TimeUnit.SECONDS);
	}
	
	/**
	 * 缓存更新
	 * 
	 * @author mingxingyu
	 * @date   2018年11月28日 上午10:44:26
	 */
	public void init(){
		updateDeviceJobMap();
		updateJobMap();
		updateJobTemplateDbMap();
//		updateTemplateDbList();
		updateChannelMap();
	}

	/**
	 * 通道关联的任务  缓存更新
	 * 
	 * @author mingxingyu
	 * @date   2018年11月28日 上午9:54:39
	 */
	public void updateDeviceJobMap(){
		try {
			long startTime = System.currentTimeMillis();
			List<Map<String,Object>> list = dataInitDAO.queryChannelJobMap();
			Map<String,List<String>> deviceJobMap = new ConcurrentHashMap<String,List<String>>();
			if ( list != null && list.size() > 0 ) {
				for (int i = 0; i < list.size() ; i++) {
					List<String> jobUuidList = new ArrayList<String>();
					
					Map<String, Object> paramsMap = list.get(i);
					String deviceUuid = paramsMap.get("deviceUuid").toString();
					String jobUuids = paramsMap.get("jobUuids").toString();
					String[] jobUuidArr = jobUuids.split(",");
					for (String jobUuid : jobUuidArr) {
						jobUuidList.add(jobUuid);
					}
					deviceJobMap.put(deviceUuid, jobUuidList);
				}
			}
			AlarmCache.deviceJobMap = deviceJobMap;
			long endTime = System.currentTimeMillis();
			log.info("channelJobMap缓存已更新,耗时:"+(endTime-startTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("channelJobMap缓存更新发生异常."+e.getMessage());
		}
	}
	
	/**
	 * 
	 * 更新任务和模板库的关联关系
	 * @author mingxingyu
	 * @date   2018年11月28日 上午9:54:39
	 */
	public void updateJobTemplateDbMap(){
		try {
			long startTime = System.currentTimeMillis();
			List<Map<String,Object>> list = dataInitDAO.queryJobTemplateDbMap();
			Map<String,List<Integer>> jobTemplateDbMap = new HashMap<String, List<Integer>>();
			if ( list != null && list.size() > 0 ) {
				for (int i = 0; i < list.size() ; i++) {
					List<Integer> templateDbList = new ArrayList<Integer>();
					
					Map<String, Object> paramsMap = list.get(i);
					String jobUuid = paramsMap.get("jobUuid").toString();
					String templateDbIds = paramsMap.get("templateDbIds").toString();
					String[] templateDbIdArr = templateDbIds.split(",");
					for (String templateDbId : templateDbIdArr) {
						Integer templateDbIdInt = templateDbId==null?null:Integer.parseInt(templateDbId);
						if ( templateDbIdInt != null ) {
							templateDbList.add(templateDbIdInt);
						}
					}
					jobTemplateDbMap.put(jobUuid,templateDbList);
				}
			}
			AlarmCache.jobTemplateDbMap = jobTemplateDbMap;
			long endTime = System.currentTimeMillis();
			log.info("jobTemplateDbMap缓存已更新,耗时:"+(endTime-startTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("jobTemplateDbMap缓存更新发生异常."+e.getMessage());
		}
	}
	
	/**
	 * 
	 * 更新布控任务的缓存
	 * @author mingxingyu
	 * @date   2018年11月28日 上午9:54:39
	 */
	public void updateJobMap(){
		try {
			long startTime = System.currentTimeMillis();
			List<JobBean> list = dataInitDAO.queryJobMap();
			Map<String,JobBean> jobMap = new ConcurrentHashMap<String,JobBean>();
			if ( list != null && list.size() > 0 ) {
				for (int i = 0; i < list.size() ; i++) {
					JobBean jobBean = list.get(i);
					jobMap.put(jobBean.getUuid(),jobBean);
				}
			}
			AlarmCache.jobMap = jobMap;
			long endTime = System.currentTimeMillis();
			log.info("jobMap缓存已更新,耗时:"+(endTime-startTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("jobMap缓存更新发生异常."+e.getMessage());
		}
	}
	
//	/**
//	 * 更新可用的模板库
//	 * 
//	 * @author mingxingyu
//	 * @date   2018年11月29日 下午5:32:53
//	 */
//	public void updateTemplateDbList(){
//		try {
//			long startTime = System.currentTimeMillis();
//			List<Map<String,Object>> list = dataInitDAO.queryTemplateDbList();
//			List<Integer> templateDbList = new ArrayList<Integer>();
//			if ( list != null && list.size() > 0 ) {
//				for (int i = 0; i < list.size() ; i++) {
//					Map<String, Object> param = list.get(i);
//					templateDbList.add(Integer.parseInt(param.get("id").toString()));
//				}
//			}
//			AlarmCache.templateDbList = templateDbList;
//			long endTime = System.currentTimeMillis();
//			log.info("templateDbList缓存已更新,耗时:"+(endTime-startTime)+"ms");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("jobMap缓存更新发生异常."+e.getMessage());
//		}
//	}
	
	/**
	 * 
	 * 
	 * @author mingxingyu
	 * @date   2018年11月29日 下午5:32:53
	 */
	public void updateChannelMap(){
		try {
			long startTime = System.currentTimeMillis();
			List<DeviceBean> deviceList = dataInitDAO.queryChannel();
			Map<String,DeviceBean> deviceMap = new ConcurrentHashMap<String, DeviceBean>();
			if ( deviceList != null && deviceList.size() > 0  ) {
				for (int i = 0; i < deviceList.size() ; i++) {
					DeviceBean bean = deviceList.get(i);
					deviceMap.put(bean.getUuid(),bean);
				}
			}
			AlarmCache.deviceMap = deviceMap;
			long endTime = System.currentTimeMillis();
			log.info("deviceMap缓存已更新,耗时:"+(endTime-startTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("deviceMap缓存更新发生异常."+e.getMessage());
		}
	}
	
	/**
	 * 定时任务更新告警缓存
	 * 
	 * @author mingxingyu
	 * @date   2018年11月29日 下午5:36:54
	 */
	public void updateAlarmInfoMap(){
		long currentTime = new Date().getTime()/1000;
		//删除alarmMap数据
		Set<Entry<String,AlarmInfo>> alarmMapSet = AlarmCache.alarmInfoMap.entrySet();
		int alarmDelCount = 0 ;
		for (Entry<String,AlarmInfo> entry : alarmMapSet) {
			String key = entry.getKey();
			AlarmInfo alarmInfo = AlarmCache.alarmInfoMap.get(key);
			Long alarmTime = alarmInfo.getAlarmTime();
			//告警生成时间超过30分钟的就删除
			if ( currentTime - alarmTime > AlarmCache.alarmCacheTime ) {
				AlarmCache.alarmInfoMap.remove(key);
				alarmInfo = null;
				alarmDelCount ++;
			}
		}
		String alarmMapInfo = "定时任务删除alarmInfoMap过期的数据，本次已删除"+alarmDelCount+"条，剩余"+AlarmCache.alarmInfoMap.size()+"条。";
		log.info(alarmMapInfo);
				
	}
	
}
