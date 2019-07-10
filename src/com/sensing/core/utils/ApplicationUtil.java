package com.sensing.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 解决普通类调用spring 注入依赖接口问题
 * @author admin
 *
 */
public class ApplicationUtil implements ApplicationContextAware{
	private static ApplicationContext applicationContext = null;  
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
	       if (ApplicationUtil.applicationContext == null) {  
	        ApplicationUtil.applicationContext = applicationContext;  
	       }  
	   }  
	   public static ApplicationContext getApplicationContext() {  
	       return applicationContext;  
	   }  
	   public static <T> T getBean(Class<T> clazz) {  
	       return getApplicationContext().getBean(clazz);  
	   }  

}
