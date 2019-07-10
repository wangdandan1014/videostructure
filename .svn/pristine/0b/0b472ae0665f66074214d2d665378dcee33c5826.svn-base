package com.sensing.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.dao.INonmotorVehicleDAO;
import com.sensing.core.service.INonmotorVehicleService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;

/**
 *@author wenbo
 */
@Service
public class NonmotorVehicleServiceImpl implements INonmotorVehicleService{


	private static final Log log = LogFactory.getLog(INonmotorVehicleService.class);

	@Resource
	public INonmotorVehicleDAO nonmotorVehicleDAO;

	public NonmotorVehicleServiceImpl() {
		super();
	}

	@Override
	public NonmotorVehicle saveNewNonmotorVehicle(NonmotorVehicle nonmotorVehicle)  throws Exception{
		try {
			String uuid = UuidUtil.getUuid();
			nonmotorVehicle.setUuid(uuid);
			nonmotorVehicleDAO.saveNonmotorVehicle(nonmotorVehicle);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return nonmotorVehicle;
	}

	@Override
	public NonmotorVehicle updateNonmotorVehicle(NonmotorVehicle nonmotorVehicle)  throws Exception{
		nonmotorVehicleDAO.updateNonmotorVehicle(nonmotorVehicle);
		return nonmotorVehicle;
	}

	@Override
	public NonmotorVehicle findNonmotorVehicleById(java.lang.String uuid) throws Exception{
		try {
			return nonmotorVehicleDAO.getNonmotorVehicle(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeNonmotorVehicle(String uuid) throws Exception{
		try {
		nonmotorVehicleDAO.removeNonmotorVehicle(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<NonmotorVehicle> list = nonmotorVehicleDAO.queryList(pager);
			int totalCount = nonmotorVehicleDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}