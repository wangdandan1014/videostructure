package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.stateutils.RemoteUtil;

/**
 * 
 * com.sensing.core.multithread
 * 比对服务检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class CmpFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 *  
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(CmpFuture.class);
	private String channelId;
	
	
	public CmpFuture(String channelId) {
		this.channelId = channelId;
	}



	/**
	 * 具体的业务实现方法
	 * 比对服务检查(需要考虑集群环境)
	 * 使用特征值检测是否可以正常比对
	 * 
	 */
	public String call() throws Exception {
		log.info("CmpFuture.call()开始调用");
		String host = CameraNetFuture.getCapIpByChannelId(channelId);
		if(StringUtils.isEmptyOrNull(host)){
			log.error("当前通道"+channelId+",未使用。即找不到指定的服务器IP");
			return "";
		}else{
			int port = 7092;
			int timeout = 3000;
			Boolean checkServerState = RemoteUtil.checkServerState(host, port, timeout);
			if(checkServerState){
				return "OK";
			}else{
				return "NO";
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
