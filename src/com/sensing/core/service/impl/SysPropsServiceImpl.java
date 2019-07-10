package com.sensing.core.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

import com.sensing.core.bean.SysProps;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Exception.ExpUtil;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.StringTool;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.service.ISysPropsService;
import com.sensing.core.dao.ISysPropsDAO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author wenbo
 */
@Service
public class SysPropsServiceImpl implements ISysPropsService{


	private static final Log log = LogFactory.getLog(ISysPropsService.class);

	@Resource
	public ISysPropsDAO sysPropsDAO;

	public SysPropsServiceImpl() {
		super();
	}

	@Override
	public SysProps saveNewSysProps(SysProps sysProps)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			sysProps.setUuid(id);
			sysPropsDAO.saveSysProps(sysProps);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysProps;
	}

	@Override
	public SysProps updateSysProps(SysProps sysProps)  throws Exception{
		sysPropsDAO.updateSysProps(sysProps);
		return sysProps;
	}

	@Override
	public SysProps findSysPropsById(java.lang.String uuid) throws Exception{
		try {
			return sysPropsDAO.getSysProps(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeSysProps(String uuid) throws Exception{
		try {
		sysPropsDAO.removeSysProps(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<SysProps> list = sysPropsDAO.queryList(pager);
			int totalCount = sysPropsDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	/**
	 * @Description: 查询所有服务模块     
	 * @author dongsl
	 * @Date 2018年6月14日下午3:33:01
	 */
	@Override
	public List<String> queryModuleList() throws Exception {
		List<String> resList = new ArrayList<String>();
		try {
			resList = sysPropsDAO.queryModuleList();
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return resList;
	}
	
	
	/**
	 * 获取配置文件KV
	 * @param ipAddr
	 * @param userName
	 * @param password
	 * @param path
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getRemoteProps(String ipAddr,String userName,String password,String path,String file) throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		//第一步：创建连接对象
		Connection conn = null;
		try {
			//第二步：验证网络连通状态
			boolean status = InetAddress.getByName(ipAddr).isReachable(1500);
			if (status) {
				//第三步：建立连接
				conn = new Connection(ipAddr);
				conn.connect();
				//第四步：验证帐号密码
				boolean isAuthed = conn.authenticateWithPassword(userName, password);
				if (isAuthed) {
					//第五步：从远程服务器上面获取文件并保存到本地
					Session session = conn.openSession();
					SCPClient scpClient = conn.createSCPClient();
					File localTemp = new File(PropUtils.getString("localTempDir"));
				  	if(!localTemp.exists()) localTemp.mkdirs();
					scpClient.get(path + file,PropUtils.getString("localTempDir"));
					session.close();//关闭连接
					//读取配置文件
					result = this.getKVFromLocalPropFile(PropUtils.getString("localTempDir") + file);
				} else {
					log.error("连接" + ipAddr + "时登录失败");
				}
			}else{
				log.error("连接" + ipAddr + "失败");
			}
		}catch (Exception e) {
			log.error("执行ISysPropsService.getRemoteProps(String ipAddr,String userName,String password,String path,String file)方法异常！信息：" + ExpUtil.getExceptionDetail(e));
			throw e;
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * 读取本地配置文件中的数据
	 * @param localPath
	 * @throws Exception
	 */
	public Map<String,String> getKVFromLocalPropFile(String localPath) throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		String temp = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			//读取从服务器上get的临时文件
			File file = new File(localPath);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			//遍历每一行，写入StringBuffer
			for (int j = 1; (temp = br.readLine()) != null ; j++) {
				//修改配置文件，标准格式  
				if(temp!=null && !temp.trim().equals("") && !temp.startsWith("#") && !temp.startsWith(";") && temp.indexOf("=")>0){
					temp = temp.trim();
					String[] kv = temp.split("=");
					result.put(kv[0].trim(), kv[1].trim());
				}
			}
		} catch (IOException e) {
			log.error(e);
			throw e;
		}finally{
			if(fis!=null) fis.close();
			if(isr!=null) isr.close();
			if(br!=null) br.close();
		}
		return result;
	}

	/**
	 * @Description: 查询某服务模块下所有的文件 
	 * @param module
	 * @return
	 * @throws Exception       
	 * @author dongsl
	 * @Date 2018年6月14日下午6:01:45
	 */
	@Override
	public List<SysProps> queryModuleFilesList(String module) throws Exception {
		List<SysProps> resList = new ArrayList<SysProps>();
		try {
			resList = sysPropsDAO.queryModuleFilesList(module);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return resList;
	}
	
	
	/**
	 * 修改远程linux服务器的配置文件
	 * @param ipAddr
	 * @param userName
	 * @param password
	 * @param path
	 * @param file
	 * @param kv
	 * @return
	 * @throws Exception
	 */
	public String modifyProps(String module,String ipAddr,String userName,String password,String path,String file,Map kv) throws Exception{
		String res = "";
		//第一步：创建连接对象
		Connection conn = null;
		try {
			//第二步：验证网络连通状态
			boolean status = InetAddress.getByName(ipAddr).isReachable(1500);
			if (status) {
				//第三步：建立连接
				conn = new Connection(ipAddr);
				conn.connect();
				//第四步：验证帐号密码
				boolean isAuthed = conn.authenticateWithPassword(userName, password);
				if (isAuthed) {
					//第五步：从远程服务器上面获取文件并保存到本地
					Session session = conn.openSession();
					SCPClient scpClient = conn.createSCPClient();
					scpClient.get(path + file,PropUtils.getString("localTempDir"));
					session.close();//关闭连接
					
					//第六步：通过一系列文件操作修改配置文件
					//备份修改前的配置文件
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
					String dateStr = sdf.format(date);
					this.copyFile(new File(PropUtils.getString("localTempDir") + file), new File(PropUtils.getString("localTempDir") + ipAddr + "_" + dateStr + "_" + file));
					this.updateParam(PropUtils.getString("localTempDir") + file, kv);
					
					//第七步：删掉旧版本文件
					session = conn.openSession();
					String cmd = "cd "+path+" &&rm -rf "+file;
					session.execCommand(cmd);
					session.close();
		
					//第八步：将修改后的本地文件放入远程服务器原路径中
					scpClient.put(PropUtils.getString("localTempDir") + file, path);
					
					//查询数据库，判断修改字段是否需要重启服务
					boolean reload = false;
					for (Object o : kv.keySet()) {  
						   SysProps prop = sysPropsDAO.queryPropByModuleAndFileNameAndKey(module,file,o.toString());
						   if(prop.getNeedsReboot() == 1){
							   reload = true;
						   }
					 }
					 //调用远程接口重新加载配置文件
					 String url = "";
					 if("remoteutil.properties".equals(file)){
						 if(module.contains("facecore")){
							 url = "http://" + ipAddr + ":8000/facecenter/" + "props/reloadPorperties";
						 }else if(module.contains("facejob")){
							 url = "http://" + ipAddr + ":8001/facecenter/" + "props/reloadPorperties";
						 }
					 }else if("log4j.properties".equals(file)){
						 if(module.contains("facecore")){
							 url = "http://" + ipAddr + ":8000/facecenter/" + "props/reloadLog4jConfig";
						 }else if(module.contains("facejob")){
							 url = "http://" + ipAddr + ":8001/facecenter/" + "props/reloadLog4jConfig";
						 }
					 }
					 if(StringUtils.isNotEmpty(url)){
						 HttpDeal.post(url,"",1);
					 }
					res = "success";
				} else {
					res = "login failed";
				}
			}else{
				res = "connet failed";
			}
		}catch (Exception e) {
			log.error("执行ISysPropsService.modifyProps(String module,String ipAddr,String userName,String password,String path,String file,Map kv)方法异常！信息：" + ExpUtil.getExceptionDetail(e));
			throw e;
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		return res;
	}
	
	
	/**
	 * 修改本地配置文件
	 * @param localPath 本地目录的配置文件
	 * @param kv	配置项KV对,如xxx=xxxxxx
	 * @throws Exception
	 */
	public void updateParam(String localPath,Map<String,String> kv) throws Exception{
		String temp = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			//读取从服务器上get的临时文件
			File file = new File(localPath);
			String code = "utf-8";
			if(localPath.contains("config.ini")){
				code = "GBK";
			}
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,code);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			//遍历每一行，写入StringBuffer
			for (int j = 1; (temp = br.readLine()) != null ; j++) {
				//修改配置文件，标准格式
				temp = temp.trim();
				String k = temp.split("=")[0].toString().trim();
				if(kv.get(k)!=null){
					String temp1 = temp.replaceAll(temp.split("=")[1].toString().trim(),kv.get(k));
					buf = buf.append(temp1);
				//修改配置文件，标准格式
				}else{
					buf = buf.append(temp);
				}
				buf = buf.append(System.getProperty("line.separator"));
			}
			br.close();
			//输出更新后的文件
			fos = new FileOutputStream(file);
			pw = new PrintWriter(new OutputStreamWriter(fos,code));  
			pw.write(buf.toString().toCharArray());
			pw.flush();
		} catch (IOException e) {
			log.error("执行ISysPropsService.updateParam(String localPath,Map<String,String> kv)方法异常！信息：" + ExpUtil.getExceptionDetail(e));
			throw e;
		}finally{
			if(fis!=null) fis.close();
			if(isr!=null) isr.close();
			if(br!=null) br.close();
			if(fos!=null) fos.close();
			if(pw!=null) pw.close();
		}
	}
	
	
	/**
	 * 拷贝文件,用于修改前备份
	 * @param source
	 * @param copy
	 * @throws IOException
	 */
	private void copyFile(File source, File copy){
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(copy);
			byte[] buf = new byte[1024];
			int bytesRead;
	        while((bytesRead = input.read(buf)) != -1){
	        	output.write(new String(buf, 0, bytesRead, "utf-8").getBytes());
	        }
			if(input!=null) input.close();
			if(output!=null) output.close();
		}catch(Exception e){
			log.error("执行ISysPropsService.copyFile(File source, File copy)方法异常！" + ExpUtil.getExceptionDetail(e));
		}
	}
	
	/**
	 * @Description: 获取指定ip服务器的文件存放到本地  
	 * @author dongsl
	 * @Date 2018年6月20日上午11:22:47
	 */
	public String getFile(String ipAddr,String userName,String password,String path,String file){
		String res = "";
		//第一步：创建连接对象
		Connection conn = null;
		try {
			//第二步：验证网络连通状态
			boolean status = InetAddress.getByName(ipAddr).isReachable(1500);
			if (status) {
				//第三步：建立连接
				conn = new Connection(ipAddr);
				conn.connect();
				//第四步：验证帐号密码
				boolean isAuthed = conn.authenticateWithPassword(userName, password);
				if (isAuthed) {
					//第五步：从远程服务器上面获取文件并保存到本地
					Session session = conn.openSession();
					SCPClient scpClient = conn.createSCPClient();
					scpClient.get(path + file,PropUtils.getString("localTempDir"));
					session.close();//关闭连接
					res = PropUtils.getString("localTempDir") + file;
					return "success";
				} else {
					return "login failed";
				}
			}else{
				return "connet failed";
			}
		}catch (Exception e) {
			log.error(e);
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		return res;
	}

	/**
	 * @Description: 获取某服务器某服务模块的配置信息 
	 * @param module	服务模块名称
	 * @param ip	服务器ip    
	 * @author dongsl
	 * @Date 2018年6月20日上午11:31:28
	 */
	@Override
	public Map<String, Map> getPropsFromModule(String module,String ip) throws Exception {
		Map<String,Map> paramMap = new HashMap<String,Map>();
		try{
			if(StringTool.isNotEmpty(module) && StringTool.isNotEmpty(ip)){
				List<SysProps> fileList = queryModuleFilesList(module);//查询该服务模块所有的配置文件名称
				//遍历每一个配置文件，并取出值放入map中
				for(SysProps prop : fileList){
					//获取某ip节点下的配置文件信息
					Map map = getRemoteProps(ip,PropUtils.getString("server.username"),PropUtils.getString("server.password"),prop.getPath(),prop.getFileName());
					paramMap.put(prop.getFileName(),map);
				}
			}else{
				log.error("服务模块名称为空或ip为空！module:" + module + ";ip:" + ip);
				throw new BussinessException("服务模块名称为空或ip为空！");
			}
		}catch(Exception e){
			log.error("执行ISysPropsService.getPropsFromModule()方法异常！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
		return paramMap;
	}

	/**
	 * @Description: 更改服务器配置文件信息 
	 * @param fileMapp
	 * @param mapList  
	 * @author dongsl
	 * @Date 2018年6月20日上午11:44:41
	 */
	@Override
	public String setConfig(Map fileMapp, List<Map> mapList) throws Exception {
		String res = "";
		try{
			List<Map> fileMappp = (List<Map>) fileMapp.get("elements");
			Map<String,Map> fileMap = (Map) fileMappp.get(0).get("value");
			List<String> ipList = (List<String>) mapList.get(0).get("value");
			String module = (String) mapList.get(2).get("value");
			for(String ip : ipList){
				for(Map m : fileMappp){
					 String str = (String) m.get("key");
					 String path = str.split(",")[0];
					 String fileName = str.split(",")[1];
					 Map resMap = (Map) m.get("value");
					 List<Map> paraList = (List<Map>) resMap.get("elements");
					 Map<String,String> paraMap = new HashMap<String,String>();
					 for(Map ma : paraList){
						 String a = ma.get("key").toString();
						 String b = ma.get("value").toString();
						 paraMap.put(a,b);
					 }
					res = modifyProps(module,ip,PropUtils.getString("server.username"),PropUtils.getString("server.password"),path,fileName,paraMap);
				}
			}
		}catch(Exception e){
			log.error("执行ISysPropsService.setConfig()方法异常！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
		return res;
	}

}