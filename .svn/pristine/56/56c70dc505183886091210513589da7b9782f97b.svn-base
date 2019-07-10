package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.SysTypecode;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface ISysTypecodeService {

	public abstract SysTypecode saveNewSysTypecode(SysTypecode sysTypecode)  throws Exception;

	public SysTypecode updateSysTypecode(SysTypecode sysTypecode)  throws Exception;

	public abstract SysTypecode findSysTypecodeById(java.lang.String uuid) throws Exception;

	public abstract void removeSysTypecode(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	/**
	 * 根据type_code 和 item_id 查询一条记录 
	 * @param typeCode
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public abstract List<SysTypecode> querySysTypeCodeByTypeCodeAndItemId(String typeCode,String itemId) throws Exception;

	/**
	 * 批量查询属性列表
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2018年8月14日 下午2:29:41
	 */
	public abstract Pager queryBatchPage(Pager pager) throws Exception;
	
}