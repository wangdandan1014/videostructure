package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sensing.core.utils.StringUtils;

/**
 * 
 * com.sensing.core.multithread
 * 缓存检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class RedisClusterFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(RedisClusterFuture.class);
	private String channelId;
	public RedisClusterFuture(String channelId) {
		this.channelId = channelId;
	}



	/**
	 * 
	 * 具体的业务实现方法
	 * 检查缓存是否正常工作(需要考虑集群部署方式)
	 * 
	 */
	public String call() throws Exception {
		log.info("RedisClusterFuture.call()开始调用");
		String ip = CameraNetFuture.getCapIpByChannelId(channelId);
		if(StringUtils.isEmptyOrNull(ip)){
			log.error("通过抓拍服务，未获取到通道使用信息。即当前通道没有被使用。");
			return "";
		}else{
			boolean probeRedis = true;//RemoteUtil.probeRedis(ip);
			if(probeRedis){
				return  "OK";
			}else{
				return  "NO";
			}
		}
		
	}



	public String getChannelId() {
		return channelId;
	}



	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	
	
}
