package com.sensing.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.Alarm;
import com.sensing.core.bean.SysTypecode;
import com.sensing.core.bean.Template;
import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.bean.alarm.req.AlarmReq;
import com.sensing.core.bean.alarm.resp.AlarmDetailResp;
import com.sensing.core.bean.alarm.resp.AlarmResp;
import com.sensing.core.bean.alarm.resp.CapImgResp;
import com.sensing.core.dao.IAlarmDAO;
import com.sensing.core.dao.ISysTypecodeDAO;
import com.sensing.core.service.IAlarmService;
import com.sensing.core.service.ICapAttrConvertService;
import com.sensing.core.service.IJobsService;
import com.sensing.core.service.ITemplateObjMotorService;
import com.sensing.core.service.ITemplateService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.time.DateStyle;
import com.sensing.core.utils.time.QueryDateUtils;
import com.sensing.core.utils.time.TransfTimeUtil;

/**
 * @author mingxingyu
 */
@Service
public class AlarmServiceImpl implements IAlarmService {

	private static final Log log = LogFactory.getLog(IAlarmService.class);

	@Resource
	public IAlarmDAO alarmDAO;
	@Resource
	public IJobsService jobsService;
	@Resource
	public ITemplateObjMotorService templateObjMotorService;
	@Resource
	public ITemplateService templateService;
	@Resource
	public ICapAttrConvertService capAttrConvertService;
	@Resource
	public ISysTypecodeDAO sysTypecodeDAO;

	public AlarmServiceImpl() {
		super();
	}

	/**
	 * 统计告警数量 单日告警的数量和未审核的告警数量
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月10日 下午6:28:25
	 */
	public Map<String, Object> queryAlarmStatistics() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(date) + " 00:00:00";
		String end = sdf.format(date) + " 23:59:59";
		Date startDate = sdf1.parse(start);
		Date endDate = sdf1.parse(end);

		params.put("startTime", startDate.getTime() / 1000);
		params.put("endTime", endDate.getTime() / 1000);
		params.put("state", Constants.ALARM_STATE_UNVERIFY);
		return alarmDAO.queryAlarmStatistics(params);
	}

	/**
	 * 首页的最新四条告警信息查询
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2018年12月7日 下午4:31:07
	 */
	public List<Map<String, Object>> queryAlarmHomePage(String userUuid, Integer pageRows) throws Exception {
		try {
			List<Map<String, Object>> alarmList = alarmDAO.queryAlarmHomePage(userUuid, Constants.ALARM_STATE_UNVERIFY,
					pageRows);
			if (alarmList != null && alarmList.size() > 0) {
				for (int i = 0; i < alarmList.size(); i++) {
					Map<String, Object> resultMap = alarmList.get(i);
					Object mainTemplateUrlObj = resultMap.get("mainTemplateUrl");
					if (mainTemplateUrlObj != null && !"".equals(mainTemplateUrlObj.toString())) {
						resultMap.put("mainTemplateUrl", Constants.PHOTO_URL_PERSIST + mainTemplateUrlObj.toString());
					}
					Object capImgUrlObj = resultMap.get("capUrl");
					if (capImgUrlObj != null && !"".equals(capImgUrlObj.toString())) {
						resultMap.put("capUrl", Constants.PHOTO_URL_PERSIST + capImgUrlObj.toString());
					}
					// 替换车牌号码的字符
					Object plateNoObj = resultMap.get("plateNo");
					if (plateNoObj != null && !"".equals(plateNoObj.toString())) {
						String plateNo = plateNoObj.toString().replace("_", "?").replaceAll("\\%", "*");
						resultMap.put("plateNo", plateNo);
					}
					// 车牌颜色的转换
					Object plateColorObj = resultMap.get("plateColor");
					if (plateColorObj != null && !"".equals(plateColorObj.toString())) {
						resultMap.put("plateColor", plateColorObj);
						resultMap.put("plateColorTag",
								Constants.SYS_TYPECODE_PLATENO_MAP.get(Integer.parseInt(plateColorObj.toString())));
					}

				}
				return alarmList;
			}
		} catch (Exception e) {
			log.error("查询首页的最新四条告警信息发生异常." + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Alarm saveNewAlarm(Alarm alarm) throws Exception {
		try {
			String id = UuidUtil.getUuid();
			alarm.setUuid(id);
			alarmDAO.saveAlarm(alarm);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return alarm;
	}

	@Override
	public Alarm updateAlarm(Alarm alarm) {
		Alarm a = alarmDAO.getAlarm(alarm.getUuid());
		if (a != null) {
			if (alarm.getStateMemo() == null) {
				alarm.setStateMemo(a.getStateMemo());
			}
			if (alarm.getState() == null) {
				alarm.setState(a.getState());
			}
			alarm.setStateTime(new Date());
			alarmDAO.updateAlarm(alarm);
		}
		alarm = alarmDAO.getAlarm(alarm.getUuid());
		return alarm;
	}

	@Override
	public Alarm findAlarmById(java.lang.String uuid) throws Exception {
		try {
			return alarmDAO.getAlarm(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeAlarm(String uuid) throws Exception {
		try {
			alarmDAO.removeAlarm(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public AlarmReq queryPage(AlarmReq pager) {
		long l1 = new Date().getTime();
		String date = pager.getF().get("date" + "");
		if (StringUtils.isNotEmpty(date)) {
			long startTime = 0l;
			long endTime = 0l;
			Date[] dates;
			if ("1".equals(date)) {
				dates = QueryDateUtils.getToday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-1".equals(date)) {
				dates = QueryDateUtils.getYesterday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-3".equals(date)) {
				dates = QueryDateUtils.get3Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-7".equals(date)) {
				dates = QueryDateUtils.get7Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-15".equals(date)) {
				dates = QueryDateUtils.get15Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-30".equals(date)) {
				dates = QueryDateUtils.get30Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			}
			pager.getF().put("startTime", TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			pager.getF().put("endTime", TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
		}
		List<AlarmResp> list = alarmDAO.queryList(pager);
		long l2 = new Date().getTime();
		log.info("报警列表查询耗时----------------------" + (l2 - l1) + "ms");
		for (AlarmResp alarmResp : list) {
			// 车牌号码的转换
			alarmResp = motorDBAttrConvert(alarmResp);
			String plateNo = alarmResp.getPlateNo().replace("_", "?").replaceAll("\\%", "*");
			alarmResp.setPlateNo(plateNo);
			// 车牌颜色的转换
			Integer plateColor = alarmResp.getPlateColor();
			if (plateColor != null) {
				alarmResp.setPlateColorTag(Constants.SYS_TYPECODE_PLATENO_MAP.get(plateColor));
			}
		}
		long l3 = new Date().getTime();
		log.info("报警车牌号转换查询耗时----------------------" + (l3 - l2) + "ms");
		int totalCount = alarmDAO.selectCount(pager);
		long l4 = new Date().getTime();
		log.info("报警总数查询耗时----------------------" + (l4 - l3) + "ms");
		pager.setTotalCount(totalCount);
		pager.setResultList(list);
		return pager;
	}

	private AlarmResp motorDBAttrConvert(AlarmResp alarmResp) {
		if (alarmResp != null) {
			Long alarmTime = alarmResp.getAlarmTime();
			if (alarmTime != null) {
				String aTime = TransfTimeUtil.UnixTimeStamp2Date(alarmTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
				alarmResp.setaTime(aTime);
			}
			if (alarmResp.getCapImgUrl() != null) {
				alarmResp.setCapImgUrl(Constants.PHOTO_URL_PERSIST + alarmResp.getCapImgUrl());
			}
			if (alarmResp.getImgUrl() != null) {
				alarmResp.setImgUrl(Constants.PHOTO_URL_PERSIST + alarmResp.getImgUrl());
			}
			if (alarmResp.getState() != null) {
				switch (alarmResp.getState()) {
				case 10:
					alarmResp.setStateTag("未核查");
					break;
				case 20:
					alarmResp.setStateTag("已确认");
					break;
				case 30:
					alarmResp.setStateTag("已排除");
					break;
				case 40:
					alarmResp.setStateTag("核查待定");
					break;
				default:
					break;
				}
			}
			if (alarmResp.getLevel() != null) {
				switch (alarmResp.getLevel()) {
				case 1:
					alarmResp.setLevelTag("一级");
					break;
				case 2:
					alarmResp.setLevelTag("二级");
					break;
				case 3:
					alarmResp.setLevelTag("三级");
					break;

				default:
					break;
				}
			}
		}
		return alarmResp;
	}

	@Override
	public AlarmDetailResp queryByUuid(String uuid) {
		AlarmDetailResp alarmDetailResp = alarmDAO.queryByUuid(uuid);
		if (alarmDetailResp != null) {
			String itemId = alarmDetailResp.getItemId();
			if (StringUtils.isNotEmpty(itemId)) {
				try {
					List<SysTypecode> list = sysTypecodeDAO.selectSysTypeCodeByTypeCodeAndItemId("TEMPLATEDB_TYPE",
							itemId);
					if (list != null && list.size() > 0) {
						SysTypecode typecode = list.get(0);
						alarmDetailResp.setItemValue(typecode.getItemValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("查询库类型失败，失败信息为：" + e.getMessage());
				}
			}

			Long alarmTime = alarmDetailResp.getAlarmTime();
			if (alarmTime != null) {
				String aTime = TransfTimeUtil.UnixTimeStamp2Date(alarmTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
				alarmDetailResp.setaTime(aTime);
			}
			String startDate1 = "";
			if (alarmDetailResp.getStartDate() != null) {
				String startDate = TransfTimeUtil.dateToStrLong(alarmDetailResp.getStartDate());
				try {
					startDate1 = TransfTimeUtil.TimeStamp2DateByString(startDate, DateStyle.YYYY_MM_DD_HH_MM_SS,
							DateStyle.YYYY_MM_DD);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String endDate1 = "";
			if (alarmDetailResp.getEndDate() != null) {
				String endDate = TransfTimeUtil.dateToStrLong(alarmDetailResp.getEndDate());
				try {
					endDate1 = TransfTimeUtil.TimeStamp2DateByString(endDate, DateStyle.YYYY_MM_DD_HH_MM_SS,
							DateStyle.YYYY_MM_DD);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (alarmDetailResp.getLevel() != null) {
				switch (alarmDetailResp.getLevel()) {
				case 1:
					alarmDetailResp.setLevelTag("一级");
					break;
				case 2:
					alarmDetailResp.setLevelTag("二级");
					break;
				case 3:
					alarmDetailResp.setLevelTag("三级");
					break;
				default:
					break;
				}
			}
			String jobsDate = "";
			if (StringUtils.isEmptyOrNull(endDate1)) {
				jobsDate = startDate1 + "至永久布控 " + alarmDetailResp.getStartTime() + "至" + alarmDetailResp.getEndTime()
//						+ " <br/>" + StringUtils.getWeekString(alarmDetailResp.getRunWeek(), "、");
						+ " " + StringUtils.getWeekString(alarmDetailResp.getRunWeek(), "、");
			} else {
				jobsDate = startDate1 + "至" + endDate1 + " " + alarmDetailResp.getStartTime() + "至"
//						+ alarmDetailResp.getEndTime() + " <br/>"
						+ alarmDetailResp.getEndTime() + " "
						+ StringUtils.getWeekString(alarmDetailResp.getRunWeek(), "、");
			}
			alarmDetailResp.setJobsDate(jobsDate);
			List<CapImgResp> list = alarmDAO.queryImgByUuid(uuid);
			if (list != null && list.size() > 0) {
				for (CapImgResp capImgResp : list) {
					if (StringUtils.isNotEmpty(capImgResp.getImgUrl())) {
						capImgResp.setImgUrl(Constants.PHOTO_URL_PERSIST + capImgResp.getImgUrl());
					}
					if (StringUtils.isNotEmpty(capImgResp.getSceneUrl())) {
						capImgResp.setSceneUrl(Constants.PHOTO_URL_PERSIST + capImgResp.getSceneUrl());
					}
				}
				alarmDetailResp.setCapImgList(list);
			}
			if ("单目标库".equals(alarmDetailResp.getTemplateDbName())) {
				String jobsUuid = alarmDetailResp.getJobsUuid();
				String objUuid = jobsService.getObjUuidByJobsUuid(jobsUuid);
				try {
					TemplateObjMotor motor = templateObjMotorService.findTemplateObjMotorById(objUuid);
					if (motor != null) {
						String plateNo = motor.getPlateNo();
						if (StringUtils.isNotEmpty(plateNo)) {
							String newPlateNo = plateNo.toString().replace("_", "?").replaceAll("\\%", "*");
							alarmDetailResp.setTemplateDbName("单目标\\" + newPlateNo);
						}
						if (motor.getPlateColor() != null) {
							alarmDetailResp.setPlateColorTag(
									Constants.SYS_TYPECODE_PLATENO_MAP.get(motor.getPlateColor().intValue()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				TemplateObjMotor motor = templateObjMotorService
						.findTemplateObjMotorById(alarmDetailResp.getCmpObjUuid());
				if (motor != null) {
					if (StringUtils.isNotEmpty(motor.getPlateNo())) {
						String newPlateNo = motor.getPlateNo().replace("_", "?").replaceAll("\\%", "*");
						alarmDetailResp.setPlateNo(newPlateNo);
					}
					String templateUuid = motor.getMainTemplateUuid();
					if (StringUtils.isNotEmpty(templateUuid)) {
						Template template = templateService.findTemplateById(templateUuid);
						if (template != null) {
							alarmDetailResp.setObjUrl(Constants.PHOTO_URL_PERSIST + template.getImageUrl());
						}
					}
					TemplateObjMotor newMotor = capAttrConvertService.templateObjMotorConvert(motor);
					if (newMotor != null && newMotor.getPlateColor() != null) {
						alarmDetailResp.setPlateColor(newMotor.getPlateColor().intValue());
						alarmDetailResp.setPlateColorTag(newMotor.getPlateColorTag());
					}
					alarmDetailResp.setVehicleColorTag(newMotor.getVehicleColorTag());
					alarmDetailResp.setVehicleBrandTag(newMotor.getVehicleBrandTag());
					alarmDetailResp.setVehicleClassTag(newMotor.getVehicleClassTag());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return alarmDetailResp;
		} else {
			return null;
		}
	}

	@Override
	public AlarmReq queryAlarmData(AlarmReq pager) {
		long l1 = new Date().getTime();
		List<AlarmResp> list = alarmDAO.queryAlarmData(pager);
		long l2 = new Date().getTime();
		log.info("报警列表查询耗时----------------------" + (l2 - l1) + "ms");
		for (AlarmResp alarmResp : list) {
			alarmResp = motorDBAttrConvert(alarmResp);
			String plateNo = alarmResp.getPlateNo().replace("_", "?").replaceAll("\\%", "*");
			alarmResp.setPlateNo(plateNo);
		}
		long l3 = new Date().getTime();
		log.info("报警车牌号转换查询耗时----------------------" + (l3 - l2) + "ms");
		int totalCount = alarmDAO.selectCount(pager);
		long l4 = new Date().getTime();
		log.info("报警总数查询耗时----------------------" + (l4 - l3) + "ms");
		pager.setTotalCount(totalCount);
		pager.setResultList(list);
		return pager;
	}

	@Override
	public List<String> queryCapUuids(AlarmReq alarmReq) {
		List<String> capUuids = alarmDAO.queryCapUuids(alarmReq);
		return capUuids;
	}

	@Override
	public String nextUuid(AlarmReq pager) {
		String date = pager.getF().get("date" + "");
		if (StringUtils.isNotEmpty(date)) {
			long startTime = 0l;
			long endTime = 0l;
			Date[] dates;
			if ("1".equals(date)) {
				dates = QueryDateUtils.getToday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-1".equals(date)) {
				dates = QueryDateUtils.getYesterday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-3".equals(date)) {
				dates = QueryDateUtils.get3Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-7".equals(date)) {
				dates = QueryDateUtils.get7Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-15".equals(date)) {
				dates = QueryDateUtils.get15Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-30".equals(date)) {
				dates = QueryDateUtils.get30Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			}
			pager.getF().put("startTime", TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			pager.getF().put("endTime", TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
		}
		String uuid = alarmDAO.nextUuid(pager);
		return uuid;
	}

	@Override
	public String prevUuid(AlarmReq pager) {
		String date = pager.getF().get("date" + "");
		if (StringUtils.isNotEmpty(date)) {
			long startTime = 0l;
			long endTime = 0l;
			Date[] dates;
			if ("1".equals(date)) {
				dates = QueryDateUtils.getToday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-1".equals(date)) {
				dates = QueryDateUtils.getYesterday();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-3".equals(date)) {
				dates = QueryDateUtils.get3Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-7".equals(date)) {
				dates = QueryDateUtils.get7Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-15".equals(date)) {
				dates = QueryDateUtils.get15Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			} else if ("-30".equals(date)) {
				dates = QueryDateUtils.get30Day();
				endTime = dates[0].getTime() / 1000;
				startTime = dates[1].getTime() / 1000;
			}
			pager.getF().put("startTime", TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			pager.getF().put("endTime", TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
		}
		String uuid = alarmDAO.prevUuid(pager);
		return uuid;
	}

	@Override
	public AlarmReq queryByAlarmId(AlarmReq pager) throws Exception {
		List<AlarmResp> list = alarmDAO.queryByAlarmId(pager);
		for (AlarmResp alarmResp : list) {
			// 车牌号码的转换
			alarmResp = motorDBAttrConvert(alarmResp);
			String plateNo = alarmResp.getPlateNo().replace("_", "?").replaceAll("\\%", "*");
			alarmResp.setPlateNo(plateNo);
			// 车牌颜色的转换
			Integer plateColor = alarmResp.getPlateColor();
			if (plateColor != null) {
				alarmResp.setPlateColorTag(Constants.SYS_TYPECODE_PLATENO_MAP.get(plateColor));
			}
		}
		int totalCount = alarmDAO.queryByAlarmIdCount(pager);
		pager.setTotalCount(totalCount);
		pager.setResultList(list);
		return pager;
	}

}