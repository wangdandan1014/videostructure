package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.alarm.CmpResultBean;

/**
 * 
 * <p>Title: ICmpDao</p>
 * <p>Description:数据库的比对</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月26日
 * @version 1.0
 */
public interface ICmpDAO {
	
	/**
	 * 检索查询
	 * @param capBean
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月26日 下午2:58:59
	 */
	public List<CmpResultBean> cmp(Map<String,String> params) throws Exception;
	
	
	/**
	 * 
	 * @param capBean
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月26日 下午2:58:59
	 */
	public List<CmpResultBean> cmpQuery(@Param("params")Map<String,Object> params) throws Exception;
}
