package com.sensing.core.dao;

import java.util.List;
import com.sensing.core.bean.CmpLogs;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ICmpLogsDAO {
	public int saveCmpLogs(CmpLogs cmpLogs) throws Exception;

	public CmpLogs getCmpLogs(java.lang.String uuid) throws Exception;

	public int removeCmpLogs(java.lang.String uuid) throws Exception;

	public int updateCmpLogs(CmpLogs cmpLogs) throws Exception;

	public List<CmpLogs> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

}
