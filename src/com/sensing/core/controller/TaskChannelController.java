package com.sensing.core.controller;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sensing.core.bean.TaskChannel;
import com.sensing.core.service.ITaskChannelService;

import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/taskChannel")
public class TaskChannelController extends BaseController {

	private static final Log log = LogFactory.getLog(TaskChannelController.class);

	
	@Resource
	public ITaskChannelService taskChannelService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		try {
			pager = taskChannelService.queryPage(pager);
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
	@RequestMapping("/update")
	public ResponseBean update(@RequestBody JSONObject m)
	{
		TaskChannel model = JSONObject.toJavaObject(m, TaskChannel.class);
		ResponseBean result = new ResponseBean();
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = taskChannelService.updateTaskChannel(model);
				result.getMap().put("model", model);
				result.setError(0);
				result.setMessage("successful");
			}else{
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
		TaskChannel model = JSONObject.toJavaObject(m, TaskChannel.class);
		ResponseBean result = new ResponseBean();
		try {
			model = taskChannelService.saveNewTaskChannel(model);
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
}
