package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.Regions;
import com.sensing.core.bean.SysPara;
import com.sensing.core.utils.Pager;

/**
 * 
 * com.sensing.core.dao
 * 系统参数接口类
 * @author haowenfeng
 * @date 2017年12月11日 上午10:47:38
 */
public interface ISysParaDAO {
	
	/**
	 * 分页查询
	 * @param pager 分页对象
	 * @return
	 * @throws Exception
	 */
	public List<Regions> queryList(Pager pager) throws Exception;
	
	/**
	 * 查询系统参数数量
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public int selectCount(Pager pager) throws Exception;
	
	/**
	 * 根据主键查询一条系统信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public SysPara selectSysParaByKey(String key) throws Exception;
	
	/**
	 * 保存系统参数
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public int saveSysPara(SysPara sysPara) throws Exception;
	
	/**
	 * 修改系统参数信息
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public int updateSysPara(SysPara sysPara) throws Exception;
	
	/**
	 * 删除系统参数信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int removeSysPara(String key) throws Exception;

}
