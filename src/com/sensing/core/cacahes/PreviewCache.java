package com.sensing.core.cacahes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户视频预览缓存
 * <p>Title: PreviewCache</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年1月17日
 * @version 1.0
 */
public class PreviewCache {

	//用户-通道、时间的关联关系
	public static Map<String,String[]> userPreviewMap = new ConcurrentHashMap<String, String[]>();
	//通道-打开的次数的关联关系
	public static Map<String,Integer> devicePreviewMap = new ConcurrentHashMap<String, Integer>();
    //通道-打开时间
    public static Map<String,Long> deviceTimeMap = new ConcurrentHashMap<String, Long>();

}
