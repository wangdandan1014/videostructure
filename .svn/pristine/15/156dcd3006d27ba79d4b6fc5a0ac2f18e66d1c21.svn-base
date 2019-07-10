package com.sensing.core.multithread;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.utils.Pager;

/**
 * 
 * com.sensing.core.multithread
 * MySQL数据库检查
 * @author haowenfeng
 * @date 2018年6月15日 下午3:28:35
 */
public class MySqlCheckFuture implements Callable<String>,Serializable{

	
	
	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年6月15日 下午3:30:11
	 */
	private static final long serialVersionUID = 8422108314112092034L;
	
	
	private static final Log log = LogFactory.getLog(MySqlCheckFuture.class);
	
	private ISysUserDAO sysUserDAO;
	
	public MySqlCheckFuture(ISysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}



	/**
	 * 具体的业务实现方法
	 * MySQL数据库检查:检查数据库功能是否正常
	 */
	public String call() throws Exception {
		log.info("MySqlCheckFuture.call()开始调用");
		//检测数据库是否正常
		int selectCount = 0;
		try {
			selectCount = sysUserDAO.selectCount(new Pager());
		} catch (Exception e) {
			return "NO";
		}
		return ""+selectCount;
	}



	public ISysUserDAO getSysUserDAO() {
		return sysUserDAO;
	}



	public void setSysUserDAO(ISysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}

	
}
