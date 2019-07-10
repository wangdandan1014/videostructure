package com.sensing.core.utils.stateutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sensing.core.utils.StringUtils;

/**
 * 远程执行linux的shell script
 * 
 * @author liuwei
 */
@SuppressWarnings("all")
public class RemoteUtil {
	private static final Log log = LogFactory.getLog(RemoteUtil.class);
	// 字符编码默认是utf-8
	private static String DEFAULTCHART = "UTF-8";
	private static Connection conn;

	public static void setCharset(String charset) {
		DEFAULTCHART = charset;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 远程登录linux的主机
	 * 
	 * @author Ickes
	 * @since V0.1
	 * @return 登录成功返回true，否则返回false
	 */
	public static Boolean login(String ip, String userName, String userPwd) {
		boolean flg = false;
		try {
			conn = new Connection(ip);
			conn.connect();// 连接
			flg = conn.authenticateWithPassword(userName, userPwd);// 认证
			if (flg) {
				log.info("认证成功！");
			} else {
				log.error("登陆失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flg;
	}

	/**
	 * @author Ickes 远程执行shll脚本或者命令
	 * @param cmd
	 *            即将执行的命令
	 * @return 命令执行完后返回的结果值
	 * @since V0.1
	 */
	public static String execute(String cmd, String ip, String userName, String userPwd) {
		String result = "";
		try {
			if (login(ip, userName, userPwd)) {
				Session session = conn.openSession();// 打开一个会话
				session.execCommand(cmd);// 执行命令
				result = processStdout(session.getStdout(), DEFAULTCHART);
				// 如果为得到标准输出为空，说明脚本执行出错了
				if (StringUtils.isEmptyOrNull(result)) {
					result = processStdout(session.getStderr(), DEFAULTCHART);
				}
				conn.close();
				session.close();
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	/**
	 * @author Ickes 远程执行shll脚本或者命令
	 * @param cmd
	 *            即将执行的命令
	 * @return 命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
	 * @since V0.1
	 */
	public static String executeSuccess(String cmd, String ip, String userName, String userPwd) {
		String result = "";
		try {
			if (login(ip, userName, userPwd)) {
				Session session = conn.openSession();// 打开一个会话
				session.execCommand(cmd);// 执行命令
				result = processStdout(session.getStdout(), DEFAULTCHART);
				conn.close();
				session.close();
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 * 
	 * @author Ickes
	 * @param in
	 *            输入流对象
	 * @param charset
	 *            编码
	 * @since V0.1
	 * @return 以纯文本的格式返回
	 * @throws Exception 
	 */
	public static String processStdout(InputStream in, String charset) throws Exception {
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		
		
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\n");
			}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Title: probeRedis
	 * </p>
	 * <p>
	 * Description: redis集群状态探测
	 * </p>
	 * 
	 * @param ip
	 * @return
	 * @throws IOException
	 *             boolean
	 * @author liuwei
	 * @date 2018年6月13日
	 */
	public static boolean probeRedis(String ip) throws IOException {

//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		// 最大连接数
//		poolConfig.setMaxTotal(10);
//		// 最大空闲数
//		poolConfig.setMaxIdle(1);
//		// 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
//		// Could not get a resource from the pool
//		poolConfig.setMaxWaitMillis(1000);
		/*Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
		nodes.add(new HostAndPort(ip, 8051));
		nodes.add(new HostAndPort(ip, 8052));
		nodes.add(new HostAndPort(ip, 8053));
		nodes.add(new HostAndPort(ip, 8054));
		nodes.add(new HostAndPort(ip, 8055));
		nodes.add(new HostAndPort(ip, 8056));
		JedisCluster cluster = new JedisCluster(nodes, poolConfig);

		try {
			cluster.set("status", "success");
			cluster.expire("status", 300);
			if (cluster.get("status").equals("success")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			log.error(e);
		} finally {
			cluster.close();
		}
		*/
		
		/*JedisPool jedisPool = new JedisPool(poolConfig,RemoteInfoUtil.REDIS_SERVER_IP,RemoteInfoUtil.REDIS_SERVER_PORT);
		Jedis jedis = jedisPool.getResource();
		String set = jedis.set("probeRedis", "success");
		String getV = jedis.get("probeRedis");
		if ("success".equals(getV)) {
			jedis.del("probeRedis");
			if(jedis.isConnected()){
				jedis.close();
			}
			return true;
		} else {
			if(jedis.isConnected()){
				jedis.close();
			}
			return false;
		}*/
		return false;
		
	}

	/**
	 * 根据IP和端口号查询服务是否能ping通
	 * @param host
	 * @param port
	 * @param timeout
	 * @return
	 */
	public static Boolean checkServerState(String host,int port,int timeout){
		Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port),timeout);
        } catch (IOException e) {
            log.error("链接比对服务异常："+e);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("关闭与比对服务的链接异常!"+e);
            }
        }
        return true;
	}

}
