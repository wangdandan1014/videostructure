package com.sensing.core.datasave;

import java.util.Arrays;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sensing.core.bean.KafkaCapMsgM.pbcapturemsg;
import com.sensing.core.utils.Constants;

/**
 * 抓拍的数据保存到mongodb的实现类
 * <p>Title: DataSaveService</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p>
 * 
 * @author mingxingyu
 * @date 2019年1月11日
 * @version 1.0
 */
public class DataSaveService implements Runnable {

	private MongoTemplate mongoTemplate;
	private pbcapturemsg msg;

	public DataSaveService(pbcapturemsg msgParam, MongoTemplate mongoTemplate) {
		this.msg = msgParam;
		this.mongoTemplate = mongoTemplate;
	}

	// 将接收到的抓拍消息保存到mongodb中
	// 将文件保存到文件服务器
	// 前端还要能预览到文件
	public void run() {
		String deviceId = msg.getDeviceId();
		Integer[] capTypesArr = DataSaveCache.deviceIdMap.get(deviceId);

		if (capTypesArr == null || capTypesArr.length == 0) {
			if (Constants.CAP_ANALY_TYPE_PERSON == msg.getCapType()) {
				Document document = new Document();
				document.put("uuid", msg.getUuid());
				document.put("deviceId", msg.getDeviceId());
				document.put("capTime", msg.getCapTime());
				document.put("capUrl", msg.getCapUrl());
				document.put("seceneUrl", msg.getSeceneUrl());
				mongoTemplate.insert(document, Constants.PERSON_TEMP);
			}
			if (Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE == msg.getCapType()) {
				Document document = new Document();
				document.put("uuid", msg.getUuid());
				document.put("deviceId", msg.getDeviceId());
				document.put("capTime", msg.getCapTime());
				document.put("capUrl", msg.getCapUrl());
				document.put("seceneUrl", msg.getSeceneUrl());

				mongoTemplate.insert(document, Constants.MOTOR_VEHICLE_TEMP);
			}
			if (Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE == msg.getCapType()) {
				Document document = new Document();
				document.put("uuid", msg.getUuid());
				document.put("deviceId", msg.getDeviceId());
				document.put("capTime", msg.getCapTime());
				document.put("capUrl", msg.getCapUrl());
				document.put("seceneUrl", msg.getSeceneUrl());
				mongoTemplate.insert(document, Constants.NONMOTOR_VEHICLE_TEMP);
			}
			return;
		}

		if (Constants.CAP_ANALY_TYPE_PERSON == msg.getCapType()
				&& Arrays.binarySearch(capTypesArr, Constants.CAP_ANALY_TYPE_PERSON) > -1) {
			Document document = new Document();
			document.put("uuid", msg.getUuid());
			document.put("deviceId", msg.getDeviceId());
			document.put("capTime", msg.getCapTime());
			document.put("frameTime", msg.getFrameTime());
			document.put("age", msg.getAge());
			document.put("genderCode", msg.getGenderCode());
			document.put("bagStyle", msg.getBagStyle());
			document.put("bigBagStyle", msg.getBigBagStyle());
			document.put("orientation", msg.getOrientation());
			document.put("motion", msg.getMotion());
			document.put("cap", msg.getCap());
			document.put("glass", msg.getGlass());
			document.put("coatColor", msg.getCoatColor());
			document.put("coatLength", msg.getCoatLength());
			document.put("coatTexture", msg.getCoatTexture());
			document.put("trousersColor", msg.getTrousersColor());
			document.put("trousersLen", msg.getTrousersLen());
			document.put("trousersTexture", msg.getTrousersTexture());
			document.put("capLocation", msg.getCapLocation());
			document.put("capUrl", msg.getCapUrl());
			document.put("seceneUrl", msg.getSeceneUrl());
			document.put("respirator", msg.getRespirator());
			// 口罩属性的转换
//			if (msg.getRespirator() == 0 || msg.getRespirator() == 1) {
//				document.put("respirator", 0);
//			} else {
//				document.put("respirator", 1);
//			}
			mongoTemplate.insert(document, Constants.PERSON);
		}
		if (Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE == msg.getCapType()
				&& Arrays.binarySearch(capTypesArr, Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE) > -1) {
			Document document = new Document();
			document.put("uuid", msg.getUuid());
			document.put("deviceId", msg.getDeviceId());
			document.put("capTime", msg.getCapTime());
			document.put("frameTime", msg.getFrameTime());
			document.put("plateNo", msg.getPlateNo());
			document.put("vehicleColor", msg.getVehicleColor());
			document.put("orientation", msg.getOrientation());
			document.put("plateColor", msg.getPlateColor());
			document.put("vehicleClass", msg.getVehicleClass());
			document.put("plateClass", msg.getPlateClass());
			document.put("vehicleBrandTag", msg.getVehicleBrandTag());
			document.put("vehicleModelTag", msg.getVehicleModelTag());
			document.put("vehicleStylesTag", msg.getVehicleStylesTag());
			document.put("vehicleMarkerMot", msg.getVehicleMarkerMot());
			document.put("vehicleMarkerTissuebox", msg.getVehicleMarkerTissuebox());
			document.put("vehicleMarkerPendant", msg.getVehicleMarkerPendant());
			document.put("sunvisor", msg.getSunvisor());
			document.put("safetyBelt", msg.getSafetyBelt());
			document.put("safetyBeltCopilot", msg.getSafetyBeltCopilot());
			document.put("calling", msg.getCalling());
			document.put("capLocation", msg.getCapLocation());
			document.put("capUrl", msg.getCapUrl());
			document.put("seceneUrl", msg.getSeceneUrl());

			mongoTemplate.insert(document, Constants.MOTOR_VEHICLE);
		}
		if (Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE == msg.getCapType()
				&& Arrays.binarySearch(capTypesArr, Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE) > -1) {
			Document document = new Document();
			document.put("uuid", msg.getUuid());
			document.put("deviceId", msg.getDeviceId());
			document.put("capTime", msg.getCapTime());
			document.put("frameTime", msg.getFrameTime());
			document.put("age", msg.getAge());
			document.put("genderCode", msg.getGenderCode());
			document.put("orientation", msg.getOrientation());
			document.put("vehicleColor", msg.getVehicleColor());
			document.put("vehicleClass", msg.getVehicleClass());
			document.put("motion", msg.getMotion());
			document.put("cap", msg.getCap());
			document.put("glass", msg.getGlass());
			document.put("coatColor", msg.getCoatColor());
			document.put("coatLength", msg.getCoatLength());
			document.put("coatTexture", msg.getCoatTexture());
			document.put("capLocation", msg.getCapLocation());
			document.put("capUrl", msg.getCapUrl());
			document.put("seceneUrl", msg.getSeceneUrl());
			document.put("respirator", msg.getRespirator());
			// 口罩属性的转换
//			if (msg.getRespirator() == 0 || msg.getRespirator() == 1) {
//				document.put("respirator", 0);
//			} else {
//				document.put("respirator", 1);
//			}
			mongoTemplate.insert(document, Constants.NONMOTOR_VEHICLE);
		}
	}

}
