package com.sensing.core.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.service.IMotorVehicleService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.results.ResultUtils;

@Controller
@RequestMapping("/motorVehicle")
public class MotorVehicleController extends BaseController {

	private static final Log log = LogFactory.getLog(MotorVehicleController.class);

	@Resource
	public IMotorVehicleService MotorVehicleService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		try {
			pager = MotorVehicleService.queryPage(pager);
			result.getMap().put("pager", pager);
			result.setError(0);
			result.setMessage("successful");
		} catch (Exception e) {
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/detail")
	public ResponseBean detail(@RequestBody JSONObject p) throws Exception {
		log.info("开始查询机动车详情，调用 MotorVehicle/detail 接口，传递参数为: " + p);
		String uuid = p.getString("uuid");
		Integer capType = Integer.parseInt(p.getString("capType"));
		ResponseBean result = new ResponseBean();
		if (StringUtils.isNotEmpty(uuid)) {
			Map<String, Object> map = MotorVehicleService.queryCapByUuid(uuid, capType);
			if (map == null) {
				result = ResultUtils.NONE();
			} else {
				result = ResultUtils.success(map);
			}
		} else {
			result = ResultUtils.REQUIRED_EMPTY_ERROR();
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResponseBean update(@RequestBody JSONObject m) {
		MotorVehicle model = JSONObject.toJavaObject(m, MotorVehicle.class);
		ResponseBean result = new ResponseBean();
		try {
			if (model != null && model.getUuid() != null && !model.getUuid().equals("")) {
				model = MotorVehicleService.updateMotorVehicle(model);
				result.getMap().put("model", model);
				result.setError(0);
				result.setMessage("successful");
			} else {
				result.setError(100);
				result.setMessage("business error");
			}
		} catch (Exception e) {
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * save data
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResponseBean save(@RequestBody JSONObject m) {
		MotorVehicle model = JSONObject.toJavaObject(m, MotorVehicle.class);
		ResponseBean result = new ResponseBean();
		try {
			model = MotorVehicleService.saveNewMotorVehicle(model);
			result.getMap().put("model", model);
			result.setError(0);
			result.setMessage("successful");
		} catch (Exception e) {
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody String[] idarr) {
		ResponseBean result = new ResponseBean();
		try {
			for (int i = 0; idarr != null && i < idarr.length; i++) {
				MotorVehicleService.removeMotorVehicle(idarr[i]);
			}
			result.setError(0);
			result.setMessage("successful");
		} catch (Exception e) {
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
