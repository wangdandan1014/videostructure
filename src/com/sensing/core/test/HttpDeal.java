///**
// * <p>Title: HttpDeal.java</p>
// * <p>Description: </p>
// * <p>Copyright: Copyright (c) 2017</p>
// * <p>Company: www.sensingtech.com</p>
// * @author admin
// * @date 2017年8月24日下午3:19:42
// * @version 1.0
// */
//package com.sensing.core.utils.httputils;
//
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.net.ssl.SSLException;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.PutMethod;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.StatusLine;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.entity.BufferedHttpEntity;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sensing.core.bean.ApolloAnnotationBean;
//import com.sensing.core.utils.stringutils.StringUtils;
//
///**
// * <p>Title: HttpDeal</p>
// * <p>Description:</p>
// * <p>Company: www.sensingtech.com</p>
// * 
// * @author admin
// * @date 2017年8月24日下午3:19:42
// * @version 1.0
// */
//@Component
//@SuppressWarnings("all")
//public class HttpDeal {
//	
//	private static final Log log = LogFactory.getLog(HttpDeal.class);
//	private final static int CONNECT_TIME_OUT = 300000;
//    private final static int READ_OUT_TIME = 50000;
//    
//    @Resource
//	public ApolloAnnotationBean apolloAnnotationBean;
//	public static HttpDeal httpDeal;
//    
//    @PostConstruct
//    private void init(){
//    	httpDeal=this;
//    	httpDeal.apolloAnnotationBean = this.apolloAnnotationBean;
//    }
//    
//    
//	
//	/**
//	 * 调用远程接口
//	 * @param url 远程地址
//	 * @param body 请求体
//	 * @param retryTime 连接不上的时候，重新连接的次数
//	 * @return
//	 * @author admin
//	 * @date   2017年9月1日上午10:56:43
//	 */
//	public static String post(String url, String body, int retryTime) throws Exception{
//		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(url);
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
//		httpPost.setConfig(requestConfig);
//		StringEntity entity;
//		String res = "";
//		try {
//			entity = new StringEntity(body, "UTF-8");
//			httpPost.setHeader("Content-type", "application/json");
//			httpPost.setEntity(entity);
//			HttpResponse httpResponse;
//			// 发送post请求
//			httpResponse = closeableHttpClient.execute(httpPost);
//			StatusLine sl = httpResponse.getStatusLine();
//			if (sl == null || sl.getStatusCode() < 200 || sl.getStatusCode() > 300){
//				throw new Exception("请求返回状态码不正确！" + sl.getStatusCode()+"||message:"+EntityUtils.toString(new BufferedHttpEntity(httpResponse.getEntity()), "UTF-8"));
//			}
//			HttpEntity httpEntity = httpResponse.getEntity();
//			if (httpEntity != null) {
//				httpEntity = new BufferedHttpEntity(httpEntity);
//				res = EntityUtils.toString(httpEntity, "UTF-8");
//			}
//		} catch (Exception e) {
//			int retryLimt = retryTime - 1;
//			if (retryLimt <= 0) {
//				log.error("调用"+url+"，达到重复次数，仍未成功", e.fillInStackTrace());
//				throw new Exception("调用远程接口异常，达到重复次数，仍未成功！["+url+"]");
//			} else {
//				log.info("调用远程接口异常，未达到重复次数，正在重新调用  " + retryLimt,e.fillInStackTrace());
//				res = post(url, body, retryLimt);
//			}
//		} finally {
//			// 释放资源
//			closeableHttpClient.close();
//			closeableHttpClient.getConnectionManager().shutdown();
//		}
//		return res;
//	}
//	
//	/**
//	 * 向指定URL发送GET方法的请求
//	 * 
//	 * @param url
//	 *            发送请求的URL
//	 * @param param
//	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//	 * @return URL 所代表远程资源的响应结果
//	 */
//	public static String sendGet(String url,int retryTime) throws Exception {
//		String result = "";
//		BufferedReader in = null;
//		try {
//			URL realUrl = new URL(url);
//			// 打开和URL之间的连接
//			URLConnection connection = realUrl.openConnection();
//			// 设置通用的请求属性
//			connection.setRequestProperty("accept", "*/*");
//			connection.setRequestProperty("connection", "Keep-Alive");
//			// 建立实际的连接
//			connection.connect();
//			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
//			// 定义 BufferedReader输入流来读取URL的响应
//			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += line;
//			}
//		} catch (Exception e) {
//			int retryLimt = retryTime - 1;
//			if (retryLimt <= 0) {
//				log.error("调用"+url+"，达到重复次数，仍未成功", e.fillInStackTrace());
//				throw new Exception("调用远程接口异常，达到重复次数，仍未成功！["+url+"]");
//			} else {
//				result = sendGet(url,retryLimt);
//			}
//		}
//		// 使用finally块来关闭输入流
//		finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return result;
//	}
//	
//	 /**
//     * 发送delete请求
//     * 
//     * @param url
//     * @param token
//     * @param jsonStr
//     * @return
//	 * @throws Exception 
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String delete(String url) throws Exception {
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpDelete httpDelete = new HttpDelete(url);
//        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
//        httpDelete.setConfig(requestConfig);
//        httpDelete.setHeader("Content-type", "application/json");
//        httpDelete.setHeader("DataEncoding", "UTF-8");
//
//        CloseableHttpResponse httpResponse = null;
//        try {
//            httpResponse = httpClient.execute(httpDelete);
//            StatusLine sl = httpResponse.getStatusLine();
//			if (sl == null || sl.getStatusCode() < 200 || sl.getStatusCode() > 300){
//				throw new Exception("请求返回状态码不正确！" + sl.getStatusCode()+"||message:"+httpResponse.getEntity());
//			}
//            HttpEntity entity = httpResponse.getEntity();
//            String result = EntityUtils.toString(entity);
//            return result;
//        } catch (Exception e) {
//        	log.error("调用delete远程接口:"+url+"异常！");
//        	throw new Exception("调用delete远程接口:"+url+"异常！");
//        }
//         finally {
//            if (httpResponse != null) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    
//    /**
//     * 原生字符串发送put请求
//     * 
//     * @param url
//     * @param token
//     * @param jsonStr
//     * @return
//     * @throws Exception 
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String put(String url,String jsonStr) throws Exception {
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPut httpPut = new HttpPut(url);
//        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
//        httpPut.setConfig(requestConfig);
//        httpPut.setHeader("Content-type", "application/json");
//        httpPut.setHeader("DataEncoding", "UTF-8");
//        
//        CloseableHttpResponse httpResponse = null;
//        try {
//            httpPut.setEntity(new StringEntity(jsonStr));
//            httpResponse = httpClient.execute(httpPut);
//            HttpEntity entity = httpResponse.getEntity();
//            String result = EntityUtils.toString(entity);
//            return result;
//        } catch (Exception e) {
//        	log.error("调用put远程接口:"+url+"异常！");
//        	throw new Exception("调用put远程接口:"+url+"异常！");
//        } finally {
//            if (httpResponse != null) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
