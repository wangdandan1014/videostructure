package com.sensing.core.service;


import com.mongodb.client.model.geojson.LineString;
import com.sensing.core.utils.ResponseBean;

import java.util.List;

/**
 * 视频预览更新心跳数据
 * <p>Title: IPreviewService</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年1月17日
 * @version 1.0
 */
public interface IPreviewService {

	/**
	 * 心跳添加更新预览缓存
	 * @param userId
	 * @param deviceId
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年1月17日 下午5:24:23
	 */
	public ResponseBean updateData(String userId, String deviceId) throws Exception;
	
	/**
	 * 用户退出通道的视频预览
	 * @param userId 用户的uuid
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2019年1月18日 上午11:10:41
	 */
	public void delDevice(List<String> device) throws Exception;
	
}