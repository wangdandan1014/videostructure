package com.sensing.core.dao;

import java.util.List;
import com.sensing.core.bean.RpcLog;
import com.sensing.core.bean.RpcLogLogin;
import com.sensing.core.bean.RpcLogRun;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IRpcLogDAO {
	public int saveRpcLog(RpcLog rpcLog);

	public int removeRpcLog(java.lang.Integer id) throws Exception;

	public List<RpcLog> queryList(Pager pager) throws Exception;

	public Integer selectCount(Pager pager) throws Exception;

	public List<RpcLog> queryRpcLog(RpcLog rpcLog);

	public List<RpcLogRun> queryRpcRunLog(RpcLog rpcLog);

	public List<RpcLogLogin> queryRpcLoginLog(RpcLog rpcLog);

	public List<RpcLog> getModle();

}
