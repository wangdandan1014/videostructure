package com.sensing.core.service;

import java.util.Map;

import com.sensing.core.thrift.cmp.bean.FeatureInfo;

public interface ICapService {
	
	/**
	 * 打开通道画框
	 * @param channelUuid  通道的uuid
	 * @return	接口调用返回值
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年12月6日 下午3:35:54
	 */
	public Integer openChannelFrame(String channelUuid)throws Exception;
	
	/**
	 * 根据图片的byte数组获取图片中的抓拍信息
	 * @param imgUrl
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年9月13日 上午10:31:07
	 */
	public Map<String, Object> queryCap(byte[] imgUrl) throws Exception;

	
	/**
	 * 根据图片的base64，获取该图片的特征文件
	 * @param base64
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年9月11日 上午11:25:20
	 */
	public FeatureInfo getFeaByImgBase(String base64) throws Exception;
	
	/**
	 * 根据图片的byte数组，获取该图片的特征文件
	 * @param imageBytes
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年9月11日 上午11:25:20
	 */
	public FeatureInfo getFeaByImgBytes(byte[] imageBytes) throws Exception;
	
	
}
