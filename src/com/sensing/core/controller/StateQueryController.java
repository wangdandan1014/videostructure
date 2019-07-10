package com.sensing.core.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.ResponseBean;


@Controller
@RequestMapping("/tags")
@SuppressWarnings("all")
public class StateQueryController extends BaseController {

	private static final Log log = LogFactory.getLog(StateQueryController.class);

	
	@ResponseBody
	@RequestMapping("/queryCapState")
	public ResponseBean queryCapState(@RequestBody JSONObject p) {
		ResponseBean result = new ResponseBean();
		result.getMap().put("state", true);
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/queryComState")
	public ResponseBean queryComState(@RequestBody JSONObject p) {
		ResponseBean result = new ResponseBean();
		result.getMap().put("state", true);
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/queryMethodeState")
	public ResponseBean queryMethodeState(@RequestBody JSONObject p) {
		ResponseBean result = new ResponseBean();
		result.getMap().put("state", true);
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
	
	
}
