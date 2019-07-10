package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.httputils.HttpDeal;

/**
 * 
 * com.sensing.core.multithread
 * 策略服务最新统计类
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class FaceJobCountFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 *  
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(FaceJobCountFuture.class);
	private String channelId;
	
	
	public FaceJobCountFuture(String channelId) {
		this.channelId = channelId;
	}



	/**
	 * 
	 * 具体的业务实现方法
	 * 当前通道的最新抓拍时间 2018-06-14 12:12:21
	 * 策略推送告警最近时间  2018-06-14 12:12:21
	 * 策略收到比对数据的最近时间 2018-06-14 01:22:44
	 * 
	 */
	public String call() throws Exception {
		
		log.info("FaceJobCountFuture.call()开始调用");
		String ip = CameraNetFuture.getCapIpByChannelId(channelId);
		if(StringUtils.isEmptyOrNull(ip)){
			log.error("当前通道"+channelId+"未被使用。");
			return "";
		}else{
			String url = "http://"+ip+":8001/facejob/heartRate/queryJobLatestNews";
			Map<String,String> map = new HashMap<String,String>();
			map.put("channelId", channelId);
			String body = JSONObject.toJSONString(map, true);
			String data = null;
			try {
				data = HttpDeal.post(url, body, 2);
			} catch (Exception e) {
				log.error("远程调用异常!",e);
				return "";
			}
			
			if(data!=null){
				return data;
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
