package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * com.sensing.core.multithread
 * 通道布控监控
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class ChanelWorkStatusFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(ChanelWorkStatusFuture.class);
	private String result;
	private String channelId;
	
//	public ChanelWorkStatusFuture(IJobChannelService jobChannelService,String channelId) {
//		this.jobChannelService = jobChannelService;
//		this.channelId = channelId;
//	}



	/**
	 * 具体的业务实现方法
	 * 查看当前通道布控情况:布控中/未布控
	 */
	public String call() throws Exception {
		log.info("ChanelWorkStatusFuture.call()开始调用");
		Integer count = 0;
		try {
//			count = jobChannelService.queryJobCountByChannelId(channelId);
		} catch (Exception e) {
			log.error(e);
			return "0";
		}
		return ""+count;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
