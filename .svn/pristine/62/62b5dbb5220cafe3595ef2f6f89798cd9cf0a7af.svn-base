package com.sensing.core.alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.sensing.core.utils.props.PropUtils;

public class AlarmTask {
	
	//抓拍机动车缓存对列
	public static LinkedBlockingQueue<CapBean> capLinkedQuene = new LinkedBlockingQueue<CapBean>(1000);

	//执行比对的线程池
	public static ExecutorService cmpService = Executors.newFixedThreadPool(PropUtils.getInt("alarm.cmp.threadcount"));

	//比对结果存放的对列
	public static LinkedBlockingQueue<CmpResultBean> cmpResultQuene = new LinkedBlockingQueue<CmpResultBean>(1000);
	
	//执行判断保存数据的线程池
	public static ExecutorService dataSaveService = Executors.newFixedThreadPool(70);
	
}
