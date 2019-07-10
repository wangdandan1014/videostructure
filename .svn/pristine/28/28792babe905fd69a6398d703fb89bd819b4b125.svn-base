package com.sensing.core.alarm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.ImageFile;
import com.sensing.core.controller.AlarmDataPush;
import com.sensing.core.dao.IAlarmInfoDAO;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.NetUtils;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.time.DateStyle;
import com.sensing.core.utils.time.TransfTimeUtil;

/**
 * 
 * <p> Title: AlarmProcess </p>
 * <p> Description:告警流程 </p>
 * <p> Company: www.sensingtech.com </p>
 * 
 * @author mingxingyu
 * @date 2018年11月22日
 * @version 1.0
 */
public class AlarmProcess implements Runnable {

	private IAlarmInfoDAO alarmInfoDAO;

	public AlarmProcess() {
	}

	public AlarmProcess(IAlarmInfoDAO alarmInfoDAO) {
		this.alarmInfoDAO = alarmInfoDAO;
	}

	private Log log = LogFactory.getLog(AlarmProcess.class);

	/**
	 * 告警流程判断
	 */
	public void run() {
		try {
			while (true) {
				// 从比对结果的对列获取数据
				CmpResultBean cmpResultBean = AlarmTask.cmpResultQuene.poll(1, TimeUnit.SECONDS);

				if (cmpResultBean == null) {
					continue;
				}

				String threadName = Thread.currentThread().getName();
				if (threadName.contains("thread-1")) {
					long startTime = System.currentTimeMillis() / 1000;
					if (startTime % 60 == 0) {
						log.info("未处理的比对数据数量为:" + AlarmTask.cmpResultQuene.size());
					}
					if (AlarmTask.cmpResultQuene.size() > 500) {
						log.error("告警未处理的比对数据量已超过500,未处理的比对数据数量为:" + AlarmTask.cmpResultQuene.size());
					}
				}

				// 查找该通道绑定的任务
				List<String> jobsList = AlarmCache.deviceJobMap.get(cmpResultBean.getDeviceUuid());
				// 遍历任务
				if (jobsList != null && jobsList.size() > 0) {
					for (int i = 0; i < jobsList.size(); i++) {
						String jobsUuid = jobsList.get(i);
						// 判断任务和模板库的关联
						List<Integer> templateDbList = AlarmCache.jobTemplateDbMap.get(jobsUuid);
						if (templateDbList == null || templateDbList.size() < 1
								|| !templateDbList.contains(cmpResultBean.getTemplateDbId())) {
							continue;
						}
						JobBean job = AlarmCache.jobMap.get(jobsList.get(i));
						// 判断是否是单目标库的目标
						if (templateDbList.contains(AlarmCache.singleTemplateDbId)) {
							if (cmpResultBean.getTemplateDbId() != AlarmCache.singleTemplateDbId
									|| !job.getObjUuid().equals(cmpResultBean.getTemplateObjUuid())) {
								continue;
							}
						}
						// 判断任务是否在布控时间点
						if (!isJobWork(job, new Date(cmpResultBean.getCapTime() * 1000))) {
							continue;
						}

						// 满足告警
						// 判断告警对象是否已有
//						String alarmKey = cmpResultBean.getDeviceUuid() + cmpResultBean.getIdentityId() + jobsUuid + cmpResultBean.getTemplateObjUuid();
						String alarmKey = cmpResultBean.getDeviceUuid() + cmpResultBean.getCapPlateNo() + jobsUuid
								+ cmpResultBean.getTemplateObjUuid();
						AlarmInfo alarmInfo = AlarmCache.alarmInfoMap.get(alarmKey);

						// 已告警的数据生成时间超过 告警的缓存时间之后，就删除
						if (alarmInfo != null && System.currentTimeMillis() / 1000
								- alarmInfo.getAlarmTime() > AlarmCache.alarmCacheTime) {
							AlarmCache.alarmInfoMap.remove(alarmKey);
							alarmInfo = null;
						}

						if (alarmInfo == null) {
							// 新建告警的对象
							alarmInfo = new AlarmInfo();
							alarmInfo.setUuid(UuidUtil.getUuid());
							alarmInfo.setAlarmId(UuidUtil.getTime());
							
							//告警时间使用特殊的处理；大于抓拍3s,告警时间是抓拍时间+3s；
							long currTime = System.currentTimeMillis()/1000;
							alarmInfo.setAlarmTime(currTime-cmpResultBean.getCapTime()>3?cmpResultBean.getCapTime()+3:currTime);

							alarmInfo.setJobsUuid(jobsUuid);
							alarmInfo.setMatchedCount(1);
							alarmInfo.setAlarmFlag(true);
							alarmInfo.setJobName(job.getName());
							alarmInfo.setState(Constants.ALARM_STATE_UNVERIFY);
							alarmInfo.setJobLevel(job.getJobLevel());
							alarmInfo.setAlarmSound(job.getAlarmSound());
							AlarmCache.alarmInfoMap.put(alarmKey, alarmInfo);
							// 保存数据
							saveAlarm(alarmInfo, cmpResultBean, 1);
						} else {
							// 获取告警对象的uuid
							Integer state = alarmInfoDAO.queryByUuid(alarmInfo.getUuid());
							if (state != null) {
								alarmInfo.setState(state);
								// 更新缓存数据
								alarmInfo.setMatchedCount(alarmInfo.getMatchedCount() + 1);
								// alarmInfo.setJobsUuid(jobsUuid);
								alarmInfo.setJobName(job.getName());
								alarmInfo.setJobLevel(job.getJobLevel());
								alarmInfo.setAlarmSound(job.getAlarmSound());
								AlarmCache.alarmInfoMap.put(alarmKey, alarmInfo);
								// 保存数据
								saveAlarm(alarmInfo, cmpResultBean, 2);
							} else {
								log.error("告警缓存中存在数据,但是检索数据库未获取到数据.告警的uuid:" + alarmInfo.getUuid());
							}

						}
					}
				}

				CountInfo.dealCmpCount.getAndIncrement();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("告警流程发生异常:" + e.getMessage());
		}
	}

	/**
	 * 保存告警信息
	 * 
	 * @param alarmInfo
	 * @param cmpResultBean
	 * @author mingxingyu
	 * @date 2018年12月3日 上午9:52:08
	 */
	public void saveAlarm(AlarmInfo alarmInfo, CmpResultBean cmpResultBean, int type) {

		CountInfo.alarmCount.getAndIncrement();

		// 告警之后，将图片存储到8500服务上。用于用久保存
		// 抓拍图和场景图的判断和存储
		String capImgUrl = null;
		String sceneImgUrl = null;
		String findCapUrl = null;
		String findSceneUrl = null;
		try {
			
			findCapUrl = alarmInfoDAO.findCapUrl(cmpResultBean.getCapUuid());
			findSceneUrl = alarmInfoDAO.findSceneUrl(cmpResultBean.getCapUuid());
			
			if ( StringUtils.isEmptyOrNull(findCapUrl) ) {
				byte[] capImgBytes = NetUtils.getImgBase64FromUrl(Constants.PHOTO_URL_TEMP + cmpResultBean.getCapUrl());
				byte[] sceneImgBytes = NetUtils.getImgBase64FromUrl(Constants.PHOTO_URL_TEMP + cmpResultBean.getSceneUrl());
				// 将图片保存到图片服务器
				String capFileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
				String sceneFileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
				
				String capResult = HttpDeal.doPut(capFileName, capImgBytes);
				String sceneResult = HttpDeal.doPut(sceneFileName, sceneImgBytes);
	
				ImageFile capImageFile = JSONObject.toJavaObject(JSONObject.parseObject(capResult), ImageFile.class);
				ImageFile sceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(sceneResult), ImageFile.class);
	
				if (capImageFile != null && capImageFile.getError() >= 0) {
					capImgUrl = capImageFile.getMessage();
				} else {
					log.error("告警中临时图片文件转存到用久存储文件发生错误。保存抓拍图片返回的结果为空。");
				}
				if (sceneImageFile != null && sceneImageFile.getError() >= 0) {
					sceneImgUrl = sceneImageFile.getMessage();
				} else {
					log.error("告警中临时图片文件转存到用久存储文件发生错误。保存场景图片返回的结果为空。");
				}
	
				if (capImgBytes.length < 100 || sceneImgBytes.length < 100 || capImageFile.getError() < 0
						|| sceneImageFile.getError() < 0) {
					log.error("文件保存发生异常;告警的uuid是:"+alarmInfo.getUuid()
							+";Constants.PHOTO_URL_TEMP+cmpResultBean.getCapUrl():"+ (Constants.PHOTO_URL_TEMP + cmpResultBean.getCapUrl())
							+";Constants.PHOTO_URL_TEMP+cmpResultBean.getSceneUrl():"+(Constants.PHOTO_URL_TEMP + cmpResultBean.getSceneUrl()) 
							+";capImgBytes.length:"+capImgBytes.length
							+";sceneImgBytes.length:"+sceneImgBytes.length
							+";capImageFile.getError():" + capImageFile.getError() 
							+";sceneImageFile.getError():"+sceneImageFile.getError() 
							+";capImageFile.getMessage():" + capImageFile.getMessage()
							+";sceneImageFile.getMessage():" + sceneImageFile.getMessage()
							);
				}
			}else{
				capImgUrl = findCapUrl;
				sceneImgUrl = findSceneUrl;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("告警中临时图片文件转存到用久存储文件发生异常:" + e.getMessage());
		}

		// 保存抓拍信息
		if ( StringUtils.isEmptyOrNull(findCapUrl) ) {
			try {
				Map<String, String> capParams = new HashMap<String, String>();
				capParams.put("uuid", cmpResultBean.getCapUuid());
				capParams.put("deviceId", cmpResultBean.getDeviceUuid());
				capParams.put("identityId", cmpResultBean.getIdentityId());
				capParams.put("capType", Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE + "");
				capParams.put("capTime", cmpResultBean.getCapTime() + "");
				// capParams.put("capUrl", cmpResultBean.getCapUrl());
				capParams.put("capUrl", capImgUrl);
				capParams.put("plateNo", cmpResultBean.getCapPlateNo());
	
				DeviceBean deviceBean = AlarmCache.deviceMap.get(cmpResultBean.getDeviceUuid());
				capParams.put("deviceArea", deviceBean == null ? null : deviceBean.getDeviceArea());
				int i = alarmInfoDAO.saveCapture(capParams);
				if (deviceBean == null) {
					log.error("deviceId:" + cmpResultBean.getDeviceUuid() + ",通道缓存map中未获取到该通道.deviceArea为空！");
				}
				log.info("告警中抓拍入库成功.i=" + i);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("告警中抓拍入库发生异常:" + e.getMessage());
			}
		}

		// 未告警的情况下，先保存数据
		// 再告警的情况就不在更新数据
		if (type == 1) {
			// 保存告警的数据
			try {
				Map<String, Object> alarmParams = new HashMap<String, Object>();
				alarmParams.put("uuid", alarmInfo.getUuid());
				alarmParams.put("deviceId", cmpResultBean.getDeviceUuid());
				alarmParams.put("alarmTime", alarmInfo.getAlarmTime());
				alarmParams.put("capTime", cmpResultBean.getCapTime());
				alarmParams.put("cmpUuid", null);
				alarmParams.put("cmpTemplatedbId", cmpResultBean.getTemplateDbId());
				alarmParams.put("cmpObjUuid", cmpResultBean.getTemplateObjUuid());
				alarmParams.put("cmpScore", null);
				alarmParams.put("capUuid", cmpResultBean.getCapUuid());
				alarmParams.put("jobUuid", alarmInfo.getJobsUuid());
				alarmParams.put("jobLevel", alarmInfo.getJobLevel());
				alarmParams.put("state", alarmInfo.getState());
				alarmParams.put("matchedCount", 1);
				alarmParams.put("alarmId", alarmInfo.getAlarmId());
				int i = alarmInfoDAO.saveAlarm(alarmParams);
				log.info("告警中告警数据入库成功.i=" + i);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("告警中告警入库发生异常:" + e.getMessage());
			}
		}
		// 更新告警的信息
		if (type == 2) {
			try {
				Map<String, Object> alarmParams = new HashMap<String, Object>();
				alarmParams.put("uuid", alarmInfo.getUuid());
				alarmParams.put("alarmTime", alarmInfo.getAlarmTime());
				alarmParams.put("capTime", cmpResultBean.getCapTime());
				alarmParams.put("jobLevel", alarmInfo.getJobLevel());
				alarmParams.put("capUuid", cmpResultBean.getCapUuid());
				int i = alarmInfoDAO.updateAlarm(alarmParams);
				log.info("告警中告警数据更新成功.i=" + i);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("告警中告警更新发生异常:" + e.getMessage());
			}
		}

		// 告警关联表添加
		try {
			Map<String, String> alarmCmpLogsParams = new HashMap<String, String>();
			alarmCmpLogsParams.put("uuid", UuidUtil.getUuid());
			alarmCmpLogsParams.put("alarmUuid", alarmInfo.getUuid());
			alarmCmpLogsParams.put("cmpUuid", null);
			alarmCmpLogsParams.put("capUuid", cmpResultBean.getCapUuid());
			alarmCmpLogsParams.put("sceneUrl", sceneImgUrl);
			int i = alarmInfoDAO.saveAlarmCmpLogs(alarmCmpLogsParams);
			log.info("告警中告警关联信息数据入库成功.i=" + i);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("告警中告警关联信息入库发生异常:" + e.getMessage());
		}

		// 保存抓拍信息到数据库的表
		try {

			int count = alarmInfoDAO.getMotorCountByUuid(cmpResultBean.getCapUuid());
			if (count < 1) {
				Map<String, Object> mvp = new HashMap<String, Object>();
				mvp.put("uuid", cmpResultBean.getCapUuid());
				mvp.put("type", 0);// 抓拍机动车的类型
				mvp.put("deviceId", cmpResultBean.getDeviceUuid());
				mvp.put("capTime", cmpResultBean.getCapTime());
				mvp.put("plateNo", cmpResultBean.getCapPlateNo());
				mvp.put("vehicleColor", cmpResultBean.getVehicleColor());
				mvp.put("orientation", cmpResultBean.getOrientation());
				mvp.put("plateColor", cmpResultBean.getPlateColor());
				mvp.put("plateClass", cmpResultBean.getPlateClass());
				mvp.put("vehicleClass", cmpResultBean.getVehicleClass());
				mvp.put("vehicleBrandTag", StringUtils.isEmptyOrNull(cmpResultBean.getVehicleBrandTag()) ? null
						: cmpResultBean.getVehicleBrandTag());
				mvp.put("vehicleModelTag", StringUtils.isEmptyOrNull(cmpResultBean.getVehicleModelTag()) ? null
						: cmpResultBean.getVehicleModelTag());
				mvp.put("vehicleStylesTag", StringUtils.isEmptyOrNull(cmpResultBean.getVehicleStylesTag()) ? null
						: cmpResultBean.getVehicleStylesTag());
				mvp.put("vehicleMarkerMot", cmpResultBean.getVehicleMarkerMot());
				mvp.put("vehicleMarkerTissuebox", cmpResultBean.getVehicleMarkerTissuebox());
				mvp.put("vehicleMarkerPendant", cmpResultBean.getVehicleMarkerPendant());
				mvp.put("sunvisor", cmpResultBean.getSunvisor());
				mvp.put("safetyBelt", cmpResultBean.getSafetyBelt());
				mvp.put("safetyBeltCopilot", cmpResultBean.getSafetyBeltCopilot());
				mvp.put("calling", cmpResultBean.getCalling());
				mvp.put("fea", cmpResultBean.getFea());
				mvp.put("capUrl", capImgUrl);
				mvp.put("capLocation", cmpResultBean.getCapLocation());
				mvp.put("sceneUrl", sceneImgUrl);
				mvp.put("isDeleted", Constants.DELETE_NO);
				mvp.put("createTime", new Date());
				mvp.put("videoUrl", null);
				mvp.put("createUser", null);
				mvp.put("modifyUser", null);
				mvp.put("modifyTime", null);
				int i = alarmInfoDAO.saveMotorVehicle(mvp);
				log.info("告警中告警关联的抓拍信息数据入库成功.i=" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("告警中告警关联的抓拍信息入库发生异常:" + e.getMessage());
		}
		// 往客户端推送报警的数据

		cmpResultBean.setCapUrl(capImgUrl);
		cmpResultBean.setSceneUrl(sceneImgUrl);
		pushAlarm(alarmInfo, cmpResultBean);
		long alarmCap = System.currentTimeMillis() / 1000 - cmpResultBean.getCapTime();
		log.info("告警数据执行完成之后，统计当前时间与抓拍延迟:" + alarmCap + "s.当前时间与接收到的抓拍时间相差:"
				+ (System.currentTimeMillis() - cmpResultBean.getReceiveTime()) + "ms;");

	}

	/**
	 * 判断任务是否在布控时间点
	 * 
	 * @param jobBean
	 * @param capDate
	 * @return
	 * @author mingxingyu
	 * @date 2018年11月28日 下午2:19:40
	 */
	public boolean isJobWork(JobBean jobBean, Date capDate) {

		if (jobBean == null || jobBean.getRunWeek() == null) {
			return false;
		}
		// 星期数是否在布控范围内
		if (!AlarmUtils.isRunWeek(jobBean.getRunWeek(), capDate)) {
			return false;
		}
		// 日期是否在布控时间范围内
		if (!AlarmUtils.isDateRange(jobBean.getBeginDate(), jobBean.getEndDate(), capDate)) {
			return false;
		}
		// 时间段是否在布控的
		if (!AlarmUtils.isTimeRange(jobBean.getBeginTime(), jobBean.getEndTime(), capDate)) {
			return false;
		}
		return true;
	}

	/**
	 * 报警推送
	 * 
	 * @param alarmInfo     报警对象
	 * @param cmpResultBean 比对结果对象
	 * @return
	 * @author mingxingyu
	 * @date 2018年12月4日 下午6:19:04
	 */
	public void pushAlarm(AlarmInfo alarmInfo, CmpResultBean cmpResultBean) {
		try {
			CopyOnWriteArraySet<AlarmDataPush> sockets = AlarmDataPush.alarmWbSockets;
			if (sockets != null && sockets.size() > 0) {

				Map<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("alarmUuid", alarmInfo.getUuid());
				msgMap.put("alarmTime",
						TransfTimeUtil.UnixTimeStamp2Date(alarmInfo.getAlarmTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				msgMap.put("capUrl", cmpResultBean.getCapUrl() == null ? ""
						: (Constants.PHOTO_URL_PERSIST + cmpResultBean.getCapUrl()));
				msgMap.put("mainTemplateUrl", cmpResultBean.getMainTemplateUrl() == null ? ""
						: (Constants.PHOTO_URL_PERSIST + cmpResultBean.getMainTemplateUrl()));
				msgMap.put("state", alarmInfo.getState() + "");
				msgMap.put("matchedCount", alarmInfo.getMatchedCount() + "");
				msgMap.put("jobName", alarmInfo.getJobName());
				msgMap.put("jobUuid", alarmInfo.getUuid());
				msgMap.put("jobLevel", alarmInfo.getJobLevel() + "");
				String plateNo = "";
				if (StringUtils.isNotEmpty(cmpResultBean.getObjPlateNo())) {
					plateNo = cmpResultBean.getObjPlateNo().replace("_", "?").replaceAll("\\%", "*");
				}
				msgMap.put("plateNo", plateNo);
				msgMap.put("plateColor",cmpResultBean.getPlateColor()==null?"":(cmpResultBean.getPlateColor()+""));
				msgMap.put("plateColorTag",cmpResultBean.getPlateColor()==null?"":Constants.SYS_TYPECODE_PLATENO_MAP.get(cmpResultBean.getPlateColor()));
				msgMap.put("alarmSound", alarmInfo.getAlarmSound() == null ? "0" : (alarmInfo.getAlarmSound() + ""));

				for (AlarmDataPush dataPush : sockets) {
					// 判断客户端传递来的通道id是否 和接收到的通道id相等
					if (alarmInfo.getJobsUuid() != null && dataPush.alarmJobStr.contains(alarmInfo.getJobsUuid())) {
						dataPush.sendMessage(JSONObject.toJSONString(msgMap));
						log.info("报警消息已推送,告警的uuid:" + alarmInfo.getUuid() + ";用户的uuid:" + dataPush.userUuid
								+ ";任务的uuid:" + alarmInfo.getJobsUuid() + ";");
					}
				}
			}
		} catch (Exception e) {
			log.error("报警消息推送发生异常:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
