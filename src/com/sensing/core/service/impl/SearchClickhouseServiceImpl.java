
package com.sensing.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sensing.core.alarm.AlarmCache;
import com.sensing.core.alarm.DeviceBean;
import com.sensing.core.bean.Channel;
import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.bean.Person;
import com.sensing.core.bean.Task;
import com.sensing.core.clickhouseDao.IMotorVehicleCKDAO;
import com.sensing.core.clickhouseDao.INonmotorVehicleCKDAO;
import com.sensing.core.clickhouseDao.IPersonCKDAO;
import com.sensing.core.dao.IChannelDAO;
import com.sensing.core.dao.IMotorVehicleDAO;
import com.sensing.core.dao.ITaskChannelDAO;
import com.sensing.core.service.ICapAttrConvertService;
import com.sensing.core.service.ICapService;
import com.sensing.core.service.ISearchClickhouseService;
import com.sensing.core.service.ITaskService;
import com.sensing.core.utils.BeanUtil;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.props.VehicleColorMutexCons;
import com.sensing.core.utils.time.DateStyle;
import com.sensing.core.utils.time.DateUtil;
import com.sensing.core.utils.time.QueryDateUtils;
import com.sensing.core.utils.time.TransfTimeUtil;

@Service
public class SearchClickhouseServiceImpl<Obejct> implements ISearchClickhouseService {

	private static final Log log = LogFactory.getLog(ISearchClickhouseService.class);
	@Resource
	public ICapAttrConvertService capAttrConvertService;
	@Resource
	public IChannelDAO channelDAO;
	@Resource
	public ITaskChannelDAO taskChannelDAO;
	@Resource
	public ICapService capService;
	@Resource
	public ITaskService taskService;
	@Resource
	public IMotorVehicleDAO motorVehicleDAO;
	@Resource
	public IPersonCKDAO personCKDAO;
	@Resource
	public IMotorVehicleCKDAO motorVehicleCKDAO;
	@Resource
	public INonmotorVehicleCKDAO nonmotorVehicleCKDAO;

	/**
	 * 关键词检索的接口
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月6日 上午10:35:08
	 */
	public Pager queryByKeyword(Pager pager) throws Exception {
		long l3 = System.currentTimeMillis();
		String words = pager.getF().get("keyword");

		InputStream dicIS = this.getClass().getResourceAsStream("/dll/attrs.dic");

		BufferedReader br = new BufferedReader(new InputStreamReader(dicIS, "UTF-8"));

		// 检索输入词中的车牌号码
		String carnumRegex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})";
		String plateNoResult = null;
		Pattern pattern = Pattern.compile(carnumRegex);
		Matcher matcher = pattern.matcher(words);
		while (matcher.find()) {
			plateNoResult = matcher.group(0);
		}
		if (StringUtils.isNotEmpty(plateNoResult)) {
			pager.getF().put("plateNo", plateNoResult);
		}

		// 检索其他的属性
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineStrs = line.split(";");
			if (words.contains(lineStrs[1]) && line != null && !"".equals(line)) {
				String[] valsArr = lineStrs[2].split(":");
				pager.getF().put(valsArr[0], valsArr[1]);
//				int lineNum = Integer.parseInt(lineStrs[0]);
			}
		}
		br.close();

		long l4 = System.currentTimeMillis();
		log.info("字典翻译耗时毫秒数为:" + (l4 - l3));
		long l1 = System.currentTimeMillis();
		pager = queryPage(pager);
		long l2 = System.currentTimeMillis();
		log.info("queryPage耗时毫秒数为:" + (l2 - l1));
		return pager;
	}

	/**
	 * 根据uuid获取比对特征文件
	 * 
	 * @param jo 参数
	 * @return
	 * @author mingxingyu
	 * @throws Exception
	 * @date 2018年9月20日 下午2:47:56
	 */
	public List<Object> queryResultByCmpByUuid(JSONObject jo) throws Exception {
		String capUuid = jo.getString("uuid");
		Integer capType = jo.getInteger("capType");

		// 检索参数
		JSONObject cmpJson = new JSONObject();
		cmpJson.put("capType", capType + "");
		if (StringUtils.isNotEmpty(jo.getString("deviceId"))) {
			cmpJson.put("deviceId", jo.getString("deviceId"));
		} else {
			cmpJson.put("deviceId", "");
		}
		if (StringUtils.isNotEmpty(jo.getString("startTime"))) {
			cmpJson.put("startTime", jo.getString("startTime"));
		}
		if (StringUtils.isNotEmpty(jo.getString("endTime"))) {
			cmpJson.put("endTime", jo.getString("endTime"));
		}
		if (jo.getDouble("score") != null) {
			cmpJson.put("score", jo.getDouble("score"));
		} else {
			cmpJson.put("score", Constants.PHOTO_SEARCH_SCORE);
		}

		// 从比对服务获取特征文件，比对服务不存在，则对应从db中检索
		// 布控任务关联的通道，没有开启结构化任务，则使用报警的抓拍图进行以图搜图的时候，会报错，检索不到对象
		String capFeature = null;
		try {
			// 查询特征文件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uuid", capUuid);
			params.put("type", capType);
			String cmpResult = HttpDeal.sendPost(Constants.CMP_FEATURE_URL, JSONObject.toJSONString(params));
			JSONObject resultJson = JSONObject.parseObject(cmpResult, JSONObject.class);
			Integer error = resultJson.getInteger("error");
			if (error == null || error != 0) {
				log.error("调用比对服务以图搜图失败，错误信息:" + resultJson.getString("message"));
				// throw new BussinessException("抓拍以图搜图调用失败!");
			} else {
				// 获取特征文件
				JSONObject map = (JSONObject) resultJson.get("map");
				capFeature = map.getString("fea");
			}
		} catch (Exception e) {
			log.error("从比对服务获取特征信息出错." + e.getMessage());
			e.printStackTrace();
		}
		// 比对服务不存在特征，从db中检索
		if (capFeature == null) {
			MotorVehicle motorVehicle = motorVehicleDAO.getMotorVehicle(capUuid);
			if (motorVehicle != null && motorVehicle.getFea() != null && motorVehicle.getFea().length > 0) {
				capFeature = Base64.encodeBase64String(motorVehicle.getFea());
			} else {
				throw new BussinessException("抓拍以图搜图获取特征文件失败");
			}
		}

		// 组装调用以图搜图的接口的对象
		cmpJson.put("capFeature", capFeature);
		cmpJson.put("vehicleColor", jo.getInteger("vehicleColor"));

		// 删除不必要查询的key值
		removeObjKey(cmpJson);

		List<Object> list = queryResultByCmp(cmpJson);

		return list;
	}

	/**
	 * 以图搜图调用比对查询
	 * 
	 * @param jo
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年9月17日 下午4:44:02
	 */
	public List<Object> queryResultByCmp(JSONObject jo) throws Exception {
		// 日期转换的格式
		SimpleDateFormat sdfLess = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 传入的参数
		// String capType = jo.getString("capType");
		Integer capTypeInt = jo.getInteger("capType");
		String deviceId = jo.getString("deviceId");
		String startTime = jo.getString("startTime");
		String endTime = jo.getString("endTime");
		String capFeature = jo.getString("capFeature");
		Double score = jo.getDouble("score");
		Long startTimeUnix = 0L;
		Long endTimeUnix = 0L;

		if (!StringUtils.isNotEmpty(deviceId)) {
			deviceId = "";
		}

		// 删除不必要查询的key值
		removeObjKey(jo);

		// 结果集
		List<Object> resultList = new ArrayList<Object>();
		// 非机动车检索mongodb
//		if (capTypeInt == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
//			jo.put("capType", capType);
//			Map map = JSON.parseObject(jo.toJSONString());
//			map.remove("score");
//			map.remove("capFeature");
//			Pager pager = new Pager();
//			pager.setPageNo(1);
//			pager.setPageRows(200);
//			pager.setF(map);
//			pager = queryPage(pager);
//			resultList.addAll(pager.getResultList());
//		}

		// 行人、机动车调用比对
//		else if (capTypeInt == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE
//				|| capTypeInt == Constants.CAP_ANALY_TYPE_PERSON) {
		// 查询时间转换
		Date date = new Date();
		if (startTime == null || "".equals(startTime)) {
			String string = sdfLess.format(date) + " 00:00:00";
			startTimeUnix = sdfMore.parse(string).getTime() / 1000;
		} else {
			startTimeUnix = sdfMore.parse(startTime).getTime() / 1000;
		}
		if (endTime == null || "".equals(endTime)) {
			endTimeUnix = date.getTime() / 1000;
		} else {
			endTimeUnix = sdfMore.parse(endTime).getTime() / 1000;
		}
		// 组装请求的参数
		JSONObject params = new JSONObject();
		params.put("fea1", capFeature);
		params.put("fea2", "");
		params.put("fea3", "");
		params.put("fea4", "");
		params.put("fea5", "");
		params.put("channel_array", deviceId);
		params.put("threshold", score * 0.01);
		params.put("cap_time_start", startTimeUnix);
		params.put("cap_time_end", endTimeUnix);
		params.put("type", capTypeInt);
		params.put("retNums", 200);
		// 比对
		long cmpStartTime = System.currentTimeMillis();
		String cmpResult = HttpDeal.sendPost(Constants.CMP_SERVER_URL, JSONObject.toJSONString(params));
		long cmpEndTime = System.currentTimeMillis();
//			log.info("以图搜图调用比对返回的结果为:" + cmpResult);
		log.info("以图搜图调用比对耗时为:" + (cmpEndTime - cmpStartTime) + "ms");
		Map<String, Object> map = null;
		if (StringUtils.isNotEmpty(cmpResult)) {
			map = JSONObject.parseObject(cmpResult, Map.class);
		} else {
			log.error("以图搜图调用比对返回的结果为空.");
		}
		int error = (Integer) map.get("error");
		if (error != 0) {
			log.error("以图搜图调用比对发生异常." + (String) map.get("message"));
		}
		if (capTypeInt == Constants.CAP_ANALY_TYPE_PERSON) {
			Map<String, Object> mapObj = (Map<String, Object>) map.get("map");
			List<JSONObject> list = (List<JSONObject>) mapObj.get("result1");
			for (JSONObject resultJSON : list) {

				Person cp = new Person();
				cp.setUuid(resultJSON.getString("uuid"));
				cp.setScore(resultJSON.getFloat("score"));
				cp.setCapTime(resultJSON.getLong("capTime"));
				cp.setDeviceId(resultJSON.getString("deviceId"));
				cp.setCapUrl(Constants.PHOTO_URL_TEMP + resultJSON.getString("capUrl"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					cp.setDeviceName(channelAll.getChannelName());
				}
				cp.setCapType(Constants.CAP_ANALY_TYPE_PERSON);
				resultList.add(cp);
			}
		}
		if (capTypeInt == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			Map<String, Object> mapObj = (Map<String, Object>) map.get("map");
			List<JSONObject> list = (List<JSONObject>) mapObj.get("result1");
			for (JSONObject resultJSON : list) {

				// 从比对服务中检索出的车辆颜色和以图搜图中的车辆颜色比对
				// 进行互斥校验，颜色互斥的情况下，去掉
				Integer vehicleColorPage = jo.getInteger("vehicleColor");// 以图搜图中的车辆颜色
				if (vehicleColorPage != null && vehicleColorPage != 0) {
					Integer vehicleColorCmp = resultJSON.getInteger("vehicleColor");// 比对返回的车辆颜色
					String vehicleColorMutex = vehicleColorPage + "-" + vehicleColorCmp;
					if (vehicleColorCmp == null || vehicleColorCmp == 0
							|| VehicleColorMutexCons.VehicleColorMutexList.contains(vehicleColorMutex)) {
						continue;
					}
				}

				MotorVehicle mv = new MotorVehicle();
				mv.setUuid(resultJSON.getString("uuid"));
				mv.setScore(resultJSON.getFloat("score"));
				mv.setCapTime(resultJSON.getLong("capTime"));
				mv.setDeviceId(resultJSON.getString("deviceId"));
				mv.setCapUrl(Constants.PHOTO_URL_TEMP + resultJSON.getString("capUrl"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					mv.setDeviceName(channelAll.getChannelName());
				}
				mv.setCapType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE);
				resultList.add(mv);
			}
		}
		if (capTypeInt == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
			Map<String, Object> mapObj = (Map<String, Object>) map.get("map");
			List<JSONObject> list = (List<JSONObject>) mapObj.get("result1");
			for (JSONObject resultJSON : list) {
				NonmotorVehicle nv = new NonmotorVehicle();
				nv.setUuid(resultJSON.getString("uuid"));
				nv.setScore(resultJSON.getFloat("score"));
				nv.setCapTime(resultJSON.getLong("capTime"));
				nv.setDeviceId(resultJSON.getString("deviceId"));
				nv.setCapUrl(Constants.PHOTO_URL_TEMP + resultJSON.getString("capUrl"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					nv.setDeviceName(channelAll.getChannelName());
				}
				nv.setCapType(Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE);
				resultList.add(nv);
			}
		}
		// 结果排序
		resultList = sortCmpResult(resultList);
		return resultList;
	}

	/**
	 * 获取mongodb的单个对象
	 * 
	 * @param uuid    抓拍的uuid
	 * @param capType 抓拍的类型(字符串格式)
	 * @return
	 * @author mingxingyu
	 * @throws Exception
	 * @date 2018年9月20日 下午2:47:56
	 */
	public Object getMGObjectByUuid(String uuid, Long capTime, String capType) throws Exception {
		if (Constants.PERSON.equals(capType)) {
			List<Person> personList = personCKDAO.queryByUuid(uuid, capTime);
			if (personList == null || personList.size() == 0) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				return null;
			}
			Person capPeopleConvert = capAttrConvertService.personConvert(personList.get(0));
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;
		} else if (Constants.MOTOR_VEHICLE.equals(capType)) {
			// clickhouse的图片在8400temp上，mysql的图片在8500的persist上
			List<MotorVehicle> motorVehicleList = motorVehicleCKDAO.queryByUuid(uuid, capTime);
			boolean tempFlag = true;
			if (motorVehicleList == null || motorVehicleList.size() == 0F) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				// 抓拍库检索不到数据从db中查询,这是告警的关联抓拍数据，没有结构化的数据，从数据库中获取
				MotorVehicle motorVehicle = motorVehicleDAO.getMotorVehicle(uuid);
				if (motorVehicle != null) {
					tempFlag = false;
					motorVehicle.setCapFlag(Constants.TASK_TYPE_REALTIME);
					motorVehicleList.add(motorVehicle);
				} else {
					return null;
				}
			}
			MotorVehicle capPeopleConvert = capAttrConvertService.motorVehicleConvert(motorVehicleList.get(0),
					tempFlag ? 0 : 1);
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;

		} else if (Constants.NONMOTOR_VEHICLE.equals(capType)) {
			List<NonmotorVehicle> nonmotorVehicleList = nonmotorVehicleCKDAO.queryByUuid(uuid, capTime);
			if (nonmotorVehicleList == null || nonmotorVehicleList.size() == 0) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				return null;
			}
			NonmotorVehicle capPeopleConvert = capAttrConvertService.nonmotorVehicleConvert(nonmotorVehicleList.get(0));
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;
		}
		return null;
	}

	public Object getMGTempObjectByUuid(String uuid, String capType) throws Exception {
		if (Constants.PERSON_TEMP.equals(capType)) {

			List<Person> personList = personCKDAO.queryByUuid(uuid, null);
			if (personList == null || personList.size() == 0) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				return null;
			}
			Person capPeopleConvert = capAttrConvertService.personConvert(personList.get(0));
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;
		} else if (Constants.MOTOR_VEHICLE_TEMP.equals(capType)) {
			List<MotorVehicle> motorVehicleList = motorVehicleCKDAO.queryByUuid(uuid, null);
			if (motorVehicleList == null || motorVehicleList.size() == 0) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				return null;
			}
			MotorVehicle capPeopleConvert = capAttrConvertService.motorVehicleConvert(motorVehicleList.get(0), null);
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;
		} else if (Constants.NONMOTOR_VEHICLE_TEMP.equals(capType)) {
			List<NonmotorVehicle> nonmotorVehicleList = nonmotorVehicleCKDAO.queryByUuid(uuid, null);
			if (nonmotorVehicleList == null || nonmotorVehicleList.size() == 0) {
				log.error("检索click抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
				return null;
			}
			NonmotorVehicle capPeopleConvert = capAttrConvertService.nonmotorVehicleConvert(nonmotorVehicleList.get(0));
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			return capPeopleConvert;
		}
		return null;
	}

	/**
	 * 获取db中的的单个抓拍对象
	 * 
	 * @param uuid    抓拍的uuid
	 * @param capType 类型的字符串格式
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2019年2月22日 上午11:29:06
	 */
	public Object getDBObjectByUuid(String uuid, String capType) throws Exception {
		// 机动车的属性转换
		if (Constants.MOTOR_VEHICLE.equals(capType)) {
			MotorVehicle motorVehicle = motorVehicleDAO.getMotorVehicle(uuid);
			MotorVehicle capMotorConvert = capAttrConvertService.motorVehicleConvert(motorVehicle, null);
			if (capMotorConvert != null) {
				// 从数据库取出的数据，文件服务器的地址需要转换
				if (StringUtils.isNotEmpty(capMotorConvert.getCapUrl())) {
					capMotorConvert.setCapUrl(capMotorConvert.getCapUrl().replaceAll(Constants.PHOTO_URL_TEMP,
							Constants.PHOTO_URL_PERSIST));
				}
				if (StringUtils.isNotEmpty(capMotorConvert.getSeceneUrl())) {
					capMotorConvert.setSeceneUrl(capMotorConvert.getSeceneUrl().replaceAll(Constants.PHOTO_URL_TEMP,
							Constants.PHOTO_URL_PERSIST));
				}
				String deviceId = capMotorConvert.getDeviceId();
				Channel channelAll = channelDAO.getChannelAll(deviceId);
				if (channelAll != null) {
					capMotorConvert.setDeviceName(channelAll.getChannelName());
				}
				return capMotorConvert;
			}
		}
		return null;
	}

	/**
	 * 调用比对获取结果
	 * 
	 * @param postAddr 请求地址
	 * @param params   请求参数
	 * @return
	 * @author mingxingyu
	 * @date 2018年9月13日 下午8:27:18
	 */
	public List<Map<String, Object>> cmp(String postAddr, JSONObject params) {
		String res = HttpDeal.sendPost(postAddr, JSONObject.toJSONString(params));
		log.info("behavior  server return :" + res);
		Map<String, Object> map = null;
		if (StringUtils.isNotEmpty(res)) {
			map = JSONObject.parseObject(res, Map.class);
		} else {
			log.error("行为分析服务异常!");
		}
		int error = (Integer) map.get("error");
		if (error != 0) {
			log.error((String) map.get("message"));
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 1; i <= 5; i++) {
			resultList.addAll(((Map<String, List<Map<String, Object>>>) map.get("map")).get("result" + i));
		}
		return resultList;
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception {
		long l1 = System.currentTimeMillis();
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
			pager.getM().put("startTime", startTime);
			pager.getM().put("endTime", endTime);
		}
		if (StringUtils.isNotEmpty(pager.getF().get("startTime" + ""))) {
			Long startTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("startTime" + ""),
					DateStyle.YYYY_MM_DD_HH_MM_SS);
			pager.getM().put("startTime", startTime);
		}
		if (StringUtils.isNotEmpty(pager.getF().get("endTime" + ""))) {
			Long endTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("endTime" + ""),
					DateStyle.YYYY_MM_DD_HH_MM_SS);
			pager.getM().put("endTime", endTime);
		}
		List<String> deviceIds = null;
		if (StringUtils.isNotEmpty(pager.getF().get("deviceId" + ""))) {
			String deviceId = pager.getF().get("deviceId" + "");
			deviceIds = StringUtils.stringToList(deviceId);
		}
		if (StringUtils.isNotEmpty(pager.getF().get("plateNo" + ""))) {
			String plateLicence = pager.getF().get("plateNo" + "");
			plateLicence = plateLicence.toUpperCase();
			if (plateLicence.length() > 6
					&& !(plateLicence.contains("?") || plateLicence.contains("？") || plateLicence.contains("*"))) {// 精准查询
				pager.getF().put("plateNo", plateLicence);
			} else {
				pager.getF().remove("plateNo");
				if (!"非空".equals(plateLicence.trim())) {
					// 模糊匹配正则规则
					String plateNo = plateLicence.replaceAll("\\*", "\\%").replaceAll("\\?", "\\_").replaceAll("\\？",
							"\\_");
					if (plateNo.endsWith("%")) {
						//如果最后一个是*结尾，如*2*，表示后面还有数字，故加一个下划线设置一下，不输出最后是2的车牌号
						String substring = plateNo.substring(0, plateNo.length() - 1);
						pager.getF().put("plateNoMatch", substring + "_%");
					} else if (!plateNo.contains("%") && !plateNo.contains("_")) {
						pager.getF().put("plateNoMatch", "%" + plateNo + "%");
					} else {
						pager.getF().put("plateNoMatch", plateNo);
//						pager.getF().put("plateNoMatch", plateNo + "%");
					}
				} else {
					pager.getF().put("plateNoNotNull", "非空");
				}
			}
		}
		long l2 = System.currentTimeMillis();
		log.info("clickhouse_queryPage,l2-l1:" + (l2 - l1));
		pager = convertPagerParam(pager);
		long l3 = System.currentTimeMillis();
		log.info("clickhouse_queryPage,l3-l2:" + (l3 - l2));
		List<Object> newList = new ArrayList<>();
		int count = 0;
		if (pager.getF().get("capType") != null) {
			if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_PERSON) {
				List<Person> list = null;
				if (StringUtils.isNotEmpty(pager.getF().get("taskId" + ""))) {
					Task task = taskService.findTaskById(pager.getF().get("taskId" + ""));
					if (StringUtils.isEmptyOrNull(pager.getF().get("startTime" + ""))
							&& StringUtils.isEmptyOrNull(pager.getF().get("endTime" + ""))) {
						Long endTime = null;
						Long startTime = null;
						if (task != null && task.getBeginDate() != null && task.getEndDate() != null
								&& task.getEndDate().compareTo(new Date()) < 0) {
							String endDateStr = task.getEndDateStr();
							String analyEndTimeStr = task.getAnalyEndTime();
							endDateStr = endDateStr + " " + analyEndTimeStr;
							endTime = TransfTimeUtil.Date2TimeStampReturnLong(endDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							startTime = endTime - (7 * 24 * 60 * 60);
							String beginDateStr = null;
							beginDateStr = task.getBeginDateStr();
							String analyStartTimeStr = task.getAnalyStartTime();
							beginDateStr = beginDateStr + " " + analyStartTimeStr;
							Long startTimeTask = TransfTimeUtil.Date2TimeStampReturnLong(beginDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							if (startTime < startTimeTask) {
								startTime = startTimeTask;
							}
						} else if (task != null && (task.getType() == 1 || task.getType() == 2)) { // 1:实时分析的任务
																									// 2：历史结构化任务
							endTime = new Date().getTime() / 1000;
							startTime = endTime - (7 * 24 * 60 * 60);
						}
						if (startTime != null) {
							pager.getF().put("startTime",
									TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("startTime", startTime);
						}
						if (endTime != null) {
							pager.getF().put("endTime",
									TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("endTime", endTime);
						}
					}
					long l4 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l4-l3:" + (l4 - l3));
					if (task != null && task.getType() == 3) {
						list = personCKDAO.queryOffVideoListByTaskId(pager, deviceIds);
					} else if (task != null && (task.getType() == 1 || task.getType() == 2)) {
						list = personCKDAO.queryListByTaskId(pager, deviceIds);
					}
					long l5 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l5-l4:" + (l5 - l4));
					count = personCKDAO.selectCountByTaskId(pager, deviceIds);
					long l6 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l6-l5:" + (l6 - l5));
				} else {
					long l7 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l7-l3:" + (l7 - l3));
					list = personCKDAO.queryList(pager, deviceIds);
					long l8 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l8-l7:" + (l8 - l7));
					count = personCKDAO.selectCount(pager, deviceIds);
					long l9 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l9-l8:" + (l9 - l8));
				}
				long l10 = System.currentTimeMillis();
				if (list != null && list.size() > 0) {
					for (Person person : list) {
						if (StringUtils.isNotEmpty(person.getCapUrl())) {
							person.setCapUrl(Constants.PHOTO_URL_TEMP + person.getCapUrl());
						} else {
							log.error("行人的抓拍图为空,抓拍的uuid:" + person.getUuid());
						}
						// 抓拍的类型
						person.setCapType(Constants.CAP_ANALY_TYPE_PERSON);
						DeviceBean deviceBean = AlarmCache.deviceMap.get(person.getDeviceId());
						if (deviceBean != null && StringUtils.isNotEmpty(deviceBean.getDeviceName())) {
							person.setDeviceName(deviceBean.getDeviceName());
						} else {
							Channel channelAll = channelDAO.getChannelAll(person.getDeviceId());
							if (channelAll != null) {
								String deviceName = channelAll.getChannelName();
								person.setDeviceName(deviceName);
							}
						}
						newList.add(person);
					}
				}
				pager.setResultList(list);
				long l11 = System.currentTimeMillis();
				log.info("clickhouse_queryPage,l11-l10:" + (l11 - l10));
			} else if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
				List<MotorVehicle> list = null;
				if (StringUtils.isNotEmpty(pager.getF().get("taskId" + ""))) {
					Task task = taskService.findTaskById(pager.getF().get("taskId" + ""));
					if (StringUtils.isEmptyOrNull(pager.getF().get("startTime" + ""))
							&& StringUtils.isEmptyOrNull(pager.getF().get("endTime" + ""))) {
						Long endTime = null;
						Long startTime = null;
						if (task != null && task.getBeginDate() != null && task.getEndDate() != null
								&& task.getEndDate().compareTo(new Date()) < 0) {
							String endDateStr = task.getEndDateStr();
							String analyEndTimeStr = task.getAnalyEndTime();
							endDateStr = endDateStr + " " + analyEndTimeStr;
							endTime = TransfTimeUtil.Date2TimeStampReturnLong(endDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							startTime = endTime - (7 * 24 * 60 * 60);
							String beginDateStr = null;
							beginDateStr = task.getBeginDateStr();
							String analyStartTimeStr = task.getAnalyStartTime();
							beginDateStr = beginDateStr + " " + analyStartTimeStr;
							Long startTimeTask = TransfTimeUtil.Date2TimeStampReturnLong(beginDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							if (startTime < startTimeTask) {
								startTime = startTimeTask;
							}
						} else if (task != null && (task.getType() == 1 || task.getType() == 2)) {
							endTime = new Date().getTime() / 1000;
							startTime = endTime - (7 * 24 * 60 * 60);
						}
						if (startTime != null) {
							pager.getF().put("startTime",
									TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("startTime", startTime);
						}
						if (endTime != null) {
							pager.getF().put("endTime",
									TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("endTime", endTime);
						}
					}
					long l12 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l12-l3:" + (l12 - l3));
					if (task != null && task.getType() == 3) {
						list = motorVehicleCKDAO.queryOffVideoListByTaskId(pager, deviceIds);
					} else if (task != null && (task.getType() == 1 || task.getType() == 2)) {
						list = motorVehicleCKDAO.queryListByTaskId(pager, deviceIds);
					}
					long l13 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l13-l12:" + (l13 - l12));
					count = motorVehicleCKDAO.selectCountByTaskId(pager, deviceIds);
					long l14 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l14-l13:" + (l14 - l13));
				} else {
					long l15 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l15-l3:" + (l15 - l3));
					list = motorVehicleCKDAO.queryList(pager, deviceIds);
					long l16 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l16-l15:" + (l16 - l15));
					count = motorVehicleCKDAO.selectCount(pager, deviceIds);
					long l17 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l17-l16:" + (l17 - l16));
				}
				long l18 = System.currentTimeMillis();
				if (list != null && list.size() > 0) {
					for (MotorVehicle motorVehicle : list) {

						if (StringUtils.isNotEmpty(motorVehicle.getCapUrl())) {
							motorVehicle.setCapUrl(Constants.PHOTO_URL_TEMP + motorVehicle.getCapUrl());
						} else {
							log.error("机动车的抓拍图为空,抓拍的uuid:" + motorVehicle.getUuid());
						}
						// 抓拍的类型
						motorVehicle.setCapType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE);

						DeviceBean deviceBean = AlarmCache.deviceMap.get(motorVehicle.getDeviceId());
						if (deviceBean != null && StringUtils.isNotEmpty(deviceBean.getDeviceName())) {
							motorVehicle.setDeviceName(deviceBean.getDeviceName());
						} else {
							Channel channelAll = channelDAO.getChannelAll(motorVehicle.getDeviceId());
							if (channelAll != null) {
								String deviceName = channelAll.getChannelName();
								motorVehicle.setDeviceName(deviceName);
							}
						}
						newList.add(motorVehicle);
					}
				}
				pager.setResultList(list);
				long l19 = System.currentTimeMillis();
				log.info("clickhouse_queryPage,l18-l18:" + (l19 - l18));
			} else if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
				List<NonmotorVehicle> list = null;
				if (StringUtils.isNotEmpty(pager.getF().get("taskId" + ""))) {
					Task task = taskService.findTaskById(pager.getF().get("taskId" + ""));
					if (StringUtils.isEmptyOrNull(pager.getF().get("startTime" + ""))
							&& StringUtils.isEmptyOrNull(pager.getF().get("endTime" + ""))) {
						Long endTime = null;
						Long startTime = null;
						if (task != null && task.getBeginDate() != null && task.getEndDate() != null
								&& task.getEndDate().compareTo(new Date()) < 0) {
							String endDateStr = task.getEndDateStr();
							String analyEndTimeStr = task.getAnalyEndTime();
							endDateStr = endDateStr + " " + analyEndTimeStr;
							endTime = TransfTimeUtil.Date2TimeStampReturnLong(endDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							startTime = endTime - (7 * 24 * 60 * 60);
							String beginDateStr = null;
							beginDateStr = task.getBeginDateStr();
							String analyStartTimeStr = task.getAnalyStartTime();
							beginDateStr = beginDateStr + " " + analyStartTimeStr;
							Long startTimeTask = TransfTimeUtil.Date2TimeStampReturnLong(beginDateStr,
									DateStyle.YYYY_MM_DD_HH_MM_SS);
							if (startTime < startTimeTask) {
								startTime = startTimeTask;
							}
						} else if (task != null && (task.getType() == 1 || task.getType() == 2)) {
							endTime = new Date().getTime() / 1000;
							startTime = endTime - (7 * 24 * 60 * 60);
						}
						if (startTime != null) {
							pager.getF().put("startTime",
									TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("startTime", startTime);
						}
						if (endTime != null) {
							pager.getF().put("endTime",
									TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
							pager.getM().put("endTime", endTime);
						}
					}
					long l20 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l20-l3:" + (l20 - l3));
					if (task != null && task.getType() == 3) {
						list = nonmotorVehicleCKDAO.queryOffVideoListByTaskId(pager, deviceIds);
					} else if (task != null && (task.getType() == 1 || task.getType() == 2)) {
						list = nonmotorVehicleCKDAO.queryListByTaskId(pager, deviceIds);
					}
					long l21 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l21-l20:" + (l21 - l20));
					count = nonmotorVehicleCKDAO.selectCountByTaskId(pager, deviceIds);
					long l22 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l22-l21:" + (l22 - l21));
				} else {
					long l23 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l23-l3:" + (l23 - l3));
					list = nonmotorVehicleCKDAO.queryList(pager, deviceIds);
					long l24 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l24-l23:" + (l24 - l23));
					count = nonmotorVehicleCKDAO.selectCount(pager, deviceIds);
					long l25 = System.currentTimeMillis();
					log.info("clickhouse_queryPage,l25-l24:" + (l25 - l24));
				}
				long l26 = System.currentTimeMillis();
				if (list != null && list.size() > 0) {
					for (NonmotorVehicle nonmotor : list) {

						if (StringUtils.isNotEmpty(nonmotor.getCapUrl())) {
							nonmotor.setCapUrl(Constants.PHOTO_URL_TEMP + nonmotor.getCapUrl());
						} else {
							log.error("非机动车的抓拍图为空,抓拍的uuid:" + nonmotor.getUuid());
						}

						// 抓拍的类型
						nonmotor.setCapType(Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE);

						DeviceBean deviceBean = AlarmCache.deviceMap.get(nonmotor.getDeviceId());
						if (deviceBean != null && StringUtils.isNotEmpty(deviceBean.getDeviceName())) {
							nonmotor.setDeviceName(deviceBean.getDeviceName());
						} else {
							Channel channelAll = channelDAO.getChannelAll(nonmotor.getDeviceId());
							if (channelAll != null) {
								String deviceName = channelAll.getChannelName();
								nonmotor.setDeviceName(deviceName);
							}
						}
						newList.add(nonmotor);
					}
				}
				pager.setResultList(list);
				long l27 = System.currentTimeMillis();
				log.info("clickhouse_queryPage,l27-l26:" + (l27 - l26));
			}
		}
		pager.setTotalCount(count);
		return pager;
	}

	private Pager convertPagerParam(Pager pager) {
		if (StringUtils.isNotEmpty(pager.getF().get("age" + ""))) {
			pager.getM().put("age", Integer.parseInt(pager.getF().get("age" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("genderCode" + ""))) {
			pager.getM().put("genderCode", Integer.parseInt(pager.getF().get("genderCode" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("bagStyle" + ""))) {
			pager.getM().put("bagStyle", Integer.parseInt(pager.getF().get("bagStyle" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("bigBagStyle" + ""))) {
			pager.getM().put("bigBagStyle", Integer.parseInt(pager.getF().get("bigBagStyle" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("orientation" + ""))) {
			pager.getM().put("orientation", Integer.parseInt(pager.getF().get("orientation" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("motion" + ""))) {
			pager.getM().put("motion", Integer.parseInt(pager.getF().get("motion" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("cap" + ""))) {
			pager.getM().put("cap", Integer.parseInt(pager.getF().get("cap" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("respirator" + ""))) {
			pager.getM().put("respirator", Integer.parseInt(pager.getF().get("respirator" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("glass" + ""))) {
			pager.getM().put("glass", Integer.parseInt(pager.getF().get("glass" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("coatColor" + ""))) {
			pager.getM().put("coatColor", Integer.parseInt(pager.getF().get("coatColor" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("coatLength" + ""))) {
			pager.getM().put("coatLength", Integer.parseInt(pager.getF().get("coatLength" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("coatTexture" + ""))) {
			pager.getM().put("coatTexture", Integer.parseInt(pager.getF().get("coatTexture" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("trousersColor" + ""))) {
			pager.getM().put("trousersColor", Integer.parseInt(pager.getF().get("trousersColor" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("trousersLen" + ""))) {
			pager.getM().put("trousersLen", Integer.parseInt(pager.getF().get("trousersLen" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("trousersTexture" + ""))) {
			pager.getM().put("trousersTexture", Integer.parseInt(pager.getF().get("trousersTexture" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("vehicleColor" + ""))) {
			pager.getM().put("vehicleColor", Integer.parseInt(pager.getF().get("vehicleColor" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("plateColor" + ""))) {
			pager.getM().put("plateColor", Integer.parseInt(pager.getF().get("plateColor" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("vehicleClass" + ""))) {
			pager.getM().put("vehicleClass", Integer.parseInt(pager.getF().get("vehicleClass" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("plateClass" + ""))) {
			pager.getM().put("plateClass", Integer.parseInt(pager.getF().get("plateClass" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("vehicleMarkerMot" + ""))) {
			pager.getM().put("vehicleMarkerMot", Integer.parseInt(pager.getF().get("vehicleMarkerMot" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("vehicleMarkerTissuebox" + ""))) {
			pager.getM().put("vehicleMarkerTissuebox",
					Integer.parseInt(pager.getF().get("vehicleMarkerTissuebox" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("vehicleMarkerPendant" + ""))) {
			pager.getM().put("vehicleMarkerPendant", Integer.parseInt(pager.getF().get("vehicleMarkerPendant" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("sunvisor" + ""))) {
			pager.getM().put("sunvisor", Integer.parseInt(pager.getF().get("sunvisor" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("safetyBelt" + ""))) {
			pager.getM().put("safetyBelt", Integer.parseInt(pager.getF().get("safetyBelt" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("safetyBeltCopilot" + ""))) {
			pager.getM().put("safetyBeltCopilot", Integer.parseInt(pager.getF().get("safetyBeltCopilot" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("calling" + ""))) {
			pager.getM().put("calling", Integer.parseInt(pager.getF().get("calling" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("safetyBeltCopilot" + ""))) {
			pager.getM().put("safetyBeltCopilot", Integer.parseInt(pager.getF().get("safetyBeltCopilot" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("photoWidthFrom" + ""))) {
			pager.getM().put("photoWidthFrom", Integer.parseInt(pager.getF().get("photoWidthFrom" + "")));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("photoWidthEnd" + ""))) {
			pager.getM().put("photoWidthEnd", Integer.parseInt(pager.getF().get("photoWidthEnd" + "")));
		}

		return pager;
	}

	@Override
	public List<Map<String, Object>> queryChannelTraffic(String deviceId, Integer dateScope) throws Exception {
		Pager pager = new Pager();
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		query.put("deviceId", deviceId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 统计一段时间范围的通道通行量
		if (dateScope == null) {
			dateScope = 7;
		}
		for (int i = dateScope - 1; i >= 0; i--) {
			Map<String, Object> map = new HashMap<String, Object>();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -i);
			Date time = c.getTime();
			String tagDay = sf.format(time);
			long beforTime = DateUtil.getDayStartTime(c.getTime()).getTime() / 1000;
			long afterTime = beforTime + 3600 * 24;
			query.put("capTime", new BasicDBObject().append("$gte", beforTime).append("$lt", afterTime));

			pager.getM().put("deviceId", deviceId);
			pager.getM().put("startTime", beforTime);
			pager.getM().put("endTime", afterTime);
			int peopleCount = personCKDAO.selectCount(pager, null);
			int motorCount = motorVehicleCKDAO.selectCount(pager, null);
			int nonmotorCount = nonmotorVehicleCKDAO.selectCount(pager, null);

			map.put("deviceId", deviceId);
			map.put("date", tagDay);
			map.put("peopleCount", peopleCount);
			map.put("motorCount", motorCount);
			map.put("nonmotorCount", nonmotorCount);
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据uuid 抓拍时间和抓拍类型检索单个详情
	 * 
	 * @param uuid    抓拍的uuid
	 * @param capTime 抓拍的时间
	 * @param capType 抓拍类型
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2019年6月13日 下午1:44:38
	 */
	public Map<String, Object> queryCapByUuid(String uuid, Long capTime, Integer capType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> list = new ArrayList<>();
		String deviceId = null;
		if (capType == Constants.CAP_ANALY_TYPE_PERSON) {
			List<Person> personList = personCKDAO.queryByUuid(uuid, capTime);
			if (personList != null && personList.size() > 0) {
				Person person = capAttrConvertService.personConvert(personList.get(0));
				list.add(person);
				deviceId = person.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			List<MotorVehicle> motorVehicleList = motorVehicleCKDAO.queryByUuid(uuid, capTime);
			if (motorVehicleList != null && motorVehicleList.size() > 0) {
				MotorVehicle motorVehicle = capAttrConvertService.motorVehicleConvert(motorVehicleList.get(0), null);
				list.add(motorVehicle);
				deviceId = motorVehicle.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
			List<NonmotorVehicle> nonmotorVehicleList = nonmotorVehicleCKDAO.queryByUuid(uuid, capTime);
			if (nonmotorVehicleList != null && nonmotorVehicleList.size() > 0) {
				NonmotorVehicle nonmotorVehicle = capAttrConvertService
						.nonmotorVehicleConvert(nonmotorVehicleList.get(0));
				list.add(nonmotorVehicle);
				deviceId = nonmotorVehicle.getDeviceId();
			}
		}
		List<Channel> channels = channelDAO.getChannelByIds(deviceId);
		Channel channel = null;
		if (channels != null && channels.size() > 0) {
			channel = channels.get(0);
		}
		if (list != null && list.size() >= 1) {
			Map<String, Object> map = BeanUtil.transBean2Map(list.get(0));
			Object sceneUrlObj = map.get("seceneUrl");
			if (sceneUrlObj != null && StringUtils.isNotEmpty(sceneUrlObj.toString())) {
				String sceneUrl = sceneUrlObj.toString();
				BufferedImage sceneImg = ImageIO.read(new URL(sceneUrl));
				if (sceneImg != null) {
					map.put("sceneWidth", sceneImg.getWidth());
					map.put("sceneHeight", sceneImg.getHeight());
				}
			}
			param.put("model", map);
			param.put("channel", channel);
			return param;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> queryCapByUuids(Integer capType, String uuids, List<String> capUuids) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> list = new ArrayList<>();
		String deviceId = null;
		List<String> uuid = new ArrayList<>();
		if (StringUtils.isNotEmpty(uuids)) {
			uuid = Arrays.asList(uuids.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
		}
		if (capUuids != null && capUuids.size() > 0) {
			uuid = capUuids;
		}
		if (capType == Constants.CAP_ANALY_TYPE_PERSON) {
			List<Person> pList = personCKDAO.queryByUuids(uuid);
			for (Person person : pList) {
//				person = capAttrConvertService.personConvert(person);
				if (StringUtils.isNotEmpty(person.getCapUrl())) {
					person.setCapUrl(Constants.PHOTO_URL_TEMP + person.getCapUrl());
				} else {
					log.error("行人的抓拍图为空,抓拍的uuid:" + person.getUuid());
				}
				person.setCapType(Constants.CAP_ANALY_TYPE_PERSON);
				list.add(person);
				deviceId = person.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			List<MotorVehicle> pList = motorVehicleCKDAO.queryByUuids(uuid);
			for (MotorVehicle person : pList) {
//				person = capAttrConvertService.motorVehicleConvert(person);
				if (StringUtils.isNotEmpty(person.getCapUrl())) {
					person.setCapUrl(Constants.PHOTO_URL_TEMP + person.getCapUrl());
				} else {
					log.error("机动车的抓拍图为空,抓拍的uuid:" + person.getUuid());
				}
				person.setCapType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE);
				list.add(person);
				deviceId = person.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
			List<NonmotorVehicle> pList = nonmotorVehicleCKDAO.queryByUuids(uuid);
			for (NonmotorVehicle person : pList) {
//				person = capAttrConvertService.nonmotorVehicleConvert(person);
				if (StringUtils.isNotEmpty(person.getCapUrl())) {
					person.setCapUrl(Constants.PHOTO_URL_TEMP + person.getCapUrl());
				} else {
					log.error("非机动车的抓拍图为空,抓拍的uuid:" + person.getUuid());
				}
				person.setCapType(Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE);
				list.add(person);
				deviceId = person.getDeviceId();
			}
		}
		List<Channel> channels = channelDAO.getChannelByIds(deviceId);
		Channel channel = null;
		if (channels != null && channels.size() > 0) {
			channel = channels.get(0);
		}
		param.put("list", list);
		param.put("channel", channel);
		return param;
	}

	@Override
	public Map<String, Object> trafficCount(Map<String, Object> sqlParams) throws Exception {
		Pager pager = new Pager();
		pager.getM().put("capFlag", 1);
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!sqlParams.isEmpty()) {
			Long startTime = (Long) sqlParams.get("startTime");
			Long endTime = (Long) sqlParams.get("endTime");
			String type = (String) sqlParams.get("type");
			String deviceId = (String) sqlParams.get("deviceId");
			List<String> channels = null;
			if (StringUtils.isNotEmpty(deviceId)) {
				channels = Arrays.asList(deviceId.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
			}
			pager.getM().put("startTime", startTime);
			pager.getM().put("endTime", endTime);
			// 全部-0 行人-1 机动车-2 行人机动车-3
			if (!"2".equals(type)) {
				int peopleSum;
				try {
					peopleSum = personCKDAO.selectCount(pager, channels);
					param.put("peopleSum", peopleSum);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("查询行人的clickhouse失败，失败信息为：" + e.getMessage());
				}
			}
			if ("0".equals(type)) {
				int nonmotorSum;
				try {
					nonmotorSum = nonmotorVehicleCKDAO.selectCount(pager, channels);
					param.put("nonmotorSum", nonmotorSum);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("查询非机动车的clickhouse失败，失败信息为：" + e.getMessage());
				}
			}
			if (!"1".equals(type)) {
				int motorSum;
				try {
					motorSum = motorVehicleCKDAO.selectCount(pager, channels);
					param.put("motorSum", motorSum);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("查询机动车的clickhouse失败，失败信息为：" + e.getMessage());
				}
			}
			param.put("startDate", TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			param.put("endDate", TransfTimeUtil.UnixTimeStamp2Date(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
			Date startDate = new Date(startTime * 1000);
			long dayStartTime = DateUtil.getDayStartTime(startDate).getTime() / 1000;

			Date endDate = new Date(endTime * 1000);
			long dayEndTime = DateUtil.getDayEndTime(endDate).getTime() / 1000;

			long t = (dayEndTime - dayStartTime) / (3600 * 24) + 1;
			boolean flag1 = true;
			boolean flag2 = true;
			long beforTime = 0;
			for (int i = 1; i <= (int) t; i++) {
				if (flag1) {
					beforTime = startTime;
					flag1 = false;
				} else {
					beforTime = dayStartTime + 3600 * 24 * (i - 1);
				}
				long afterTime = 0;
				if (flag2) {
					if (i == t) {
						afterTime = endTime;
					} else {
						afterTime = dayStartTime + 3600 * 24 - 1;
					}
					flag2 = false;
				} else {
					if (i == t) {
						afterTime = endTime;
					} else {
						afterTime = beforTime + 3600 * 24 - 1;
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				pager.getM().put("startTime", beforTime);
				pager.getM().put("endTime", afterTime);
				if (!"2".equals(type)) {
					try {
						int peopleCount = personCKDAO.selectCount(pager, channels);
						map.put("peopleCount", peopleCount);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("查询行人的clickhouse失败，失败信息为：" + e.getMessage());
					}
				}
				if ("0".equals(type)) {
					try {
						int nonmotorCount = nonmotorVehicleCKDAO.selectCount(pager, channels);
						map.put("nonmotorCount", nonmotorCount);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("查询非机动车的clickhouse失败，失败信息为：" + e.getMessage());
					}
				}
				if (!"1".equals(type)) {
					try {
						int motorCount = motorVehicleCKDAO.selectCount(pager, channels);
						map.put("motorCount", motorCount);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("查询机动车的clickhouse失败，失败信息为：" + e.getMessage());
					}
				}
				map.put("date", TransfTimeUtil.UnixTimeStamp2Date(beforTime, DateStyle.YYYY_MM_DD));
				list.add(map);
			}
			param.put("resultList", list);
		}
		return param;
	}

	/**
	 * 删除jsonObject中value值为0,99,"",未知,全部的key
	 * 
	 * @param jo
	 * @author mingxingyu
	 * @date 2018年9月19日 下午4:04:42
	 */
	public void removeObjKey(JSONObject jo) {
		Set<String> keySet = jo.keySet();
		List<String> removeKey = new ArrayList<String>();
		for (String key : keySet) {
			Object obj = jo.get(key);
			if (obj == null || !StringUtils.isNotEmpty(obj.toString())) {
				removeKey.add(key);
				continue;
			}
			String val = obj.toString();
			if ("全部".equals(val)) {
				removeKey.add(key);
			}
		}
		for (String key : removeKey) {
			jo.remove(key);
		}
	}

	/**
	 * 针对比对结果进行排序
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年4月24日 下午6:11:01
	 */
	private List<Object> sortCmpResult(List<Object> list) {
		if (list == null || list.size() < 1) {
			return list;
		}
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if (o1 instanceof MotorVehicle) {
					MotorVehicle obj1 = (MotorVehicle) o1;
					MotorVehicle obj2 = (MotorVehicle) o2;
					if (!obj1.getScore().equals(obj2.getScore())) {
						return obj1.getScore() > obj2.getScore() ? -1 : 1;
					} else {
						return obj1.getCapTime() > obj2.getCapTime() ? -1 : 1;
					}
				}
				if (o1 instanceof NonmotorVehicle) {
					NonmotorVehicle obj1 = (NonmotorVehicle) o1;
					NonmotorVehicle obj2 = (NonmotorVehicle) o2;
					if (!obj1.getScore().equals(obj2.getScore())) {
						return obj1.getScore() > obj2.getScore() ? -1 : 1;
					} else {
						return obj1.getCapTime() > obj2.getCapTime() ? -1 : 1;
					}
				}
				if (o1 instanceof Person) {
					Person obj1 = (Person) o1;
					Person obj2 = (Person) o2;
					if (!obj1.getScore().equals(obj2.getScore())) {
						return obj1.getScore() > obj2.getScore() ? -1 : 1;
					} else {
						return obj1.getCapTime() > obj2.getCapTime() ? -1 : 1;
					}
				}
				return 0;
			}
		});
		return list;
	}

	@Override
	public Long queryPersonCount() {
		Long personCount = null;
		try {
			personCount = (long) personCKDAO.selectCount(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personCount;
	}

}
