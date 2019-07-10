package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.thrift.serverconfig.CapChannelConfig;
import com.sensing.core.thrift.serverconfig.LbServerInfo;
import com.sensing.core.thrift.serverconfig.LoadBalance.Client;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.props.PropUtils;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

/**
 * 
 * com.sensing.core.multithread
 * 摄像头网络检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
@SuppressWarnings("all")
public class CameraNetFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(CameraNetFuture.class);

	private String channelId;
	
	public CameraNetFuture(String channelId) {
		this.channelId = channelId;
	}



	/**
	 * 具体的业务实现方法
	 * 摄像头网络检查
	 */
	public String call() throws Exception {
		log.info("CameraNetFuture.call()开始调用");
		return getChannelCapInfos(channelId);
	}

	/**
	 * 获取抓拍通道的状态，判断是否有流。
	 * @param channelId
	 * @throws TException 
	 * 
	 */
	public static String getChannelCapInfos(String channelId) throws TException { 
		if(StringUtils.isEmptyOrNull(channelId)){
			log.error("CameraNetFuture.getChannelCapInfos中 当前通道为空!");
			return "";
		}
		String retValue = "NO";
		String capAddr =  PropUtils.getString("cap.server.ip");
		int port = PropUtils.getInt("cap.info.server.port");
		TTransport transportCMP = new TSocket(capAddr, port,2000);
		Client client = null;
		try {
			transportCMP.open();
		} catch (Exception e) {
			log.error(e);
			return "";
		}
		TProtocol protocolCMP = new TBinaryProtocol(transportCMP);
		client = new Client(protocolCMP);
		try {
			LbServerInfo lbServerInfo = client.GetServersInfo();
			List<LbServerInfo> lstSubSvrInfo = lbServerInfo.getLstSubSvrInfo();// 子服务器列表,其中如果有127.0.0.1表示该节点是主节点。
			//遍历子服务器列表
			for (LbServerInfo lbServerInfo2 : lstSubSvrInfo) {
				List<CapChannelConfig> lstChannel = lbServerInfo2.getLstChannel();
				//循环遍历通道信息
				for (CapChannelConfig capChannelConfig : lstChannel) {
					//判断是否是当前通道的信息
					if ( capChannelConfig.getUuid().equals(channelId) ) {
						Map<String, Map<String, Object>> map = JSONObject.parseObject(capChannelConfig.getStrReserve(), Map.class);
						String isOk = map.get("Metrics").get("isOk").toString();//"isOk":true
						if("true".equals(isOk)){
							retValue = "OK";
						}
						break;
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}finally {
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}
		return retValue;
	}

	/**
	 * 
	 * 获取抓拍服务与摄像头的链接状态
	 * channelStat 表示通道状态 0表示关闭 ，1表示打开  ，2表示未知，  3表示重连状态
	 * @param channelId
	 * @return 
	 * 
	 * @throws TException
	 */
	public static String getCameraLinkStatus(String channelId) throws TException {
		
		String retValue = "NO";
		
		if ( !StringUtils.isNotEmpty(channelId) ) {
			log.error("getCameraLinkStatus方法中channelId地址为空。");
			return null;
		}
		
		String capAddr =  PropUtils.getString("cap.server.ip");
		int port = PropUtils.getInt("cap.info.server.port");
		TTransport transportCMP = new TSocket(capAddr, port,2000);
		Client client = null;
		try {
			transportCMP.open();
		} catch (Exception e) {
			log.error(e);
		}
		TProtocol protocolCMP = new TBinaryProtocol(transportCMP);
		client = new Client(protocolCMP);
		
		try {
			LbServerInfo lbServerInfo = client.GetServersInfo();
			List<LbServerInfo> lstSubSvrInfo = lbServerInfo.getLstSubSvrInfo();
			
			for (LbServerInfo lbServerInfo2 : lstSubSvrInfo) {
				List<CapChannelConfig> lstChannel = lbServerInfo2.getLstChannel();
				
				for (CapChannelConfig capChannelConfig : lstChannel) {
					
					if ( capChannelConfig.getUuid().equals(channelId) ) {
						Map<String, Map<String, Object>> map = JSONObject.parseObject(capChannelConfig.getStrReserve(), Map.class);
						//channelStat 表示通道状态 0表示关闭 ，1表示打开  ，2表示未知，  3表示重连状态
						String channelStat = map.get("VideoAccess").get("channelStat").toString();
						switch (channelStat) {
						case "0":
							retValue = "关闭";
							break;
						case "1":
							retValue = "打开";
							break;
						case "2":
							retValue = "未知";
							break;
						case "3":
							retValue = "重连状态";
							break;
						default:
							break;
						}
						break;
					}
				}
				
			}
			
		}catch (Exception e) {
			log.error(e);
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}finally {
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}
		return retValue;
	}
	

	/**
	 * 根据通道获取通道被分配的IP地址
	 * @param channelId
	 * @return
	 */
	public static String  getCapIpByChannelId(String channelId){
		
		String retIp = "";
		
		if ( !StringUtils.isNotEmpty(channelId) ) {
			log.error("getCapIpByChannelId方法中channelId地址为空。");
			return retIp;
		}
		
		String capAddr =  PropUtils.getString("cap.server.ip");
		int port = PropUtils.getInt("cap.info.server.port");
		TTransport transportCMP = new TSocket(capAddr, port,2000);
		Client client = null;
		try {
			transportCMP.open();
		} catch (Exception e) {
			log.error(e);
		}
		TProtocol protocolCMP = new TBinaryProtocol(transportCMP);
		client = new Client(protocolCMP);
		
		try {
			LbServerInfo lbServerInfo = client.GetServersInfo();
			List<LbServerInfo> lstSubSvrInfo = lbServerInfo.getLstSubSvrInfo();
			
			for (LbServerInfo lbServerInfo2 : lstSubSvrInfo) {
				List<CapChannelConfig> lstChannel = lbServerInfo2.getLstChannel();
				if(lstChannel!=null&&lstChannel.size()>0){
					for (CapChannelConfig capChannelConfig : lstChannel) {
						if ( capChannelConfig.getUuid().equals(channelId) ) {
							String strIP = lbServerInfo2.getStrIP();
							if ( strIP != null && strIP.equals("127.0.0.1") ) {
								//说明通道被分配到了本服务，且是主服务
								return capAddr;
							}else{
								return strIP;
							}
						}else {
							continue;
						}
					}
				}
			}
			
		}catch (Exception e) {
			log.error(e);
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}finally {
			if (transportCMP.isOpen()) {
				transportCMP.close();
			}
		}
		return retIp;
	}
	
	

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	
	
}
