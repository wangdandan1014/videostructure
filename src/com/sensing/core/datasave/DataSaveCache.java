package com.sensing.core.datasave;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据缓存
 * <p>Title: DataSaveCache</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年1月11日
 * @version 1.0
 */
public class DataSaveCache {
	
	//发送kafka消息的通道数据要保存到mongodb中
	public static Map<String,Integer[]> deviceIdMap = new ConcurrentHashMap<String, Integer[]>();
	
	public static AtomicInteger shNum = new AtomicInteger(0);
	public static AtomicInteger faNum = new AtomicInteger(0);
	
	
}
