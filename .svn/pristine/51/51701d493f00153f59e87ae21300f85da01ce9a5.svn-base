package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import com.sensing.core.alarm.DeviceBean;
import com.sensing.core.alarm.JobBean;

/**
 * 
 * <p>Title: ICmpDao</p>
 * <p>Description:缓存数据初始化</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月26日
 * @version 1.0
 */
public interface IDataInitDAO {

	/**
	 * 通道与任务的关联关系
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月27日 下午5:59:39
	 */
	public List<Map<String,Object>> queryChannelJobMap()throws Exception;
	
	/**
	 * 查询任务和模板库的关联关系
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月28日 上午10:02:30
	 */
	public List<Map<String,Object>> queryJobTemplateDbMap()throws Exception;
	
	/**
	 * 查询布控的任务
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月28日 上午10:34:57
	 */
	public List<JobBean> queryJobMap()throws Exception;
	
	/**
	 * 查询可用的模板库的id集合
	 * 模板库未删除，可用状态，已被任务正在关联下的模板库的id集合
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年11月29日 下午5:31:37
	 */
	public List<Map<String,Object>> queryTemplateDbList()throws Exception;
	
	/**
	 * 查询通道列表的基础信息
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年12月3日 上午10:05:17
	 */
	public List<DeviceBean> queryChannel()throws Exception;
	
}
