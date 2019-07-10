package com.sensing.core.service;

import com.sensing.core.bean.SysCarbrand;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ISysCarbrandService {

	public abstract SysCarbrand saveNewSysCarbrand(SysCarbrand sysCarbrand) throws Exception;

	public SysCarbrand updateSysCarbrand(SysCarbrand sysCarbrand) throws Exception;

	public abstract SysCarbrand findSysCarbrandById(java.lang.String uuid) throws Exception;

	public abstract void removeSysCarbrand(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

	public SysCarbrand findSysCarbrand(int itemId, String typeCode) throws Exception;
}