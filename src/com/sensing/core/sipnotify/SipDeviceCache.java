package com.sensing.core.sipnotify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.Channel;
import com.sensing.core.dao.IChannelDAO;
import com.sensing.core.service.IChannelService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.props.PropUtils;

/**
 * sipserver通讯的数据库的缓存
 * <p>Title: SipDeviceCache</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年5月17日
 * @version 1.0
 */
@Component("sipDeviceCache")
public class SipDeviceCache {

	@Resource
	public IChannelDAO channelDAO;
	@Resource
	public IChannelService channelService;
	
	private Log log = LogFactory.getLog(SipDeviceCache.class); 
	
	//数据库的通道的缓存
	public static List<SipDevice> deviceList = new ArrayList<SipDevice>();
	
	//缓存中的通道15分钟之间没有收到来自sipserver的更新，则通知抓拍关闭，更新数据库中的状态
	public static int deviceKeepLive = PropUtils.getInt("sipserver.update.time");
	//是否开启sipserver服务定时更新通道
	public static boolean SIP_SERVER_UPDATE_CHANNEL = PropUtils.getBoolean("sipserver.update.onoff");

	/**
	 * 定时更新缓存数据
	 * 依据sipserver和db更新缓存中的通道数据；
	 * 从数据库查询未删除且是抓拍状态下的通道；
	 * 旧缓存没有的情况下，则新建之。有的话，则不理之；
	 * 更新的过程中，发现有超过一定时间还未收到sipserver通知的通道，更新抓拍和db服务，使抓拍状态修改为0；
	 * 
	 * @author mingxingyu
	 * @date   2019年5月17日 下午4:31:32
	 */
	@PostConstruct
	public void runTaskUpdate(){
		
		if ( !SIP_SERVER_UPDATE_CHANNEL ) {
			return;
		}
		
		try {
			//临时的通道集合
			List<SipDevice> device = new ArrayList<SipDevice>();
			
			//查询数据库中的未删除且是抓拍状态下的通道
			Pager pager = new Pager();
			pager.getF().put("isDel",Constants.DELETE_NO+"");
			pager.getF().put("capStat",Constants.CAP_STAT_OPEN+"");
			pager.getF().put("pageFlag","all");
			List<Channel> channelList = channelDAO.queryListNew(pager);
			
			for (int i = 0; i < channelList.size() ; i++) {
				Channel channel = channelList.get(i);
				// 判断库中的通道是否是已存在与缓存中的
				boolean hasOldData = false;
				SipDevice sipDevice = new SipDevice();
				//获取旧缓存中的通道的最后访问时间
				for (int j = 0; j < deviceList.size() ; j++) {
					if ( deviceList.get(j).getUuid().equals(channel.getUuid()) ) {
						hasOldData = true;
						sipDevice.setLastDate(deviceList.get(j).getLastDate());
						break;
					}
				}
				if ( !hasOldData ) {
					hasOldData = true;
					sipDevice.setLastDate(new Date());
				}
				
				//判断sipDevice缓存中的数据是否已过期，过期修改抓拍状态
				if ( System.currentTimeMillis()/1000-sipDevice.getLastDate().getTime()/1000>deviceKeepLive*60 ) {
					channel.setCapStat(Constants.CAP_STAT_CLOSE);
					channelService.updateChannel(channel);
				}else{
					//将从库中查询出来的通道的属性赋值给新的sipdevice
					BeanUtils.copyProperties(sipDevice,channel);
					device.add(sipDevice);
				}
			}
			deviceList = device;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 将从sipserver发送来的params数据解析
	 * @param params
	 * @return
	 * @author mingxingyu
	 * @date   2019年5月17日 下午4:40:35
	 */
	public ResponseBean sipNotify(JSONObject params){
		
		ResponseBean rb = new ResponseBean();

		if ( !SIP_SERVER_UPDATE_CHANNEL ) {
			rb.setError(-1);
			rb.setMessage("失败，未开启sipserver更新通道");
			return rb;
		}
		
//		Document doc = null;
        try {
        	//更新开始之前，更新缓存中的数据
            if ( deviceList != null && deviceList.size() > 0  ) {
            	runTaskUpdate();
			}
//            doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
//
//            Element rootElt = doc.getRootElement(); // 获取根节点
//            
//            if ( !"Response".equals(rootElt.getName()) ) {
//            	rb.setError(-1);
//            	rb.setMessage("解析错误，根节点不是Response");
//            	return rb;
//			}
//            String cmdTypeText = rootElt.elementTextTrim("CmdType");
//            if ( StringUtils.isNotEmpty(cmdTypeText) && !"Catalog".equals(cmdTypeText) ) {
//				rb.setError(-2);
//				rb.setMessage("CmdType节点不正确，<CmdType>Catalog</CmdType>");
//				return rb;
//			}
//
//            List<Element> selectNodes = rootElt.selectNodes("//Response//Item");
//            if ( selectNodes == null || selectNodes.size() == 0  ) {
//            	rb.setError(-3);
//            	rb.setMessage("Item集合为空");
//            	return rb;
//			}
        	JSONArray sipDeviceList = params.getJSONArray("DeviceList");
            
            for (int j = 0;j < sipDeviceList.size();j++) {
            	JSONObject jo = sipDeviceList.getJSONObject(j);
//				String name = element.elementTextTrim("Name");
//				String deviceId = element.elementTextTrim("DeviceID");
            	
				String name = jo.getString("Name");
				String deviceId = jo.getString("DeviceID");
				for (int i = 0; i < deviceList.size() ; i++) {
					SipDevice device = deviceList.get(i);
					if ( device.getChannelName().equals(name) ) {
						//更新缓存中该通道最后一次接收到信息的时间
						device.setLastDate(new Date());
						//国标编码已发生改变，通知更新抓拍和db数据
						if ( !device.getChannelNo().equals(deviceId) ) {
							Channel channel = channelService.getOneChannelByUuid(device.getUuid(),Constants.DELETE_NO+"");
							if ( channel != null ) {
								//预览地址和channelNo的替换
								channel.setPreviewAddr(channel.getPreviewAddr().replaceAll(channel.getChannelNo(),deviceId));
								channel.setChannelNo(deviceId);
								channelService.updateChannel(channel);	
							}
						}
						break;
					}
				}
			}
            rb.setError(0);
            rb.setMessage("success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sip通知本地接口发生异常:"+e.getMessage());
        }
		return rb;
	}
}
