package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ITemplateObjMotorDAO {
	public int saveTemplateObjMotor(TemplateObjMotor templateObjMotor);

	public TemplateObjMotor getTemplateObjMotor(java.lang.String uuid) throws Exception;

	public int removeTemplateObjMotor(java.lang.String uuid) throws Exception;

	public int updateTemplateObjMotor(TemplateObjMotor templateObjMotor) throws Exception;

	public List<TemplateObjMotor> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public List<TemplateObjMotor> queryByTemplateDbId(Integer templatedbId) throws Exception;

	public void logicalDeleted(@Param("templatedbId") Integer templatedbId) throws Exception;

	public List<TemplateObjMotor> queryTemplateObjMotor(@Param("params") Map<String, Object> params);

	public TemplateObjMotor findByMainTemplateUuid(String uuid) throws Exception;

}
