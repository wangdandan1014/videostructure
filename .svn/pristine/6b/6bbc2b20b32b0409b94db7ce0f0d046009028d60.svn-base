package com.sensing.core.alarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sensing.core.dao.ICmpDAO;

/**
 * 
 * <p>Title: CmpProcess</p>
 * <p>Description:比对的流程</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年9月14日
 * @version 1.0
 */
public class CmpProcess implements Runnable{
	
	public ICmpDAO cmpDAO;

	private CapBean capBean;
	
	//日志记录
	private Log log = LogFactory.getLog(CmpProcess.class);
	
	
	public CmpProcess(){}
	public CmpProcess(CapBean capBean,ICmpDAO cmpDAO){
		this.capBean = capBean;
		this.cmpDAO = cmpDAO;
	}

	//主流程
	public void run() {
		try {
			//库检索
			List<CmpResultBean> cmpResultList = capToCmp(capBean);
			CmpResultBean crb = null;
			if ( cmpResultList != null && cmpResultList.size() > 0  ) {
				for (int i = 0; i < cmpResultList.size() ; i++) {
					crb = cmpResultList.get(i);

					BeanUtils.copyProperties(capBean,crb);
					
					AlarmTask.cmpResultQuene.put(crb);
					CountInfo.cmpCount.getAndIncrement();
				}
			}
		} catch (Exception e) {
			log.error("后台告警流程异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据库进行检索抓拍的数据属性
	 * @param capBean
	 * @return 满足要求的比对结果
	 * @author mingxingyu
	 * @date   2018年12月3日 上午9:40:42
	 */
	public List<CmpResultBean> capToCmp(CapBean capBean){
		try {
//			if ( AlarmCache.templateDbList == null || AlarmCache.templateDbList.size() == 0 ) {
//				return null;
//			}
			Map<String,Object> cmpParams = new HashMap<String,Object>();
			
			cmpParams.put("plateNo",capBean.getCapPlateNo());
			cmpParams.put("plateColor",capBean.getPlateColor()==null?null:capBean.getPlateColor());
			cmpParams.put("vehicleBrandTag",capBean.getVehicleBrandTag());
			cmpParams.put("vehicleModelTag",capBean.getVehicleModelTag());
			cmpParams.put("vehicleStylesTag",capBean.getVehicleStylesTag());
			cmpParams.put("vehicleColor",capBean.getVehicleColor()==null?null:capBean.getVehicleColor());
			cmpParams.put("vehicleClass ",capBean.getVehicleClass()==null?null:capBean.getVehicleClass());
			
			List<CmpResultBean> cmpResultList = cmpDAO.cmpQuery(cmpParams);
			return cmpResultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
}
