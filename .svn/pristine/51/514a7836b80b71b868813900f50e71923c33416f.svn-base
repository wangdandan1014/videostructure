package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysTypecode;
import com.sensing.core.utils.Pager;

/**
 * 
 * <p>
 * Title: ISysTypecodeDAO
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 * 
 * @author haowenfeng
 * @date 2017年8月16日下午3:46:36
 * @version 2.0
 */
public interface ISysTypecodeDAO {

	public abstract int saveSysTypecode(SysTypecode paramSysTypecode)
			throws Exception;

	public abstract SysTypecode getSysTypecode(String paramString)
			throws Exception;

	public abstract int removeSysTypecode(String paramString) throws Exception;

	public abstract int updateSysTypecode(SysTypecode paramSysTypecode)
			throws Exception;

	public abstract List<SysTypecode> queryList(Pager paramPager)
			throws Exception;

	public abstract int selectCount(Pager paramPager) throws Exception;
	
	/**
	 * 根据type_code 和 item_id 查询一条记录 
	 * @param typeCode
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public abstract List<SysTypecode> selectSysTypeCodeByTypeCodeAndItemId(@Param("type_code")String typeCode,@Param("item_id")String itemId) throws Exception;
}
