package com.sensing.core.service;

import com.sensing.core.bean.StaticTask;
import com.sensing.core.utils.Pager;

public interface IStaticTaskServce {
	
	/**
	 * 查询离线任务
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年6月19日 下午4:17:53
	 */
	public Pager queryPage(Pager pager) throws Exception;

	/**
	 * 根据任务的uuid查询离线任务
	 * @param id
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年6月19日 下午4:18:10
	 */
	public StaticTask getVedioByJobId(String id) throws Exception;

	/**
	 * 通知抓拍加载离线视频的通道
	 * @param id
	 * @return 状态码
	 * @author mingxingyu
	 * @date   2019年6月19日 下午4:22:29
	 */
	public int runCompare(String id);
	
}
