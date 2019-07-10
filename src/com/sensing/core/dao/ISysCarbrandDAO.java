package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysCarbrand;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ISysCarbrandDAO {
	public int saveSysCarbrand(SysCarbrand sysCarbrand) throws Exception;

	public SysCarbrand getSysCarbrand(java.lang.String uuid) throws Exception;

	public int removeSysCarbrand(java.lang.String uuid) throws Exception;

	public int updateSysCarbrand(SysCarbrand sysCarbrand) throws Exception;

	public List<SysCarbrand> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public SysCarbrand findSysCarbrand(@Param("itemId") int itemId, @Param("typeCode") String typeCode)
			throws Exception;

}
