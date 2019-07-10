package com.sensing.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.sensing.core.utils.Exception.ExpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.net.www.protocol.http.HttpURLConnection;

public class NetUtils {

	private static final Log log = LogFactory.getLog(NetUtils.class);
	
	/**
	 * 获取网络上的图片流
	 * @param url
	 * @return 图片的byte[]
	 * @author mingxingyu
	 * @date   2018年9月20日 上午11:02:17
	 */
	public static byte[] getImgBase64FromUrl(String url){
		try{
			 URL urls = new URL(null,url,new sun.net.www.protocol.http.Handler());   
	         HttpURLConnection conn = (HttpURLConnection)urls.openConnection();    
	         conn.setRequestMethod("GET");    
	         conn.setConnectTimeout(5 * 1000);    
	         InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
	         byte[] btImg = readInputStream(inStream);//得到图片的二进制数据                  
	         return btImg;
		}catch(Exception e){
			log.error("从网络获取图片失败！" + ExpUtil.getExceptionDetail(e));
			return null;
		}
	}
	
	 public static byte[] readInputStream(InputStream inStream) throws Exception{    
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
	        byte[] buffer = new byte[1024];    
	        int len = 0;    
	        while( (len=inStream.read(buffer)) != -1 ){    
	            outStream.write(buffer, 0, len);    
	        }    
	        inStream.close();    
	        return outStream.toByteArray();    
	    }
}
