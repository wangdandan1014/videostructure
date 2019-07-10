package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.Pager;

public interface ISearchQueryService1 {

	/**
	 * 关键词检索的接口
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月6日 上午10:35:08
	 */
	public Pager queryByKeyword(Pager pager) throws Exception;

	/**
	 * 获取mongodb的单个对象
	 * 
	 * @param uuid    抓拍的uuid
	 * @param capType 抓拍的类型
	 * @return
	 * @author mingxingyu
	 * @throws Exception
	 * @date 2018年9月20日 下午2:47:56
	 */
	public Object getMGObjectByUuid(String uuid, String capType) throws Exception;

	/**
	 * 获取db中的的单个抓拍对象
	 * 
	 * @param uuid    抓拍的uuid
	 * @param capType 类型的字符串格式
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2019年2月22日 上午11:29:06
	 */
	public Object getDBObjectByUuid(String uuid, String capType) throws Exception;

	/**
	 * 抓拍图的以图搜图调用比对查询
	 * 
	 * @param jo 参数
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年9月20日 下午3:02:49
	 */
	public List<Object> queryResultByCmpByUuid(JSONObject jo) throws Exception;

	/**
	 * 以图搜图调用比对查询
	 * 
	 * @param jo
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年9月17日 下午4:44:02
	 */
	public List<Object> queryResultByCmp(JSONObject jo) throws Exception;

	Pager queryPage(Pager pager) throws Exception;

	List<Map<String, Object>> queryChannelTraffic(String deviceId, Integer dateScope) throws Exception;

	Map<String, Object> queryCapByUuid(String uuid, Integer capType) throws Exception;

	Map<String, Object> queryCapByUuids(Integer capType, String uuids, List<String> capUuids) throws Exception;

	public Map<String, Object> trafficCount(Map<String, Object> sqlParams);

	public Object getMGTempObjectByUuid(String capUuid, String string) throws Exception;

	public Long queryPersonCount();

}
