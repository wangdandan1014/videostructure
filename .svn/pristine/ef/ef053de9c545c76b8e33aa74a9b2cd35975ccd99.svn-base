package com.sensing.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class BeanUtil {

	public static Map<String, Object> transMap(Map<String, String> filterMap) {
		Map<String, Object> filterMapObj = new HashMap<String, Object>();
		if (filterMap != null) {
			for (Entry<String, String> entry : filterMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				Object objectVal = (Object) value;
				filterMapObj.put(key, objectVal);
			}
		}
		return filterMapObj;
	}

	public static Object transMap2Bean(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return null;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}

	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return map;

	}

	public static <T> T json2Bean(String jsonString, Class<T> beanCalss) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T bean = (T) JSONObject.toBean(jsonObject, beanCalss);
		return bean;
	}

	public static String beanToJson(Object bean) {
		JSONObject json = JSONObject.fromObject(bean);
		return json.toString();
	}

	public static <T> T dbObject2Bean(DBObject dbObject, T bean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return null;
		}
		java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			String varName = field.getName();
			Object object = dbObject.get(varName);
			if (object != null) {
				BeanUtils.setProperty(bean, varName, object);
			}
		}
		return bean;
	}

	public static <T> DBObject bean2DBObject(T bean) throws IllegalArgumentException, IllegalAccessException {
		if (bean == null) {
			return null;
		}
		DBObject dbObject = new BasicDBObject();
		// 获取对象对应类中的所有属性域
		java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 获取属性名
			String varName = field.getName();
			// 修改访问控制权限
			boolean accessFlag = field.isAccessible();
			if (!accessFlag) {
				field.setAccessible(true);
			}
			Object param = field.get(bean);
			if (param == null) {
				continue;
			} else if (param instanceof Integer) {// 判断变量的类型
				int value = ((Integer) param).intValue();
				dbObject.put(varName, value);
			} else if (param instanceof String) {
				String value = (String) param;
				dbObject.put(varName, value);
			} else if (param instanceof Double) {
				double value = ((Double) param).doubleValue();
				dbObject.put(varName, value);
			} else if (param instanceof Float) {
				float value = ((Float) param).floatValue();
				dbObject.put(varName, value);
			} else if (param instanceof Long) {
				long value = ((Long) param).longValue();
				dbObject.put(varName, value);
			} else if (param instanceof Boolean) {
				boolean value = ((Boolean) param).booleanValue();
				dbObject.put(varName, value);
			} else if (param instanceof Date) {
				Date value = (Date) param;
				dbObject.put(varName, value);
			} else if (param instanceof List) {
				List<Object> list = (List<Object>) param;
				dbObject.put(varName, list);
			} else if (param instanceof Map) {
				Map<Object, Object> map = (Map<Object, Object>) param;
				dbObject.put(varName, map);
			}
			// 恢复访问控制权限
			field.setAccessible(accessFlag);
		}
		return dbObject;
	}

}
