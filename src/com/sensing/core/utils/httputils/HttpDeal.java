/**
 * <p>Title: HttpDeal.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.sensingtech.com</p>
 * @author mingxingyu
 * @date 2017年8月24日下午3:19:42
 * @version 1.0
 */
package com.sensing.core.utils.httputils;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.Constants;

/**
 * <p>Title: HttpDeal</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p>
 * 
 * @author mingxingyu
 * @date 2017年8月24日下午3:19:42
 * @version 1.0
 */
@SuppressWarnings("all")
public class HttpDeal {
	
	private static final Log log = LogFactory.getLog(HttpDeal.class);
    private static String boundaryString = getBoundary();
	private final static int CONNECT_TIME_OUT = 300000;
    private final static int READ_OUT_TIME = 50000;
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
	/**
	 * httpclient发送请求更新facejob服务
	 * @param url
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年9月6日下午2:26:38
	 */
	public  static String httpPostWithJSON(String url) throws Exception {
		String respContent = null;
		CloseableHttpClient client = null;
		try {
			HttpPost httpPost = new HttpPost(url);
	        client = HttpClients.createDefault();
	        
	        JSONObject jsonParam = new JSONObject();  
	        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
	        entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json");    
	        httpPost.setEntity(entity);
	        HttpResponse resp = client.execute(httpPost);
	        StatusLine sl = resp.getStatusLine();
	        if (sl == null || sl.getStatusCode() < 200 || sl.getStatusCode() > 300){
	        	throw new Exception("请求返回状态码不正确！" + sl.getStatusCode());
	        }
	        if(sl.getStatusCode() == 200) {
	        	HttpEntity he = resp.getEntity();
	        	respContent = EntityUtils.toString(he,"UTF-8");
	    	}
		} catch (Exception e) {
			log.info("httpClient请求发生异常："+e.getMessage());
		} finally{
			try {
				if ( client != null ) {
					// 释放资源
					client.getConnectionManager().shutdown();
				}
			} catch (Exception e) {
				log.error("关闭资源异常!");
				e.printStackTrace();
			}
		}
        return respContent;
    }
	
	/**
	 * 将图片保存到图片服务器
	 * @param uri 图片保存请求路径 image/1123.jpg
	 * @param imageBytes 图片的字节数组
	 * @return
	 * @author mingxingyu
	 * @date   2017年8月24日下午3:20:22
	 */
	public static String doPut(String uri, byte[] imageBytes) {
		String resStr = null;
		HttpClient htpClient = new HttpClient();
		//HttpClient htpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );  
		PutMethod putMethod = new PutMethod(Constants.PHOTO_URL_PERSIST + uri);
		putMethod.addRequestHeader("Content-Type", "application/octet-stream");
		putMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		InputStream in = new ByteArrayInputStream(imageBytes);
		putMethod.setRequestBody(in);
		try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			int statusCode = htpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + putMethod.getStatusLine());
				return null;
			}
			byte[] responseBody = putMethod.getResponseBody();
			resStr = new String(responseBody, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			putMethod.releaseConnection();
			htpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		
		return resStr;
	}
	
	/**
	 * 调用远程接口
	 * @param url 远程地址
	 * @param body 请求体
	 * @param retryTime 连接不上的时候，重新连接的次数
	 * @return
	 * @author mingxingyu
	 * @date   2017年9月1日上午10:56:43
	 */
	public static String post(String url, String body, int retryTime) throws Exception{
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
		httpPost.setConfig(requestConfig);
		StringEntity entity;
		String res = "";
		try {
			entity = new StringEntity(body, "UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// 发送post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			StatusLine sl = httpResponse.getStatusLine();
			if (sl == null || sl.getStatusCode() < 200 || sl.getStatusCode() > 300){
				throw new Exception("请求返回状态码不正确！" + sl.getStatusCode());
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				httpEntity = new BufferedHttpEntity(httpEntity);
				res = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (Exception e) {
			int retryLimt = retryTime - 1;
			if (retryLimt <= 0) {
				log.error("调用"+url+"，达到重复次数，仍未成功", e.fillInStackTrace());
				throw new Exception("调用远程接口异常，达到重复次数，仍未成功！["+url+"]");
			} else {
				log.info("调用远程接口异常，未达到重复次数，正在重新调用  " + retryLimt,e.fillInStackTrace());
				res = post(url, body, retryLimt);
			}
		} finally {
			// 释放资源
			closeableHttpClient.close();
			closeableHttpClient.getConnectionManager().shutdown();
		}
		return res;
	}
	/**
	 * 调用远程接口   socketTimeout默认五分钟
	 * @param url 远程地址
	 * @param body 请求体
	 * @param retryTime 连接不上的时候，重新连接的次数
	 * @param connectTime 超时时间 
	 * @return
	 * @author liuwei
	 * @date   2018年4月26日
	 */
	public static String post(String url, String body, int retryTime,int connectTime) throws Exception{
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(connectTime).build();
		httpPost.setConfig(requestConfig);
		StringEntity entity;
		String res = "";
		try {
			entity = new StringEntity(body, "UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// 发送post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			StatusLine sl = httpResponse.getStatusLine();
			if (sl == null || sl.getStatusCode() < 200 || sl.getStatusCode() > 300){
				throw new Exception("请求返回状态码不正确！" + sl.getStatusCode());
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				httpEntity = new BufferedHttpEntity(httpEntity);
				res = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (Exception e) {
			int retryLimt = retryTime - 1;
			if (retryLimt <= 0) {
				log.error("调用"+url+"，达到重复次数，仍未成功", e.fillInStackTrace());
				throw new Exception("调用远程接口异常，达到重复次数，仍未成功！");
			} else {
				res = post(url, body, retryLimt,connectTime);
			}
		} finally {
			// 释放资源
			closeableHttpClient.close();
			closeableHttpClient.getConnectionManager().shutdown();
		}
		return res;
	}
	public static byte[] staticPost(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false); 
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.write(value.getBytes("UTF-8"));
        	obos.writeBytes("\r\n");

            
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
    
	public static String sendPost(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name),"UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),"UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
}
