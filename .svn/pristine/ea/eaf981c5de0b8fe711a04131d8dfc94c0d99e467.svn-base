package com.sensing.core.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.Channel;
import com.sensing.core.bean.Mq;
import com.sensing.core.bean.SysOrg;
import com.sensing.core.service.IChannelService;
import com.sensing.core.service.ISysOrgService;
import com.sensing.core.thrift.serverconfig.CapChannelConfig;
import com.sensing.core.thrift.serverconfig.LbServerInfo;
import com.sensing.core.thrift.serverconfig.LoadBalance.Client;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.props.PropUtils;
/**
 * 获取mq的地址
 * <p>Title: MqController</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2017年12月18日
 * @version 1.0
 */

@Controller
@RequestMapping("/Mq")
@SuppressWarnings("all")
public class MqController {
	
	private Log log = LogFactory.getLog(MqController.class);
	
	@Resource
	public ISysOrgService sysOrgService;
	@Resource
	public IChannelService channelService;
	
 	/**
	 * 获取告警报警信息的mq地址
	 * @param m
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年12月18日 下午4:53:43
	 */
	@ResponseBody
	@RequestMapping("/getSysMq")
	public ResponseBean getSysMq(@RequestBody JSONObject m) throws Exception
	{
		Mq model = new Mq();
		model.setUrl(PropUtils.getString("mq.capsingle.url"));
		model.setUsername(PropUtils.getString("mq.cap.usename"));
		model.setPassword(PropUtils.getString("mq.cap.password"));
		ResponseBean result = new ResponseBean();
		result.getMap().put("model", model);
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
	
	/**
	 * 根据通道的id获取抓拍的mq地址
	 * @param m
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年12月18日 下午4:53:43
	 */
	@ResponseBody
	@RequestMapping("/getCapMq")
	public ResponseBean getCapMq(@RequestBody JSONObject m) throws Exception{
		ResponseBean result = new ResponseBean();
		
		String channelId = m.getString("channelId");
		if (channelId == null ) {
			result.setError(1000);
			result.setMessage("非法的请求参数");
			return result;
		}
		
		String mqUrl = "";
		//查看mq部署的方式
		int mqDeployType = PropUtils.getInt("mq.cap.deploy.type");

		//表示抓拍-MQ-facejob-比对一体机绑定模式（仅订阅一体机的MQ中的抓拍）
		if ( mqDeployType ==  2) {
			//同步模式下
			if ( !"*".equals(PropUtils.getString("deploy.channel.org.ids")) ) {
				// 根据通道的id获取通道的详情
				Channel channel = channelService.getOneChannelByUuid(channelId, Constants.DELETE_NO.toString());
				if ( channel != null && channel.getOrgId() != null ) {
					SysOrg sysOrg = sysOrgService.findSysOrgById(channel.getOrgId());
					String mqAddr = getMqAddrByChannelId(sysOrg.getCapAddr(),PropUtils.getInt("cap.info.server.port"),channelId);
					if ( mqAddr == null ) {
						log.error("MqController的方法getCapMq()，一体机同步模式下根据通道id获取mq地址为空！");
					}else {
						mqUrl = mqAddr;
					}
				}else{
					log.error("根据通道获取mq地址失败，通道不存在或是通道的orgId不存在。通道uuid："+channelId);
				}
			}else{
				// 一体机模式下
				String mqAddr = getMqAddrByChannelId(PropUtils.getString("cap.server.ip"),PropUtils.getInt("cap.info.server.port"),channelId);
				if ( mqAddr == null ) {
					log.error("MqController的方法getCapMq()，一体机非同步模式下根据通道id获取mq地址为空！");
				}else {
					mqUrl = mqAddr;
				}
			}
		}
		//表示MQ集群模式（订阅MQ集群抓拍并按facecore分配的通道处理）
		//表示抓拍预览转发MQ模式
		else {
			mqUrl = PropUtils.getString("mq.cap.url");
		}
		
		Mq model = new Mq();
		model.setUrl(mqUrl);
		model.setUsername(PropUtils.getString("mq.cap.usename"));
		model.setPassword(PropUtils.getString("mq.cap.password"));
		result.getMap().put("model", model);
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
	
	/**
	 * 根据通道的id查询对应的mq的地址
	 * @param channelId
	 * @return
	 * @author mingxingyu
	 * @date   2018年1月17日 下午2:14:28
	 */
	public String getMqAddrByChannelId(String capAddr,Integer port,String channelId){
		String mqIp = null;
		//传值判断
		if ( !StringUtils.isNotEmpty(capAddr) ) {
			log.error("getMqAddrByChannelId方法的capAddr地址为空。");
			return mqIp;
		}
		if ( !StringUtils.isNotEmpty(channelId) ) {
			log.error("getMqAddrByChannelId方法的channelId地址为空。");
			return mqIp;
		}
		if ( port < 1 ) {
			log.error("getMqAddrByChannelId方法的port地址错误。");
			return mqIp;
		}
		try {
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
							mqIp = lbServerInfo2.getStrIP();
							if ( mqIp != null && mqIp.equals("127.0.0.1") ) {
								mqIp = capAddr;
							}
							break;
						}
					}
					if ( mqIp != null ) {
						break;
					}
				}
				if (transportCMP.isOpen()) {
					transportCMP.close();
				}
			} finally {
				if (transportCMP.isOpen()) {
					transportCMP.close();
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		if ( mqIp != null ) {
			return "tcp://"+mqIp+":"+PropUtils.getString("mq.cap.allinone.port");
		}else{
			return null;
		}
	}
	
}
