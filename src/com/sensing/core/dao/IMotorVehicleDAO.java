package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.alarm.req.AlarmReq;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IMotorVehicleDAO {
	public int saveMotorVehicle(MotorVehicle MotorVehicle) throws Exception;

	public MotorVehicle getMotorVehicle(java.lang.String uuid) throws Exception;

	public int removeMotorVehicle(java.lang.String uuid) throws Exception;

	public int updateMotorVehicle(MotorVehicle MotorVehicle) throws Exception;

	public List<MotorVehicle> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public List<MotorVehicle> queryCapByUuids(@Param("capUuids") List<String> capUuids) throws Exception;

	public List<MotorVehicle> queryAlarmMotor(AlarmReq alarmReq) throws Exception;

}
