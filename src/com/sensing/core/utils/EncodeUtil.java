package com.sensing.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * com.sensing.core.utils
 * BASE64 加密与解密
 * @author haowenfeng
 * @date 2018年4月23日 下午4:18:10
 */
public class EncodeUtil {
	
	/**
	 * BASE64 加密
	 * @param str 需要加密的字符串
	 * @return 加密结果串
	 * @throws Exception
	 */
	public static String encode(String str) throws Exception{
		byte[] bt = str.getBytes(); 
		String encodeStr = (new BASE64Encoder()).encodeBuffer(bt).replaceAll("\r|\n", ""); 
		return encodeStr;
	}
	
	/**
	 * BASE64 解密
	 * @param str 需要解密的字符串
	 * @return 解密结果字符串
	 * @throws Exception
	 */
	public static String decode(String str) throws Exception{
		byte[] bt = (new BASE64Decoder()).decodeBuffer(str); 
		//如果出现乱码可以改成： String(bt, "utf-8")或 gbk
		return new String(bt); 
	}

}
