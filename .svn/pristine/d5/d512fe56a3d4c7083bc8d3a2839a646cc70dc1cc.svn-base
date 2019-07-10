package com.sensing.core.alarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 告警缓存类
 * <p>Title: AlarmCache</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年10月30日
 * @version 1.0
 */
public class AlarmCache {

	//通道任务关联关系缓存<通道uuid,布控任务的uuid>
	public static Map<String,List<String>> deviceJobMap = new ConcurrentHashMap<String,List<String>>();
	//任务缓存
	public static Map<String,JobBean> jobMap = new ConcurrentHashMap<String,JobBean>();
	//任务关联的模板库的map
	public static Map<String,List<Integer>> jobTemplateDbMap = new HashMap<String, List<Integer>>();
	//可用的模板库的id缓存
	//public static List<Integer> templateDbList = new ArrayList<Integer>();
	//通道的缓存
	public static Map<String,DeviceBean> deviceMap = new ConcurrentHashMap<String, DeviceBean>();

	//告警的缓存 key:deviceId+personId+jobsId+vehicleId，value:告警对象
	public static Map<String, AlarmInfo> alarmInfoMap = new ConcurrentHashMap<String,AlarmInfo>();
	//计数器
	public static AtomicInteger alarmProcessAI = new AtomicInteger(0);
	//单目标库的id
	public final static Integer singleTemplateDbId = 1;
	
	//告警缓存的时间
	public final static Integer alarmCacheTime = 300;
}
