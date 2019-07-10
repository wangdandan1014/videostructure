package com.sensing.core.clickhouseDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.utils.Pager;

public interface INonmotorVehicleCKDAO {

	public int saveNonmotorVehicle(@Param(value="nonmotorVehicle")NonmotorVehicle nonmotorVehicle, @Param(value="taskIds") List<String> taskUuids)throws Exception;

	public List<NonmotorVehicle> queryByUuid(@Param(value="uuid")String uuid,@Param(value="capTime") Long capTime) throws Exception;

	public List<NonmotorVehicle> queryList(@Param(value="pager")Pager pager, @Param(value="deviceIds")List<String> deviceIds) throws Exception;

	public int selectCount(@Param(value="pager")Pager pager, @Param(value="deviceIds")List<String> deviceIds) throws Exception;

	public List<NonmotorVehicle> queryByUuids(@Param(value="uuids") List<String>  uuids) throws Exception;

	public List<NonmotorVehicle> queryListByTaskId(@Param("pager")Pager pager, @Param("deviceIds")List<String> deviceIds);

	public int selectCountByTaskId(@Param("pager")Pager pager, @Param("deviceIds")List<String> deviceIds);

	public List<NonmotorVehicle> queryOffVideoListByTaskId(@Param("pager")Pager pager, @Param("deviceIds")List<String> deviceIds);
}
