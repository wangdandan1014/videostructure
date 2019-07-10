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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sensing.core.bean.Channel;
import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.bean.Person;
import com.sensing.core.dao.IChannelDAO;
import com.sensing.core.dao.IMotorVehicleDAO;
import com.sensing.core.dao.ITaskChannelDAO;
import com.sensing.core.service.ICapAttrConvertService;
import com.sensing.core.service.ICapService;
import com.sensing.core.service.ISearchQueryService1;
import com.sensing.core.service.ITaskService;
import com.sensing.core.utils.BeanUtil;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.time.DateStyle;
import com.sensing.core.utils.time.DateUtil;
import com.sensing.core.utils.time.QueryDateUtils;
import com.sensing.core.utils.time.TransfTimeUtil;

/**
 * 结构化信息检索
 * <p>Title: SearchQueryServiceImpl</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年12月10日
 * @version 1.0
 */
//@Service
//@SuppressWarnings("all")
public class SearchQueryServiceImpl<Obejct> implements ISearchQueryService1 {

	private static final Log log = LogFactory.getLog(SearchQueryServiceImpl.class);
	@Resource
	private MongoTemplate mongoTemplate;
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
		if (StringUtils.isNotBlank(plateNoResult)) {
			pager.getF().put("plateNo", plateNoResult);
		}

		// 检索其他的属性
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineStrs = line.split(";");
			if (words.contains(lineStrs[1]) && line != null && !"".equals(line)) {
				String[] valsArr = lineStrs[2].split(":");
				pager.getF().put(valsArr[0], valsArr[1]);
				//int lineNum = Integer.parseInt(lineStrs[0]);
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
//		if (jo.getInteger("score") != null) {
//			cmpJson.put("score", jo.getInteger("score"));
//		} else {
//			cmpJson.put("score", Constants.PHOTO_SEARCH_SCORE);
//		}
		if (jo.getDouble("score") != null) {
			cmpJson.put("score", jo.getDouble("score"));
		} else {
			cmpJson.put("score", Constants.PHOTO_SEARCH_SCORE);
		}

		// 机动车和行人获取特征文件
//		if (Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE == capType || Constants.CAP_ANALY_TYPE_PERSON == capType) {
			// 查询特征文件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uuid", capUuid);
			params.put("type", capType);
			String cmpResult = HttpDeal.sendPost(Constants.CMP_FEATURE_URL, JSONObject.toJSONString(params));
			JSONObject resultJson = JSONObject.parseObject(cmpResult, JSONObject.class);
			Integer error = resultJson.getInteger("error");
			if (error == null || error != 0) {
				log.error("调用比对服务以图搜图失败，错误信息:" + resultJson.getString("message"));
				throw new BussinessException("抓拍以图搜图调用失败!");
			}
			// 获取特征文件
			JSONObject map = (JSONObject) resultJson.get("map");
			String capFeature = map.getString("fea");

			// 组装调用以图搜图的接口的对象
			cmpJson.put("capFeature", capFeature);
			// cmpJson.put("score", Constants.PHOTO_SEARCH_SCORE);
//		}

		// 非机动车查询mongodb，已属性检索
//		if (Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE == capType) {
//			NonmotorVehicle nonmotor = (NonmotorVehicle) getMGObjectByUuid(capUuid, Constants.NONMOTOR_VEHICLE);
//
//			cmpJson.remove("capFeature");
//			cmpJson.remove("score");
//
//			if (nonmotor != null) {
//				cmpJson.put("age", nonmotor.getAge().toString());
//				cmpJson.put("genderCode", nonmotor.getGenderCode().toString());
//				cmpJson.put("vehicleClass", nonmotor.getVehicleClass().toString());
//				cmpJson.put("glass", nonmotor.getGlass().toString());
//				cmpJson.put("cap", nonmotor.getCap().toString());
//				cmpJson.put("respirator", nonmotor.getRespirator().toString());
//				cmpJson.put("motion", nonmotor.getMotion().toString());
//				cmpJson.put("orientation", nonmotor.getOrientation().toString());
//				cmpJson.put("coatColor", nonmotor.getCoatColor().toString());
//				cmpJson.put("coatLength", nonmotor.getCoatLength().toString());
//			}
//
//		}
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
//		String capType = jo.getString("capType");
		Integer capTypeInt = jo.getInteger("capType");
		String deviceId = jo.getString("deviceId");
		String startTime = jo.getString("startTime");
		String endTime = jo.getString("endTime");
		String capFeature = jo.getString("capFeature");
//		Integer score = jo.getInteger("score");
		Double score = jo.getDouble("score");
		Long startTimeUnix = 0L;
		Long endTimeUnix = 0L;

		if (StringUtils.isBlank(deviceId)) {
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
		log.info("以图搜图调用比对耗时为:" + (cmpEndTime-cmpStartTime)+"ms");
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
//				cp.setCoatColor(resultJSON.getInteger("coatColor"));
//				cp.setTrousersColor(resultJSON.getInteger("trousersColor"));
//				cp.setBagStyle(resultJSON.getInteger("bagStyle"));
//				cp.setGlass(resultJSON.getInteger("glass"));
//				cp.setCap(resultJSON.getInteger("cap"));
				cp.setScore(resultJSON.getFloat("score"));
				cp.setCapTime(resultJSON.getLong("capTime"));
				cp.setDeviceId(resultJSON.getString("deviceId"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					cp.setDeviceName(channelAll.getChannelName());
				}
//				cp = capAttrConvertService.personConvert(cp);
				cp.setCapUrl(Constants.PHOTO_URL_TEMP+resultJSON.getString("capUrl"));
				resultList.add(cp);
//				Person people = (Person) getMGObjectByUuid(cp.getUuid(), Constants.PERSON);
//				if (people != null && StringUtils.isNotEmpty(people.getCapUrl())) {
//					cp.setCapUrl(people.getCapUrl());
//					resultList.add(cp);
//				}
			}
		}
		if (capTypeInt == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			Map<String, Object> mapObj = (Map<String, Object>) map.get("map");
			List<JSONObject> list = (List<JSONObject>) mapObj.get("result1");
			for (JSONObject resultJSON : list) {

				MotorVehicle cm = new MotorVehicle();
				cm.setUuid(resultJSON.getString("uuid"));
//				cm.setPlateNo(resultJSON.getString("plateNo"));
//				cm.setVehicleColor(resultJSON.getInteger("vehicleColor"));
//				cm.setVehicleBrandTag(resultJSON.getString("vehicleBrandTag"));
//				cm.setVehicleClass(resultJSON.getInteger("vehicleClass"));
				cm.setScore(resultJSON.getFloat("score"));
//				cm.setPlateColor(resultJSON.getInteger("plateColor"));
				cm.setCapTime(resultJSON.getLong("capTime"));
				cm.setDeviceId(resultJSON.getString("deviceId"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					cm.setDeviceName(channelAll.getChannelName());
				}
//				cm = capAttrConvertService.motorVehicleConvert(cm);
				cm.setCapUrl(Constants.PHOTO_URL_TEMP+resultJSON.getString("capUrl"));
				resultList.add(cm);
//				MotorVehicle motor = (MotorVehicle) getMGObjectByUuid(cm.getUuid(), Constants.MOTOR_VEHICLE);
//				if (motor != null && StringUtils.isNotEmpty(motor.getCapUrl())) {
//					cm.setCapUrl(motor.getCapUrl());
//					resultList.add(cm);
//				}
			}
		}
		if ( capTypeInt == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE ) {
			Map<String, Object> mapObj = (Map<String, Object>) map.get("map");
			List<JSONObject> list = (List<JSONObject>) mapObj.get("result1");
			for (JSONObject resultJSON : list) {

				NonmotorVehicle nv = new NonmotorVehicle();
				nv.setUuid(resultJSON.getString("uuid"));
//				cm.setPlateNo(resultJSON.getString("plateNo"));
//				cm.setVehicleColor(resultJSON.getInteger("vehicleColor"));
//				cm.setVehicleBrandTag(resultJSON.getString("vehicleBrandTag"));
//				cm.setVehicleClass(resultJSON.getInteger("vehicleClass"));
				nv.setScore(resultJSON.getFloat("score"));
//				cm.setPlateColor(resultJSON.getInteger("plateColor"));
				nv.setCapTime(resultJSON.getLong("capTime"));
				nv.setDeviceId(resultJSON.getString("deviceId"));
				Channel channelAll = channelDAO.getChannelAll(resultJSON.getString("deviceId"));
				if (channelAll != null) {
					nv.setDeviceName(channelAll.getChannelName());
				}
//				cm = capAttrConvertService.motorVehicleConvert(cm);
				nv.setCapUrl(Constants.PHOTO_URL_TEMP+resultJSON.getString("capUrl"));
				resultList.add(nv);
//				MotorVehicle motor = (MotorVehicle) getMGObjectByUuid(cm.getUuid(), Constants.MOTOR_VEHICLE);
//				if (motor != null && StringUtils.isNotEmpty(motor.getCapUrl())) {
//					cm.setCapUrl(motor.getCapUrl());
//					resultList.add(cm);
//				}
			}
		}
//		}
		//结果排序
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
	public Object getMGObjectByUuid(String uuid, String capType) throws Exception {
		DBObject objQuery = new BasicDBObject();
		objQuery.put("uuid", uuid);
//		Query query = new BasicQuery(objQuery);

		DBObject dbObject = mongoTemplate.getCollection(capType).findOne(objQuery);
		if (dbObject == null) {
			log.error("检索mongodb抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
			return null;
		}
		if (Constants.PERSON.equals(capType)) {
			Object c = BeanUtil.dbObject2Bean(dbObject, new Person());
			Person capPeopleConvert = capAttrConvertService.personConvert((Person) c);
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capPeopleConvert;
		} else if (Constants.MOTOR_VEHICLE.equals(capType)) {
			Object c = BeanUtil.dbObject2Bean(dbObject, new MotorVehicle());
			MotorVehicle capMotorConvert = capAttrConvertService.motorVehicleConvert((MotorVehicle) c,null);
			String deviceId = capMotorConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capMotorConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capMotorConvert;
		} else if (Constants.NONMOTOR_VEHICLE.equals(capType)) {
			Object c = BeanUtil.dbObject2Bean(dbObject, new NonmotorVehicle());
			NonmotorVehicle capNonmotorConvert = capAttrConvertService.nonmotorVehicleConvert((NonmotorVehicle) c);
			String deviceId = capNonmotorConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capNonmotorConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capNonmotorConvert;
		}
		return null;
	}

	public Object getMGTempObjectByUuid(String uuid, String capType) throws Exception {
		DBObject objQuery = new BasicDBObject();
		objQuery.put("uuid", uuid);

		DBObject dbObject = mongoTemplate.getCollection(capType).findOne(objQuery);
		if (dbObject == null) {
			log.error("检索mongodb抓拍信息返回的结果为空.capType:" + capType + ";uuid:" + uuid);
			return null;
		}
		if (Constants.PERSON_TEMP.equals(capType)) {
			long l1 = System.currentTimeMillis();
			Object c = BeanUtil.dbObject2Bean(dbObject, new Person());
			long l2 = System.currentTimeMillis();
			log.info("mongo转换耗时----" + (l2 - l1));
			long l5 = System.currentTimeMillis();
			Person capPeopleConvert = capAttrConvertService.personConvert((Person) c);
			long l6 = System.currentTimeMillis();
			log.info("属性翻译耗时---------" + (l6 - l5));
			String deviceId = capPeopleConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capPeopleConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capPeopleConvert;
		} else if (Constants.MOTOR_VEHICLE_TEMP.equals(capType)) {
			Object c = BeanUtil.dbObject2Bean(dbObject, new MotorVehicle());
			MotorVehicle capMotorConvert = capAttrConvertService.motorVehicleConvert((MotorVehicle) c,null);
			String deviceId = capMotorConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capMotorConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capMotorConvert;
		} else if (Constants.NONMOTOR_VEHICLE_TEMP.equals(capType)) {
			Object c = BeanUtil.dbObject2Bean(dbObject, new NonmotorVehicle());
			NonmotorVehicle capNonmotorConvert = capAttrConvertService.nonmotorVehicleConvert((NonmotorVehicle) c);
			String deviceId = capNonmotorConvert.getDeviceId();
			Channel channelAll = channelDAO.getChannelAll(deviceId);
			if (channelAll != null) {
				capNonmotorConvert.setDeviceName(channelAll.getChannelName());
			}
			// -- end
			return capNonmotorConvert;
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
			MotorVehicle capMotorConvert = capAttrConvertService.motorVehicleConvert(motorVehicle,null);
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
		if (pager.getF().get("capType") != null) {
			if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_PERSON) {
				Person people = new Person();
				pager = queryMongoDB(pager, (Obejct) people, Constants.PERSON);
			} else if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
				MotorVehicle motor = new MotorVehicle();
				pager = queryMongoDB(pager, (Obejct) motor, Constants.MOTOR_VEHICLE);
			} else if (Integer.parseInt(pager.getF().get("capType")) == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
				NonmotorVehicle nonmotor = new NonmotorVehicle();
				pager = queryMongoDB(pager, (Obejct) nonmotor, Constants.NONMOTOR_VEHICLE);
			}
		}
		return pager;
	}

	private Pager queryMongoDB(Pager pager, Obejct o, String collection) throws Exception {
		List<Object> list = new ArrayList<>();
		Set<Entry<String, String>> entrySet = pager.getF().entrySet();
		Iterator iterator = entrySet.iterator();
//		List<String> removeKey = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry<String, String> key = (Entry<String, String>) iterator.next();
			// mongo里面没有capType字段，把这个检索字段去掉
			if ("capType".equals(key.getKey())) {
				iterator.remove();
				entrySet.remove(key);
			}
			if ("plateNo".equals(key.getKey()) && StringUtils.isEmpty(pager.getF().get("plateNo").toString())) {
				iterator.remove();
				entrySet.remove(key);
			}
		}
		Map map = pager.getF();
		if (map.get("vehicleClassTag") != null) {
			String vehicleClassTag = (String) map.get("vehicleClassTag");
			Map<String, String> motorTypeConvert = motorTypeConvert();
			for (Map.Entry<String, String> entry : motorTypeConvert.entrySet()) {
				if (entry.getValue().equals(vehicleClassTag)) {
					map.put("vehicleClass", entry.getKey());
					map.put("vehicleClassTag", null);
					break;
				}
			}
		}
		Object bean = BeanUtil.transMap2Bean(map, o);
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		query = BeanUtil.bean2DBObject(bean);
//		BasicDBObject sort = new BasicDBObject();// 设置排序条件
		// 1表示正序-1表示倒序
//		BasicDBObject sort = new BasicDBObject("deviceId", 1).append("capTime", -1);
		// 调整排序
		BasicDBObject sort = new BasicDBObject("capTime", -1).append("deviceId", 1);
		String channel = pager.getF().get("deviceId" + "");
		String taskId = pager.getF().get("taskId" + "");
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
			query.put("capTime", new BasicDBObject().append("$gte", startTime).append("$lte", endTime));
		}
		boolean flag = false;
		if (StringUtils.isNotEmpty(taskId)) {
			Map<Long, Long> timeMap = taskService.getAnalyTimeByUuid(taskId);
			BasicDBList or = new BasicDBList();
			if (timeMap != null && timeMap.size() > 0) {
				for (Map.Entry<Long, Long> entry : timeMap.entrySet()) {
					BasicDBObject clause = new BasicDBObject("capTime",
							new BasicDBObject().append("$gte", entry.getKey()).append("$lte", entry.getValue()));
					or.add(clause);
				}
//				query = new BasicDBObject("$or", or);
				query.put("$or", or);
			}
			BasicDBList and = new BasicDBList();
			if (StringUtils.isNotEmpty(pager.getF().get("startTime" + ""))) {
				Long startTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("startTime" + ""),
						DateStyle.YYYY_MM_DD_HH_MM_SS);
				BasicDBObject clause = new BasicDBObject("capTime", new BasicDBObject().append("$gte", startTime));
				and.add(clause);
				query.put("$and", and);
			}
			if (StringUtils.isNotEmpty(pager.getF().get("endTime" + ""))) {
				Long endTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("endTime" + ""),
						DateStyle.YYYY_MM_DD_HH_MM_SS);
				BasicDBObject clause = new BasicDBObject("capTime", new BasicDBObject().append("$lte", endTime));
				and.add(clause);
				query.put("$and", and);
			}
			// 正在处理的实时结构化任务的通道可以删除，当实时结构化任务无关联通道时，查询结果应为空
			if (!StringUtils.isNotEmpty(channel)) {
				List<String> channels = new ArrayList<String>();
				channels.add("1");
				query.put("deviceId", new BasicDBObject("$in", channels));
				flag = true;
			}
		}
		if (StringUtils.isNotEmpty(channel)) {
			List<String> channels = Arrays.asList(channel.split(",")).stream().map(s -> s.trim())
					.collect(Collectors.toList());
			query.put("deviceId", new BasicDBObject("$in", channels));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("startTime" + ""))
				&& StringUtils.isNotEmpty(pager.getF().get("endTime" + "")) && !StringUtils.isNotEmpty(taskId)
				&& !StringUtils.isNotEmpty(date)) {
			Long startTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("startTime" + ""),
					DateStyle.YYYY_MM_DD_HH_MM_SS);
			Long endTime = TransfTimeUtil.Date2TimeStampReturnLong(pager.getF().get("endTime" + ""),
					DateStyle.YYYY_MM_DD_HH_MM_SS);
			query.put("capTime", new BasicDBObject().append("$gte", startTime).append("$lte", endTime));
		}
		if (StringUtils.isNotEmpty(pager.getF().get("plateNo" + ""))) {
			String plateLicence = pager.getF().get("plateNo" + "");
			if (plateLicence.length() > 6) {// 精准查询
				query.put("plateNo", plateLicence);
			} else {
				// 模糊匹配正则规则
				String plateNo = plateLicence.replace("*", "").replaceAll("\\?", "\\\\\\\\?").replaceAll("\\？",
						"\\\\\\\\?");
				Pattern pattern = Pattern.compile("^.*" + plateNo + ".*$", Pattern.CASE_INSENSITIVE);
				query.put("plateNo", pattern);
			}
		}
		log.info(query.toString());
		log.info(query.keySet());
		log.info(query.toMap().toString());
		// 数据量过大时skip效率过低
		DBCursor dbCursor = null;
		if (pager.getPageRows() == 0) {
//			dbCursor = mongoTemplate.getCollection(collection).find(query);
			dbCursor = mongoTemplate.getCollection(collection).find(query).sort(sort);
		} else {
//			dbCursor = mongoTemplate.getCollection(collection).find(query)
					dbCursor = mongoTemplate.getCollection(collection).find(query).sort(sort)
					.skip((pager.getPageNo() - 1) * pager.getPageRows()).limit(pager.getPageRows());
		}
		// 带条件的查询总数会很慢，5千万条数据查询总数需要约9秒
		long l1 = new Date().getTime();
		Integer count = null;
		count = (int) mongoTemplate.getCollection(collection).count(query);
		long l2 = new Date().getTime();
		log.info("------------------mongo查询count耗时：" + (l2 - l1) + "ms");
		if (!flag) {

			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				if (o instanceof Person) {
					Object c = BeanUtil.dbObject2Bean(object, new Person());
					Person capPeopleConvert = capAttrConvertService.personConvert((Person) c);
					Channel channelAll = channelDAO.getChannelAll(capPeopleConvert.getDeviceId());
					if (channelAll != null) {
						String deviceName = channelAll.getChannelName();
						capPeopleConvert.setDeviceName(deviceName);
					}
					list.add(c);
				} else if (o instanceof MotorVehicle) {
					Object c = BeanUtil.dbObject2Bean(object, new MotorVehicle());
					MotorVehicle capMotorConvert = capAttrConvertService.motorVehicleConvert((MotorVehicle) c,null);
					Channel channelAll = channelDAO.getChannelAll(capMotorConvert.getDeviceId());
					if (channelAll != null) {
						String deviceName = channelAll.getChannelName();
						capMotorConvert.setDeviceName(deviceName);
					}
					list.add(capMotorConvert);
				} else if (o instanceof NonmotorVehicle) {
					Object c = BeanUtil.dbObject2Bean(object, new NonmotorVehicle());
					NonmotorVehicle capNonmotorConvert = capAttrConvertService
							.nonmotorVehicleConvert((NonmotorVehicle) c);
					Channel channelAll = channelDAO.getChannelAll(capNonmotorConvert.getDeviceId());
					if (channelAll != null) {
						String deviceName = channelAll.getChannelName();
						capNonmotorConvert.setDeviceName(deviceName);
					}
					list.add(capNonmotorConvert);
				}
			}
		}
		pager.setResultList(list);
		pager.setTotalCount(count);
		if (pager.getPageRows() != 0) {
			pager.setTotalPages(count % pager.getPageRows() == 0 ? 1 : count / pager.getPageRows() + 1);
		}
		return pager;
	}

	@Override
	public List<Map<String, Object>> queryChannelTraffic(String deviceId, Integer dateScope) throws Exception {
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
			Long peopleCount = mongoTemplate.getCollection(Constants.PERSON).count(query);
			Long motorCount = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).count(query);
			Long nonmotorCount = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).count(query);
			map.put("deviceId", deviceId);
			map.put("date", tagDay);
			map.put("peopleCount", peopleCount);
			map.put("motorCount", motorCount);
			map.put("nonmotorCount", nonmotorCount);
			list.add(map);
		}
		return list;
	}

	@Override
	public Map<String, Object> queryCapByUuid(String uuid, Integer capType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> list = new ArrayList<>();
		String deviceId = null;
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		query.put("uuid", uuid);
		if (capType == Constants.CAP_ANALY_TYPE_PERSON) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.PERSON).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				Person c = BeanUtil.dbObject2Bean(object, new Person());
				c = capAttrConvertService.personConvert(c);
				list.add(c);
				deviceId = c.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				MotorVehicle c = BeanUtil.dbObject2Bean(object, new MotorVehicle());
				c = capAttrConvertService.motorVehicleConvert(c,null);
				list.add(c);
				deviceId = c.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				NonmotorVehicle c = BeanUtil.dbObject2Bean(object, new NonmotorVehicle());
				c = capAttrConvertService.nonmotorVehicleConvert(c);
				list.add(c);
				deviceId = c.getDeviceId();
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
	public Map<String, Object> queryCapByUuids(Integer capType, String uuids, List capUuids) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> list = new ArrayList<>();
		String deviceId = null;
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		List<String> uuid = new ArrayList<>();
		if (StringUtils.isNotEmpty(uuids)) {
			uuid = Arrays.asList(uuids.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
		}
		if (capUuids != null && capUuids.size() > 0) {
			uuid = capUuids;
		}
		query.put("uuid", new BasicDBObject("$in", uuid));
		if (capType == Constants.CAP_ANALY_TYPE_PERSON) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.PERSON).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				Person c = BeanUtil.dbObject2Bean(object, new Person());
				c = capAttrConvertService.personConvert(c);
				list.add(c);
				deviceId = c.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				MotorVehicle c = BeanUtil.dbObject2Bean(object, new MotorVehicle());
				c = capAttrConvertService.motorVehicleConvert(c,null);
				list.add(c);
				deviceId = c.getDeviceId();
			}
		} else if (capType == Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) {
			DBCursor dbCursor = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).find(query);
			while (dbCursor.hasNext()) {
				DBObject object = dbCursor.next();
				NonmotorVehicle c = BeanUtil.dbObject2Bean(object, new NonmotorVehicle());
				c = capAttrConvertService.nonmotorVehicleConvert(c);
				list.add(c);
				deviceId = c.getDeviceId();
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
	public Map<String, Object> trafficCount(Map<String, Object> sqlParams) {
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!sqlParams.isEmpty()) {
			DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
			Long startTime = (Long) sqlParams.get("startTime");
			Long endTime = (Long) sqlParams.get("endTime");
			String type = (String) sqlParams.get("type");
			String deviceId = (String) sqlParams.get("deviceId");
			if (StringUtils.isNotBlank(deviceId)) {
				List<String> channels = Arrays.asList(deviceId.split(",")).stream().map(s -> s.trim())
						.collect(Collectors.toList());
				query.put("deviceId", new BasicDBObject("$in", channels));
			}
			query.put("capTime", new BasicDBObject().append("$gte", startTime).append("$lte", endTime));
			// 全部-0 行人-1 机动车-2 行人机动车-3
			if (!"2".equals(type)) {
				int peopleSum = mongoTemplate.getCollection(Constants.PERSON).find(query).size();
				param.put("peopleSum", peopleSum);
			}
			if ("0".equals(type)) {
				int nonmotorSum = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).find(query).size();
				param.put("nonmotorSum", nonmotorSum);
			}
			if (!"1".equals(type)) {
				int motorSum = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).find(query).size();
				param.put("motorSum", motorSum);
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
				query.put("capTime", new BasicDBObject().append("$gte", beforTime).append("$lte", afterTime));
				if (!"2".equals(type)) {
					int peopleCount = mongoTemplate.getCollection(Constants.PERSON).find(query).size();
					map.put("peopleCount", peopleCount);
				}
				if ("0".equals(type)) {
					int nonmotorCount = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).find(query).size();
					map.put("nonmotorCount", nonmotorCount);
				}
				if (!"1".equals(type)) {
					int motorCount = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).find(query).size();
					map.put("motorCount", motorCount);
				}
				map.put("date", TransfTimeUtil.UnixTimeStamp2Date(beforTime, DateStyle.YYYY_MM_DD));
				list.add(map);
			}
			param.put("resultList", list);
		}
		return param;
	}

	private List<Map<String, Object>> dataQuery(Map<String, Object> map, List<Map<String, Object>> list, DBObject query,
			Long startTime, long dayEndTime) {
		query.put("capTime", new BasicDBObject().append("$gte", startTime).append("$lt", dayEndTime));
		Long peopleCount = mongoTemplate.getCollection(Constants.PERSON).count(query);
		Long motorCount = mongoTemplate.getCollection(Constants.MOTOR_VEHICLE).count(query);
		Long nonmotorCount = mongoTemplate.getCollection(Constants.NONMOTOR_VEHICLE).count(query);
		map.put("date", TransfTimeUtil.UnixTimeStamp2Date(startTime, DateStyle.YYYY_MM_DD));
		map.put("peopleCount", peopleCount);
		map.put("motorCount", motorCount);
		map.put("nonmotorCount", nonmotorCount);
		list.add(map);
		return list;
	}
	// 暂时弃用
//	@Override
//	public Map<String, Object> trafficCount(String ids, String startTime, String endTime) throws Exception {
//		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Object> motorType = motorTypeConvert();
//		Map<String, Object> nonmotorType = nonmotorTypeConvert();
//		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
//			Map<String, Object> param = null;
//			Long startTime1 = TransfTimeUtil.Date2TimeStampReturnLong(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
//			Long endTime1 = TransfTimeUtil.Date2TimeStampReturnLong(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
//			Aggregation aggregation1 = null;
//			if (StringUtils.isNotEmpty(ids)) {
//				List<String> channels = Arrays.asList(ids.split(",")).stream().map(s -> s.trim())
//						.collect(Collectors.toList());
//				aggregation1 = Aggregation.newAggregation(
//						Aggregation.match(Criteria.where("deviceId").in(channels)
//								.andOperator(Criteria.where("capTime").gte(startTime1).lt(endTime1))),
//						Aggregation.group("vehicleClass").count().as("count"));
//			} else {
//				aggregation1 = Aggregation.newAggregation(
//						Aggregation.match(Criteria.where("capTime").gte(startTime1).lt(endTime1)),
//						Aggregation.group("vehicleClass").count().as("count"));
//			}
//			AggregationResults<BasicDBObject> outputTypeCount = mongoTemplate.aggregate(aggregation1, Constants.PERSON,
//					BasicDBObject.class);
//			for (Iterator<BasicDBObject> iterator = outputTypeCount.iterator(); iterator.hasNext();) {
//				param = new HashMap<String, Object>();
//				DBObject obj = iterator.next();// { "_id" : null , "count" : 10000}
//				Integer count = (Integer) obj.get("count");
//				param.put("type", "行人");
//				param.put("count", count);
//				resultList.add(param);
//			}
//			AggregationResults<BasicDBObject> outputTypeCount1 = mongoTemplate.aggregate(aggregation1,
//					Constants.MOTOR_VEHICLE, BasicDBObject.class);
//			for (Iterator<BasicDBObject> iterator = outputTypeCount1.iterator(); iterator.hasNext();) {
//				param = new HashMap<String, Object>();
//				DBObject obj = iterator.next();// { "_id" : 4 , "count" : 10000}
//				Integer id = (Integer) obj.get("_id");
//				Integer count = (Integer) obj.get("count");
//				param.put("type", motorType.get(id.toString()));
//				param.put("count", count);
//				resultList.add(param);
//			}
//			AggregationResults<BasicDBObject> outputTypeCount2 = mongoTemplate.aggregate(aggregation1,
//					Constants.NONMOTOR_VEHICLE, BasicDBObject.class);
//			for (Iterator<BasicDBObject> iterator = outputTypeCount2.iterator(); iterator.hasNext();) {
//				param = new HashMap<String, Object>();
//				DBObject obj = iterator.next();// { "_id" : 2 , "count" : 100}
//				Integer id = (Integer) obj.get("_id");
//				Integer count = (Integer) obj.get("count");
//				param.put("type", nonmotorType.get(id.toString()));
//				param.put("count", count);
//				resultList.add(param);
//			}
//		}
//		map.put("resultList", resultList);
//		return map;
//	}

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
//			String val = jo.get(key).toString();
			Object obj = jo.get(key);
			if (obj == null || StringUtils.isEmpty(obj.toString())) {
				removeKey.add(key);
				continue;
			}
			String val = obj.toString();
			if (
//					"0".equals(val) || "99".equals(val) || "未知".equals(val) || 
			"全部".equals(val)) {
				removeKey.add(key);
			}
		}
		for (String key : removeKey) {
			jo.remove(key);
		}
	}

	/**
	 * 针对比对结果进行排序
	 * @return
	 * @author mingxingyu
	 * @date   2019年4月24日 下午6:11:01
	 */
	private List<Object> sortCmpResult(List<Object> list){
		if ( list == null || list.size() < 1 ) {
			return list;
		}
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if ( o1 instanceof MotorVehicle ) {
					MotorVehicle obj1 = (MotorVehicle)o1;
					MotorVehicle obj2 = (MotorVehicle)o2;
					if ( !obj1.getScore().equals(obj2.getScore()) ) {
						return obj1.getScore() > obj2.getScore()?-1:1;
					}else{
						return obj1.getCapTime()>obj2.getCapTime()?-1:1;
					}
				}
				if ( o1 instanceof Person ) {
					Person obj1 = (Person)o1;
					Person obj2 = (Person)o2;
					if ( !obj1.getScore().equals(obj2.getScore()) ) {
						return obj1.getScore() > obj2.getScore()?-1:1;
					}else{
						return obj1.getCapTime()>obj2.getCapTime()?-1:1;
					}
				}
				if ( o1 instanceof NonmotorVehicle ) {
					NonmotorVehicle obj1 = (NonmotorVehicle)o1;
					NonmotorVehicle obj2 = (NonmotorVehicle)o2;
					if ( !obj1.getScore().equals(obj2.getScore()) ) {
						return obj1.getScore() > obj2.getScore()?-1:1;
					}else{
						return obj1.getCapTime()>obj2.getCapTime()?-1:1;
					}
				}
				return 0;
			}
    	});
		return list;
	}
	

	private Map<String, String> motorTypeConvert() {
		Map<String, String> motorType = new HashMap<String, String>();
		motorType.put("0", "未知");
		motorType.put("1", "轿车");
		motorType.put("2", "面包车");
		motorType.put("3", "越野车（SUV）");
		motorType.put("4", "商务车（MPV）");
		motorType.put("5", "皮卡");
		motorType.put("6", "小型客车");
		motorType.put("7", "中型客车");
		motorType.put("8", "大型客车");
		motorType.put("9", "微型货车");
		motorType.put("10", "小型货车");
		motorType.put("11", "中型货车");
		motorType.put("12", "重型货车");
		motorType.put("99", "其他");
		return motorType;
	}

	@Override
	public Long queryPersonCount() {
		Long personCount = mongoTemplate.getCollection(Constants.PERSON).count();
		return personCount;
	}

}
