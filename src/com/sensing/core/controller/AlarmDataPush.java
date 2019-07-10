package com.sensing.core.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.service.ISysSubscribeService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;

/**
 * 服务端向web端推送报警数据
 * <p>Title: AlarmDataPush</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年7月23日
 * @version 1.0
 */
//@ServerEndpoint("/alarmDataPush")
@ServerEndpoint(value = "/alarmDataPush",configurator=SpringConfigurator.class)
@SuppressWarnings("all")
public class AlarmDataPush {
	
	@Resource
	public ISysSubscribeService sysSubscribeService;
	
    private Session session;
    public String userUuid;//用户的uuid
    public String alarmJobStr;//用户订阅的报警的任务的字符串集合
    public static CopyOnWriteArraySet<AlarmDataPush> alarmWbSockets = new CopyOnWriteArraySet<AlarmDataPush>(); //此处定义静态变量，以在其他方法中获取到所有连接
    private Log log = LogFactory.getLog(AlarmDataPush.class); 
    
    /**
     * 客户端创建websocket连接
     * @param session
     * @author mingxingyu
     * @date   2018年7月23日 上午11:17:22
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        this.session = session;
        alarmWbSockets.add(this); //将此对象存入集合中以在之后广播用，如果要实现一对一订阅，则类型对应为Map。由于这里广播就可以了随意用Set
        log.info("alarmDataPush收到链接,sessionId:"+session.getId());
//        if ( !flag ) {
//        	produceMsg();
//        	flag = true;
//		}
    }
    /**
     * 关闭连接
     * 
     * @author mingxingyu
     * @date   2018年7月23日 上午11:18:04
     */
    @OnClose
    public void onClose(){
    	try{
	    	alarmWbSockets.remove(this);//将socket对象从集合中移除，以便广播时不发送次连接。如果不移除会报错(需要测试)
	        log.info("alarmDataPush删除session,sessionid:"+session.getId());
	    } catch (Exception e) {
			log.error("websocket删除的时候发生异常."+e.getMessage());
			e.printStackTrace();
		}
    }
    /**
     * 接收前端传过来的数据。
     * @param message
     * @param session
     * @author mingxingyu
     * @date   2018年7月23日 上午11:18:20
     */
    @OnMessage
    public void onMessage(String message ,Session session){
        log.info("alarmDataPush接收到信息,sessionid:"+session.getId()+",message:"+message);
        this.userUuid = message;
        updateSubData();
    }
    
    /**
     * 服务端向客户端推送数据
     * @param message
     * @throws IOException
     * @throws EncodeException
     * @author mingxingyu
     * @date   2018年7月23日 上午11:19:17
     */
    public void sendMessage(String message) throws IOException, EncodeException {
    	try {
    		synchronized (this.session) {
    			this.session.getBasicRemote().sendText(message);
    			log.info("alarmDataPush推送信息,sessionid:"+session.getId()+",message:"+message.toString());
    		}
		} catch (Exception e) {
			log.error("服务端向客户端推送数据."+e.getMessage());
			e.printStackTrace();
		}
    }
    
  
    /**
     * 更新用户订阅的
     * 
     * @author mingxingyu
     * @date   2018年12月4日 下午6:08:42
     */
    public void updateSubData(){
    	
		try {
			Pager pager = new Pager();
			pager.getF().put("uid",userUuid);
			pager = sysSubscribeService.queryPage(pager);
			String oneLevel = ",";
			if ( pager != null && pager.getResultList() != null && pager.getResultList().size() > 0 ) {
				List<SysSubscribe> subList = pager.getResultList();
				for ( int i = 0; i < subList.size() ; i++ ) {
					SysSubscribe subscribe = subList.get(i);
					oneLevel += (subscribe.getJobId()+",");
				}
			}
			alarmJobStr = oneLevel;
		} catch (Exception e) {
			log.error("alarmDataPush中方法updateSubData发生异常:"+e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
    
    public void produceMsg(){
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	executor.submit(new Runnable() {
			public void run() {
				try {
					while ( true ) {
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("定时任务推送消息时发生异常。"+e.getMessage());
				}
			}
		});
    }
}