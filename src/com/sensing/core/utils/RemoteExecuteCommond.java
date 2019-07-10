package com.sensing.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteExecuteCommond {
    //字符编码
    private static String DEFAULTCHART ="utf-8";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;
    
    private static Log log = LogFactory.getLog(RemoteExecuteCommond.class);

    public RemoteExecuteCommond(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    /**
     * 远程登录Linux主机
     * @return
     * */
    public boolean login(){
        boolean flag =false;
        conn = new Connection(ip);
        try {
            conn.connect();
            flag=conn.authenticateWithPassword(userName,userPwd);
            if(flag){
               log.info("认证成功");
            }
        }catch (IOException e){
            e.printStackTrace();
            log.error(e);
        }
        return flag;
    }

    /**
     * 远程执行shell脚本或命令
     * */
    public String execute(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session  = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("") || result==null){
                    result = processStdout(session.getStderr(),DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }
        return result;
    }

    public String executeSuccess(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }
        return result;
    }
    /**
     * 解析脚本执行返回的结果集
     * */
    public static String processStdout(InputStream is,String charset)
    {
        InputStream inputStream = new StreamGobbler(is);
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(inputStream,charset));
            String line =null;
            while ((line = br.readLine()) !=null){
                sb.append(line +"\n");
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
        	try {
        		if ( inputStream != null ) {
        			inputStream.close();
        		}
        		if ( br != null ) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				log.error(e2);
			}
        }
        return sb.toString();
    }
    
    public static String runShell(String ip,String username,String pwd,String command){
        RemoteExecuteCommond rec = new RemoteExecuteCommond(ip,username,pwd);
        try {
            if (rec.login()) {
                Session session = conn.openSession();
                session.execCommand(command);
                String result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("")|| result==null){
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }
                session.close();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }
}