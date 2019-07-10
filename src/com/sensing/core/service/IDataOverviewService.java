package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.sensing.core.utils.Pager;

/**
 * 
 * <p>
 * Title: IDataOverviewService
 * </p>
 * <p>
 * Description:数据概览统计
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 * 
 * @author mingxingyu
 * @date 2018年12月5日
 * @version 1.0
 */
public interface IDataOverviewService {

	/**
	 * 根据时间查找告警的数量
	 * 
	 * @param sqlParams
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月5日 上午10:44:06
	 */
	public List<Map<String, Object>> alarmCount(Map<String, Object> sqlParams) throws Exception;

	/**
	 * 统计时间段范围内每天的告警的数量
	 * 
	 * @param sqlParams
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年12月5日 下午1:51:13
	 */
	public List<Map<String, Object>> alarmCountByDay(Map<String, Object> sqlParams) throws Exception;

	public List<Map<String, Object>> alarmTypeStatistics(Map<String, Object> sqlParams) throws Exception;

	public List<Map<String, Object>> regionAlarmStatistics(Map<String, Object> sqlParams) throws Exception;

	public Map<String, Object> alarmStatistics(Map<String, Object> sqlParams)throws Exception;

}
