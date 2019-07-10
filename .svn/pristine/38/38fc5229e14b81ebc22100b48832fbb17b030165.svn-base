package com.sensing.core.clickhouseDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.utils.Pager;

public interface IMotorVehicleCKDAO {

	public int saveMotorVehicle(@Param(value = "motorVehicle") MotorVehicle motorVehicle,
			@Param(value = "taskIds") List<String> taskUuids) throws Exception;

	public List<MotorVehicle> queryByUuid(@Param(value = "uuid") String uuid, @Param(value = "capTime") Long capTime)
			throws Exception;

	public List<MotorVehicle> queryList(@Param(value = "pager") Pager pager,
			@Param(value = "deviceIds") List<String> deviceIds) throws Exception;

	public int selectCount(@Param(value = "pager") Pager pager, @Param(value = "deviceIds") List<String> deviceIds)
			throws Exception;

	public List<MotorVehicle> queryByUuids(@Param(value = "uuids") List<String> uuids) throws Exception;

	public List<MotorVehicle> queryListByTaskId(@Param("pager") Pager pager,
			@Param("deviceIds") List<String> deviceIds);

	public int selectCountByTaskId(@Param("pager") Pager pager, @Param("deviceIds") List<String> deviceIds);

	public List<MotorVehicle> queryOffVideoListByTaskId(@Param("pager") Pager pager,
			@Param("deviceIds") List<String> deviceIds);

}
