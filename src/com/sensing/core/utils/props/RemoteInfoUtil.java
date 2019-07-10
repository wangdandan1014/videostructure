package com.sensing.core.utils.props;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * 
 * <p>Title: RemoteInfoUtil</p>
 * <p>Description: 远程调用信息类 </p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	haowenfeng
 * @date	2017年8月10日下午5:41:48
 * @version 2.0
 */
public interface RemoteInfoUtil {

	//图片服务信息
	public static final String REMOTE_IMG_SERVER = PropUtils.getString("remote.img.url");
	
	//抓拍服务
	public static final String CAP_SERVER_IP = PropUtils.getString("cap.server.ip");
	public static final int CAP_SERVER_PORT = PropUtils.getInt("cap.server.port");
	public static final int CAP_TIMEOUT = PropUtils.getInt("cap.server.timeout");
	
	//public static final String MYSQL_SERVER_IP = PropUtils.getConfigByMap("db_ip");//PropUtils.getString("mysql.server.ip");
	//public static final String MYSQL_SERVER_DBNAME = PropUtils.getConfigByMap("db_schema");
	//public static final String MYSQL_SERVER_USERNAME = PropUtils.getConfigByMap("username");
	//public static final String MYSQL_SERVER_PASSWORD = PropUtils.getConfigByMap("password");
	//public static final Integer MYSQL_SERVER_PORT = PropUtils.getInt("db_port");
	
	public static TTransport getTTransport(String targIp, int port, int timeout) {
		TTransport transport = new TSocket(targIp, port, timeout);
		try {
			transport.open();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
		return transport;
	}
}
