package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

/**
 * @author mingxingyu
 */
public interface ITemplateObjMotorService {

	public abstract ResponseBean saveNewTemplateObjMotor(JSONObject m, TemplateObjMotor templateObjMotor,
			ResponseBean result) throws Exception;

	public ResponseBean updateTemplateObjMotor(TemplateObjMotor templateObjMotor, ResponseBean result) throws Exception;

	public abstract TemplateObjMotor findTemplateObjMotorById(java.lang.String uuid) throws Exception;

	public abstract void removeTemplateObjMotor(java.lang.String uuid) throws Exception;

	/**
	 * 存车辆信息到单目标库
	 * 
	 * @param plateNo
	 * @throws Exception
	 */
	public abstract void saveObjMotorInSimpleDB(java.lang.String plateNo);

	public Pager queryPage(Pager pager) throws Exception;

	public abstract void logicalDeleted(Integer templateDbId) throws Exception;

	public abstract List<TemplateObjMotor> queryByTemplateDbId(Integer templateDbId) throws Exception;

	public abstract List<TemplateObjMotor> queryTemplateObjMotor(Map<String, Object> params);

	public abstract ResponseBean cutMotorToOtherDB(String objUuid, String dbId, ResponseBean result) throws Exception;

	public abstract ResponseBean copyMotorToOtherDB(String objUuid, String dbId, ResponseBean result) throws Exception;

	public abstract TemplateObjMotor findByMainTemplateUuid(String uuid) throws Exception;

}