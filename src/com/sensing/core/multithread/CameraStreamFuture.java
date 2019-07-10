package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * com.sensing.core.multithread
 * 摄像头视频流检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class CameraStreamFuture implements Callable<String>,Serializable{

	/**
	 * serialVersionUID
	 *  
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(CameraStreamFuture.class);
	private String channelId;
	public CameraStreamFuture(String channelId) {
		this.channelId = channelId;
	}



	/**
	 * 具体的业务实现方法
	 * 摄像头视频流检查: 推流是否成功
	 */
	public String call() throws Exception {
		log.info("CameraNetFuture.call()开始调用");
		return CameraNetFuture.getChannelCapInfos(channelId);
	}


	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	
	
}
