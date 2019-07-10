/**
 * <p>Title: StringUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.sensingtech.com</p>
 * @author mingxingyu
 * @date 2017年8月3日下午5:03:13
 * @version 1.0
 */
package com.sensing.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <p>Title: StringUtils</p>
 * <p>Description: </p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2017年8月3日下午5:03:13
 * @version 1.0
 */
public class StringTool {
	
	
	/**
	 * 判断字符串是否为空
	 * @author mingxingyu
	 * @date   2017年8月3日下午5:11:22
	 */
	public static boolean isNotEmpty(String str){
		if( null == str || "".equals(str) ){
			return false;
		}
		return true;
	}
	
	/**
	 * 去掉字符串末尾的最后一个字符
	 * @param str
	 * @return
	 * @author mingxingyu
	 * @date   2017年8月24日下午4:23:05
	 */
	public static String delLastChar(String str){
		if(!isNotEmpty(str)){
			return "";
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	/**
	 * 将带有","的字符串通过截取生成List
	 * author dongsl
	 * date 2017年8月9日上午9:48:12
	 */
	public static List<String> stringToList(String str){
		if(str == null || str.equals("")){
			return new ArrayList<String>();
		}else{
			String strArr[] = new String[1024];
			List<String> resList = new ArrayList<String>();
			strArr = str.split(",");
			Collections.addAll(resList, strArr);
			return resList;
		}
	}

}
