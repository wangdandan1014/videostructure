package com.sensing.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.Regions;
import com.sensing.core.bean.SysPara;
import com.sensing.core.dao.ISysParaDAO;
import com.sensing.core.service.ISysParaService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;

/**
 * 
 * com.sensing.core.service.impl
 * 系统参数信息业务类
 * @author haowenfeng
 * @date 2017年12月11日 下午2:32:21
 */
@Service
@SuppressWarnings("all")
public class SysParaServiceImpl implements ISysParaService {

	
	
	private static final Log log = LogFactory.getLog(SysParaServiceImpl.class);
	
	@Autowired
	private ISysParaDAO sysParaDAO;

	
	public SysParaServiceImpl() {
		super();
	}

	/**
	 * 分页查询
	 * @param pager 分页对象
	 * @return
	 * @throws Exception
	 */
	public Pager queryPage(Pager pager) throws Exception {
		List<Regions> queryList = sysParaDAO.queryList(pager);
		int totalCount = sysParaDAO.selectCount(pager);
		pager.setTotalCount(totalCount);
		pager.setResultList(queryList);
		return pager;
	}

	/**
	 * 查询系统参数数量
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public int selectCount(Pager pager) throws Exception {
		return sysParaDAO.selectCount(pager);
	}

	/**
	 * 根据主键查询一条系统信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public SysPara querySysParaByKey(String key) throws Exception {
		return sysParaDAO.selectSysParaByKey(key);
	}

	/**
	 * 保存系统参数
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public SysPara saveSysPara(SysPara sysPara) throws Exception {
		try{
			int result = sysParaDAO.saveSysPara(sysPara);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysPara;
	}

	/**
	 * 修改系统参数信息
	 * @param sysPara
	 * @return
	 * @throws Exception
	 */
	public SysPara updateSysPara(SysPara sysPara) throws Exception {
		try{
			int result = sysParaDAO.updateSysPara(sysPara);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysPara;
	}

	/**
	 * 删除系统参数信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int removeSysPara(String key) throws Exception {
		try{
			return sysParaDAO.removeSysPara(key);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	public Boolean openOrClose(List<SysPara> list) {
		Boolean flag=true;
		if(list!=null&&list.size()>0){
			for (int i=0;i<list.size();i++) {
				SysPara  sysPara = list.get(i);
				try {
					sysParaDAO.updateSysPara(sysPara);
				} catch (Exception e) {
					flag=false;
					log.error(e);
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

}
