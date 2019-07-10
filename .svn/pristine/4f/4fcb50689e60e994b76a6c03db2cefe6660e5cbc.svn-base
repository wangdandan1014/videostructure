package com.sensing.core.cacahes;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * session的缓存类
 * <p>Title: SessionCache</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年3月18日
 * @version 1.0
 */
public class SessionCache {

	
	// 用户的uuid-session的缓存
	private static Map<String,HttpSession> sessionMap = new ConcurrentHashMap<String,HttpSession>();
	
	//session的超时时间，单位为秒
	public static int sessionTimeOut = 1800;
	
	private static Log log = LogFactory.getLog(SessionCache.class);
	
	
	public static ExecutorService service = Executors.newSingleThreadExecutor();
	
	
	static{
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	    //开启定时执行
	    executorService.scheduleAtFixedRate(new Runnable() {
	        public void run() {
	        	clearExpireData();
	        }
	    }, 5, 300, TimeUnit.SECONDS);
	}
	
	/**
	 * 清除过时的session
	 * 
	 * @author mingxingyu
	 * @date   2019年3月21日 上午10:26:34
	 */
	public static void clearExpireData(){
		service.submit(new Runnable() {
			public void run() {
				try {
					long currentTimeMillis = System.currentTimeMillis();
					int i = 0;
					Set<String> keySet = sessionMap.keySet();
					for (String sessionKey : keySet) {
						HttpSession httpSession = sessionMap.get(sessionKey);
						if ( httpSession != null && httpSession.getAttribute("lastAccessedTime") != null ) {
							Object lastAccessedTimeObj = httpSession.getAttribute("lastAccessedTime");
							if ( currentTimeMillis - Long.parseLong(lastAccessedTimeObj.toString()) >= (sessionTimeOut*1000) ) {
								sessionMap.remove(sessionKey);
								i++;
							}
						}
					}
					log.info("清理过期的session缓存数据，已删除:"+i);
				} catch (Exception e) {
					log.error("SessionCache/clearExpireData 定时清除缓存的session发生异常:"+e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * session缓存添加数据
	 * @param session
	 * @author mingxingyu
	 * @date   2019年3月20日 下午5:04:16
	 */
	public static void addCache(HttpSession session){
		if ( session != null ) {
			Object userUuidObj = session.getAttribute("userUuid");
			if ( userUuidObj != null && StringUtils.isNotEmpty(userUuidObj.toString()) ) {
				sessionMap.put(userUuidObj.toString(),session);
			}else{
				log.error("session缓存添加新session,session中的userUuid或ip为空.");
			}
		}else{
			log.error("session缓存添加新session,新session为空.");
		}
	}
	
	/**
	 * session缓存删除数据
	 * @param session
	 * @author mingxingyu
	 * @date   2019年3月20日 下午5:04:29
	 */
	public static void removeCache(HttpSession session){
		if ( session != null ) {
			Object userUuidObj = session.getAttribute("userUuid");
			if ( userUuidObj != null && StringUtils.isNotEmpty(userUuidObj.toString()) ) {
				sessionMap.remove(userUuidObj.toString());
			}else{
				log.error("session缓存添加新session,session中的userUuid或ip为空.");
			}
		}else{
			log.error("session缓存删除session时,传递的session为空.");
		}
	}

	/**
	 * 根据用户的uuid获取缓存中的session
	 * @param userUuid 用户的uuid
	 * @return
	 * @author mingxingyu
	 * @date   2019年3月20日 下午6:01:17
	 */
	public static HttpSession getSessionCache(String userUuid){
		if ( StringUtils.isNotEmpty(userUuid) ) {
			HttpSession httpSession = sessionMap.get(userUuid);
			if ( httpSession != null ) {
				return httpSession;
			}else{
				log.error("获取缓存中的session时,根据userUuid获取到的session为空.");
				return null;
			}
		}else{
			log.error("获取缓存中的session时,传递的userUuid为空.");
			return null;
		}
	}
	
	/**
	 * 根据用户的uuid删除session缓存中的用户信息
	 * @param userUuid 用户的uuid
	 * @author mingxingyu
	 * @date   2019年3月21日 下午2:44:33
	 */
	public static void removeSessionByUser(String userUuid){
		if ( StringUtils.isNotEmpty(userUuid) ) {
			sessionMap.remove(userUuid);
		}else{
			log.error("获取缓存中的session时,传递的userUuid为空.");
		}
	}
	
	/**
	 * 获取缓存中的session
	 * @param userUuid 用户的uuid
	 * @return
	 * @author mingxingyu
	 * @date   2019年3月20日 下午6:01:17
	 */
	public static int getSessionSize(){
		if ( sessionMap != null && sessionMap.size() > 0 ) {
			return sessionMap.size();
		}else{
			return 0;
		}
	}
	
}
