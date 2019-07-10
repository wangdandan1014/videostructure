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
import com.sensing.core.bean.Capture;
import com.sensing.core.service.ICaptureService;

import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/capture")
public class CaptureController extends BaseController {

	private static final Log log = LogFactory.getLog(CaptureController.class);

	
	@Resource
	public ICaptureService captureService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		try {
			pager = captureService.queryPage(pager);
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
		Capture model = JSONObject.toJavaObject(m, Capture.class);
		ResponseBean result = new ResponseBean();
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = captureService.updateCapture(model);
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
		Capture model = JSONObject.toJavaObject(m, Capture.class);
		ResponseBean result = new ResponseBean();
		try {
			model = captureService.saveNewCapture(model);
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
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				captureService.removeCapture(idarr[i]);
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
