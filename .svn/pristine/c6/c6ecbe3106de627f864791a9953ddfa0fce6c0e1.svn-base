package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.RpcLog;
import com.sensing.core.bean.RpcLogLogin;
import com.sensing.core.bean.RpcLogRun;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

/**
 *@author wenbo
 */
public interface IRpcLogService {

	public RpcLog saveNewRpcLog(RpcLog rpcLog);

	public void removeRpcLog(java.lang.Integer id) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public List<RpcLog> queryRpcLog(RpcLog rpcLog) throws Exception;

	public List<RpcLogLogin> queryRpcLoginLog(RpcLog rpcLog);

	public List<RpcLogRun> queryRpcRunLog(RpcLog rpcLog);

	public List<RpcLog> getModle();

	public ResponseBean getOnlineUserCount();

}