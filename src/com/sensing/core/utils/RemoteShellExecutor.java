package com.sensing.core.utils;
 
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
 
/**
 * 调用远程连接执行命令，获取命令的运行结果
 */
public class RemoteShellExecutor {
    private Connection conn;
    private String ipAddr;
    private Integer port;
    private String charset = Charset.defaultCharset().toString();
    private String userName;
    private String password;
 
    public RemoteShellExecutor(String ipAddr, Integer port, String userName, String password, String charset) {
        this.ipAddr = ipAddr;
        this.port = port;
        this.charset = charset;
        this.userName = userName;
        this.password = password;
    }
 
    /**
     * 登录远程服务器
     *
     * @return
     * @throws IOException
     */
    public boolean login() throws IOException {
        conn = new Connection(ipAddr, port);
        conn.connect();
        return conn.authenticateWithPassword(userName, password);
    }
 
    /**
     * 获取控制台输出信息
     *
     * @param inputStream
     * @param charset
     * @return
     */
    public String processStdout(InputStream inputStream, String charset) {
        InputStream in = new StreamGobbler(inputStream);
        BufferedReader bufferedReader = null;
        StringBuffer sb = new StringBuffer();
        String buf = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(in, charset));
            while( (buf=bufferedReader.readLine()) != null ){
            	sb.append(buf+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if ( bufferedReader != null  ) {
					bufferedReader.close();
				}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
 
    /**
     * 主方法，调用执行命令
     *
     * @param cmds
     * @return
     */
    public String exec(String cmds) {
        InputStream inputStream = null;
        String result = null;
        Session session = null;
        try {
            if (this.login()) {
                session = conn.openSession();
                session.execCommand(cmds);
                inputStream = session.getStdout();
                //获取控制台标准输出
                result = this.processStdout(inputStream, this.charset);
                //如果输出为空，则说明执行失败，获取失败信息
                if ( result == null ) {
                    result = processStdout(session.getStderr(), this.charset);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}