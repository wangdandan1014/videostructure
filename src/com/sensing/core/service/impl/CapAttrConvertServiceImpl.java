package com.sensing.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.bean.Person;
import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.dao.ICapAttrConvertDAO;
import com.sensing.core.service.ICapAttrConvertService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.StringUtils;

/**
 * 抓拍属性值的转换
 * <p> Title: CapAttrConvertServiceImpl </p>
 * <p> Description: </p>
 * <p> Company: www.sensingtech.com.cn </p>
 * 
 * @author mingxingyu
 * @date 2018年8月14日
 * @version 1.0
 */
@Service
public class CapAttrConvertServiceImpl implements ICapAttrConvertService {

	private static final Log log = LogFactory.getLog(CapAttrConvertServiceImpl.class);

	@Resource
	public ICapAttrConvertDAO capAttrConvertDAO;

	/**
	 * 将抓拍人对象的属性转换为可知的属性值
	 * 
	 * @param capPeople
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月11日 下午5:02:32
	 */
	public Person personConvert(Person person) throws Exception {
		if (person == null) {
			log.error("personConvert属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.personConvert(person);
			BeanUtils.copyProperties(person, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(person.getCapUrl())) {
				person.setCapUrl(Constants.PHOTO_URL_TEMP + person.getCapUrl());
			}
			if (StringUtils.isNotEmpty(person.getVideoUrl())) {
				person.setVideoUrl(Constants.PHOTO_URL_TEMP + person.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(person.getSeceneUrl())) {
				person.setSeceneUrl(Constants.PHOTO_URL_TEMP + person.getSeceneUrl());
			}
			person.setCapType(Constants.CAP_ANALY_TYPE_PERSON);
			return person;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法capPeopleConvert抓拍人员属性翻译时发生异常，" + e.getMessage());
		}
		return person;
	}

	/**
	 * 将抓拍非机动车对象的属性转换为可知的属性值
	 * 
	 * @param nonmotorVehicle
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月11日 下午5:02:32
	 */
	public NonmotorVehicle nonmotorVehicleConvert(NonmotorVehicle nonmotorVehicle) throws Exception {
		if (nonmotorVehicle == null) {
			log.error("capNonmotorConvert属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.nonmotorVehicleConvert(nonmotorVehicle);
			BeanUtils.copyProperties(nonmotorVehicle, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(nonmotorVehicle.getCapUrl())) {
				nonmotorVehicle.setCapUrl(Constants.PHOTO_URL_TEMP + nonmotorVehicle.getCapUrl());
			}
			if (StringUtils.isNotEmpty(nonmotorVehicle.getVideoUrl())) {
				nonmotorVehicle.setVideoUrl(Constants.PHOTO_URL_TEMP + nonmotorVehicle.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(nonmotorVehicle.getSeceneUrl())) {
				nonmotorVehicle.setSeceneUrl(Constants.PHOTO_URL_TEMP + nonmotorVehicle.getSeceneUrl());
			}
			nonmotorVehicle.setCapType(Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE);
			return nonmotorVehicle;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法nonmotorVehicleConvert抓拍非机动车属性翻译时发生异常，" + e.getMessage());
		}
		return nonmotorVehicle;
	}

	/**
	 * 将抓拍机动车对象的属性转换为可知的属性值
	 * 
	 * @param capPeople
	 * @param objFlag 0或空，clickhouse库；1：数据库中的
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月11日 下午5:02:32
	 */
	public MotorVehicle motorVehicleConvert(MotorVehicle motorVehicle,Integer objFlag) throws Exception {
		if (motorVehicle == null) {
			log.error("capMotorConvert属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.motorVehicleConvert(motorVehicle);
			BeanUtils.copyProperties(motorVehicle, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(motorVehicle.getCapUrl())) {
				motorVehicle.setCapUrl( (objFlag==null||objFlag==0?Constants.PHOTO_URL_TEMP:Constants.PHOTO_URL_PERSIST)+ motorVehicle.getCapUrl());
			}
			if (StringUtils.isNotEmpty(motorVehicle.getVideoUrl())) {
				motorVehicle.setVideoUrl((objFlag==null||objFlag==0?Constants.PHOTO_URL_TEMP:Constants.PHOTO_URL_PERSIST) + motorVehicle.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(motorVehicle.getSeceneUrl())) {
				motorVehicle.setSeceneUrl((objFlag==null||objFlag==0?Constants.PHOTO_URL_TEMP:Constants.PHOTO_URL_PERSIST) + motorVehicle.getSeceneUrl());
			}
			motorVehicle.setCapType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE);
			return motorVehicle;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法motorVehicleConvert抓拍机动车属性翻译时发生异常，" + e.getMessage());
		}
		return motorVehicle;
	}

	@Override
	public TemplateObjMotor templateObjMotorConvert(TemplateObjMotor templateObjMotor) throws Exception {
		if (templateObjMotor == null) {
			log.error("templateObjMotorConvert属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.templateObjMotorConvert(templateObjMotor);
			BeanUtils.copyProperties(templateObjMotor, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(templateObjMotor.getMainTemplateUrl())) {
				templateObjMotor.setMainTemplateUrl(Constants.PHOTO_URL_PERSIST + templateObjMotor.getMainTemplateUrl());
			}
			if (templateObjMotor.getOwnerContactinfo() != null) {
				if (templateObjMotor.getOwnerContactinfo() == 0) {
					templateObjMotor.setOwnerContactinfoTag("无");
				}
				if (templateObjMotor.getOwnerContactinfo() == 1) {
					templateObjMotor.setOwnerContactinfoTag("有");
				}
			}
			if (templateObjMotor.getVehicleBrandTag() == null) {
				templateObjMotor.setVehicleBrandTag("");
			}
			if (templateObjMotor.getVehicleModelTag() == null) {
				templateObjMotor.setVehicleModelTag("");
			}
			if (templateObjMotor.getVehicleStylesTag() == null) {
				templateObjMotor.setVehicleStylesTag("");
			}
			return templateObjMotor;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法templateObjMotorConvert机动车属性翻译时发生异常，" + e.getMessage());
		}
		return templateObjMotor;
	}

	@Override
	public TemplateObjMotor templateObjMotorReverse(TemplateObjMotor templateObjMotor) throws Exception {
		if (templateObjMotor == null) {
			log.error("templateObjMotorConvert属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.templateObjMotorReverse(templateObjMotor);
			BeanUtils.copyProperties(templateObjMotor, attrMap);
			return templateObjMotor;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法templateObjMotorConvert机动车属性翻译时发生异常，" + e.getMessage());
		}
		return templateObjMotor;
	}

	@Override
	public Person personConvertWithoutCapUrl(Person person) {
		if (person == null) {
			log.error("personConvertWithoutCapUrl属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.personConvert(person);
			BeanUtils.copyProperties(person, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(person.getVideoUrl())) {
				person.setVideoUrl(Constants.PHOTO_URL_TEMP + person.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(person.getSeceneUrl())) {
				person.setSeceneUrl(Constants.PHOTO_URL_TEMP + person.getSeceneUrl());
			}
			person.setCapType(Constants.CAP_ANALY_TYPE_PERSON);
			return person;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法personConvertWithoutCapUrl抓拍人员属性翻译时发生异常，" + e.getMessage());
		}
		return person;
	}

	@Override
	public MotorVehicle motorVehicleConvertWithoutCapUrl(MotorVehicle motorVehicle) {
		if (motorVehicle == null) {
			log.error("motorVehicleConvertWithoutCapUrl属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.motorVehicleConvert(motorVehicle);
			BeanUtils.copyProperties(motorVehicle, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(motorVehicle.getVideoUrl())) {
				motorVehicle.setVideoUrl(Constants.PHOTO_URL_TEMP + motorVehicle.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(motorVehicle.getSeceneUrl())) {
				motorVehicle.setSeceneUrl(Constants.PHOTO_URL_TEMP + motorVehicle.getSeceneUrl());
			}
			motorVehicle.setCapType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE);
			return motorVehicle;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法motorVehicleConvertWithoutCapUrl抓拍机动车属性翻译时发生异常，" + e.getMessage());
		}
		return motorVehicle;
	}

	@Override
	public NonmotorVehicle nonmotorVehicleConvertWithoutCapUrl(NonmotorVehicle nonmotorVehicle) {
		if (nonmotorVehicle == null) {
			log.error("nonmotorVehicleConvertWithoutCapUrl属性转换为传入的对象为空");
			return null;
		}
		try {
			Map<String, String> attrMap = capAttrConvertDAO.nonmotorVehicleConvert(nonmotorVehicle);
			BeanUtils.copyProperties(nonmotorVehicle, attrMap);
			// 文件路径添加地址
			if (StringUtils.isNotEmpty(nonmotorVehicle.getVideoUrl())) {
				nonmotorVehicle.setVideoUrl(Constants.PHOTO_URL_TEMP + nonmotorVehicle.getVideoUrl());
			}
			if (StringUtils.isNotEmpty(nonmotorVehicle.getSeceneUrl())) {
				nonmotorVehicle.setSeceneUrl(Constants.PHOTO_URL_TEMP + nonmotorVehicle.getSeceneUrl());
			}
			nonmotorVehicle.setCapType(Constants.CAP_ANALY_TYPE_NONMOTOR_VEHICLE);
			return nonmotorVehicle;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("方法nonmotorVehicleConvertWithoutCapUrl抓拍非机动车属性翻译时发生异常，" + e.getMessage());
		}
		return nonmotorVehicle;
	}
}