package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.SysPara;
import com.sensing.core.utils.Pager;


/**
 * 
 * com.sensing.core.service
 * 系统参数信息业务接口类
 * @author haowenfeng
 * @date 2017年12月11日 下午2:31:17
 */
public interface ISysParaService {
	
	/**
	 * 分页查询
	 * @param pager 分页对象
	 * @return
	 * @throws Exception
	 */
	public Pager queryPage(Pager pager) throws Exception;
	
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
	public SysPara querySysParaByKey(String key) throws Exception;
	
	/**
	 * 保存系统参数
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public SysPara saveSysPara(SysPara sysPara) throws Exception;
	
	/**
	 * 修改系统参数信息
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public SysPara updateSysPara(SysPara sysPara) throws Exception;
	
	/**
	 * 删除系统参数信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int removeSysPara(String key) throws Exception;

	/**
	 * 开启或者关闭无人值守
	 * @param list
	 * @return
	 */
	public Boolean openOrClose(List<SysPara> list) throws Exception ;

}
