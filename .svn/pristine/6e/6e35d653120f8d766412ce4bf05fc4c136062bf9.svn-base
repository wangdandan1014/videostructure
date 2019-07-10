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
 * 文件服务检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class FileServerCheckFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(FileServerCheckFuture.class);
	private String channelId;
	public FileServerCheckFuture( String channelId ) {
		this.channelId = channelId;
	}



	/**
	 * 具体的业务实现方法
	 * 文件服务检查:是否能正常工作
	 */
	public String call() throws Exception {
		log.info("FileServerCheckFuture.call()开始调用");
		String host = CameraNetFuture.getCapIpByChannelId(channelId);
		if(StringUtils.isEmptyOrNull(host)){
			log.error("当前通道"+channelId+" 未被使用,即找不到服务IP");
			return "";
		}else{
			int port = 8500;
			int timeout = 5000;
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
