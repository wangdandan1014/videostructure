package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.Capture;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.ICaptureService;
import com.sensing.core.dao.ICaptureDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author mingxingyu
 */
@Service
public class CaptureServiceImpl implements ICaptureService{


	private static final Log log = LogFactory.getLog(ICaptureService.class);

	@Resource
	public ICaptureDAO captureDAO;

	public CaptureServiceImpl() {
		super();
	}

	@Override
	public Capture saveNewCapture(Capture capture)  throws Exception{
		try {
			String id = UuidUtil.getUuid();
			capture.setUuid(id);
			captureDAO.saveCapture(capture);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return capture;
	}

	@Override
	public Capture updateCapture(Capture capture)  throws Exception{
		captureDAO.updateCapture(capture);
		return capture;
	}

	@Override
	public Capture findCaptureById(java.lang.String uuid) throws Exception{
		try {
			return captureDAO.getCapture(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeCapture(String uuid) throws Exception{
		try {
		captureDAO.removeCapture(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<Capture> list = captureDAO.queryList(pager);
			int totalCount = captureDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}