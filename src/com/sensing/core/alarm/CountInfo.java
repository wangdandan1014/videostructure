package com.sensing.core.alarm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * <p>Title: CountInfo</p>
 * <p>Description:统计信息</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年12月4日
 * @version 1.0
 */
public class CountInfo {
	//抓拍量
	public static AtomicLong capCount = new AtomicLong(0);
	//比对量
	public static AtomicLong cmpCount = new AtomicLong(0);
	//告警处理的比对数量
	public static AtomicLong dealCmpCount = new AtomicLong(0);
	//告警的数量
	public static AtomicLong alarmCount = new AtomicLong(0);
	
	public static Long startTime = null;
}
