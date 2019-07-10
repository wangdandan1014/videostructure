package com.sensing.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensing.core.bean.SysParam;
import com.sensing.core.dao.ISysParamDAO;
import com.sensing.core.service.ISysParamService;
@Service
public class SysParamServiceImpl implements ISysParamService {
	@Resource
	ISysParamDAO sysParamDao;
	@Override
	public List<SysParam> queryTimeSyn(String typeCode)throws Exception {
		// TODO Auto-generated method stub
		return sysParamDao.queryTimeSyn(typeCode);
	}

	@Override
	public void updateParam(List<SysParam> param)throws Exception {
		sysParamDao.updateParam(param);

	}

}
