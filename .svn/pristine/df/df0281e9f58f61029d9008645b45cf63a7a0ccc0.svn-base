package com.sensing.core.cacahes;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.sensing.core.bean.Channel;
import com.sensing.core.bean.Regions;

/**
 * 
 * com.sensing.core.cacahes
 * 本地缓存
 * @author haowenfeng
 * @date 2018年4月12日 下午7:52:03
 */
@Service
public class LocalCache {


	
	/**
	 * 存储未被删除的区域信息（根据通道ID查询出的数据）
	 * key = channel ; value = Resions
	 */
	public static ConcurrentHashMap<String, Regions> chRegionData = new ConcurrentHashMap<String, Regions>();
	/**
	 * 存储未被删除的区域信息（根据通道ID查询出的数据）
	 * key = uuid ; value = channel
	 */
	public static ConcurrentHashMap<String, Channel> cacheChannelData = new ConcurrentHashMap<String, Channel>();
	public static Object capCacheMapMutex = new Object();	
	
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static synchronized void putChRegion(String key,Regions value){
		synchronized(capCacheMapMutex){
			chRegionData.put(key, value);
		}
	}
	
	/**
	 * 
	 * @param key
	 */
	public static synchronized void removeChRegion(String key){
		synchronized(capCacheMapMutex){
			chRegionData.remove(key);
		}
	}
	/**
	 * 更新本地缓存
	 * @param regionSearchCode
	 * @param region
	 */
	public static void updateLocalCacheData(List<String> channelIDs,Regions region) {
		
		if(channelIDs!=null&&channelIDs.size()>0){
			for (String key : channelIDs) {
				LocalCache.putChRegion(key, region);
			}
		}
	}
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static synchronized void putCacheChannel(String key,Channel value){
		synchronized(capCacheMapMutex){
			cacheChannelData.put(key, value);
		}
	}
	/**
	 * 
	 * @param key
	 */
	public static synchronized void removeCacheChannel(String key){
		synchronized(capCacheMapMutex){
			cacheChannelData.remove(key);
		}
	}
}
