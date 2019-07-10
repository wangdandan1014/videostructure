package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.sensing.core.bean.SysCarbrand;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.ISysCarbrandService;
import com.sensing.core.dao.ISysCarbrandDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mingxingyu
 */
@Service
public class SysCarbrandServiceImpl implements ISysCarbrandService {

	private static final Log log = LogFactory.getLog(ISysCarbrandService.class);

	@Resource
	public ISysCarbrandDAO sysCarbrandDAO;

	public SysCarbrandServiceImpl() {
		super();
	}

	@Override
	public SysCarbrand saveNewSysCarbrand(SysCarbrand sysCarbrand) throws Exception {
		try {
			String id = UuidUtil.getUuid();
			sysCarbrand.setUuid(id);
			sysCarbrandDAO.saveSysCarbrand(sysCarbrand);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return sysCarbrand;
	}

	@Override
	public SysCarbrand updateSysCarbrand(SysCarbrand sysCarbrand) throws Exception {
		sysCarbrandDAO.updateSysCarbrand(sysCarbrand);
		return sysCarbrand;
	}

	@Override
	public SysCarbrand findSysCarbrandById(java.lang.String uuid) throws Exception {
		try {
			return sysCarbrandDAO.getSysCarbrand(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeSysCarbrand(String uuid) throws Exception {
		try {
			sysCarbrandDAO.removeSysCarbrand(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception {
		try {
			List<SysCarbrand> list = sysCarbrandDAO.queryList(pager);
			int totalCount = sysCarbrandDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	@Override
	public SysCarbrand findSysCarbrand(int itemId, String typeCode) throws Exception {
		SysCarbrand sysCarbrand = sysCarbrandDAO.findSysCarbrand(itemId, typeCode);
		return sysCarbrand;
	}

}