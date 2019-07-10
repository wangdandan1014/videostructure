package com.sensing.core.dao;

import java.util.Map;

import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.bean.Person;
import com.sensing.core.bean.TemplateObjMotor;

/**
 * 属性转换
 * <p>
 * Title: ICapAttrConvertDAO
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 * 
 * @author mingxingyu
 * @date 2018年8月14日
 * @version 1.0
 */
public interface ICapAttrConvertDAO {

	/**
	 * 抓拍人的属性转换
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月14日 上午11:23:59
	 */
	public Map<String, String> personConvert(Person person) throws Exception;

	/**
	 * 抓拍非机动车的属性转换
	 * 
	 * @param nonmotorVehicle
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月14日 上午11:24:11
	 */
	public Map<String, String> nonmotorVehicleConvert(NonmotorVehicle nonmotorVehicle) throws Exception;

	/**
	 * 抓拍机动车的属性转换
	 * 
	 * @param motorVehicle
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年8月14日 上午11:24:22
	 */
	public Map<String, String> motorVehicleConvert(MotorVehicle motorVehicle) throws Exception;

	/**
	 * 车辆信息属性转换
	 * 
	 * @param templateObjMotor
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> templateObjMotorConvert(TemplateObjMotor templateObjMotor) throws Exception;

	/**
	 * 车辆信息属性值转换为属性
	 * 
	 * @param templateObjMotor
	 * @return
	 */
	public Map<String, String> templateObjMotorReverse(TemplateObjMotor templateObjMotor);
}
