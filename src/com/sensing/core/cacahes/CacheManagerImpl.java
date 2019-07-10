package com.sensing.core.cacahes;

import com.sensing.core.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManagerImpl {
    private static Map<String, Object> caches = new ConcurrentHashMap<String, Object>();

    /**
     * 存入缓存
     *
     * @param key
     * @param cache
     */
    public static void putCache(String key, Object cache) {
        if (StringUtils.isNotEmpty(key) && cache != null) {
            caches.put(key, cache);
        }
    }

    /**
     * 获取对应缓存
     *
     * @param key
     * @return
     */
    public static Object getCacheByKey(String key) {
        if (isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    /**
     * 判断是否在缓存中
     *
     * @param key
     * @return
     */
    public static boolean isContains(String key) {
        return caches.containsKey(key);
    }
}
