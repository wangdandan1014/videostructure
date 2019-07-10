package com.sensing.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.service.ISysSubscribeService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;

@Controller
@RequestMapping("/sysSubscribe")
@SuppressWarnings("all")
public class SysSubscribeController extends BaseController {

	private static final Log log = LogFactory.getLog(SysSubscribeController.class);

	@Resource
	public ISysSubscribeService sysSubscribeService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		try {
			pager = sysSubscribeService.queryPage(pager);
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
		SysSubscribe model = JSONObject.toJavaObject(m, SysSubscribe.class);
		ResponseBean result = new ResponseBean();
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = sysSubscribeService.updateSysSubscribe(model);
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
	* 告警订阅
	*/
	@ResponseBody
	@RequestMapping("/saveSec")
	public ResponseBean saveSec(@RequestBody JSONObject m) {
		SysSubscribe model = JSONObject.toJavaObject(m, SysSubscribe.class);
		ResponseBean result = new ResponseBean();
		if(!StringUtils.isNotEmpty(model.getUid())){
			result.setError(12001);
			result.setMessage("订阅用户的uuid不能为空！");
			return result;
		}else if(!StringUtils.isNotEmpty(model.getJobId())){
			result.setError(12001);
			result.setMessage("任务的uuid不能为空！");
			return result;
		}else{
			try {
				model.setSubType(Constants.SUBSCRIBE_TYPE_ORIGINAL);
				List<SysSubscribe> ssList  = sysSubscribeService.queryByParam(model.getUid(),model.getJobId(),model.getSubType());
				if(ssList!=null && ssList.size()>0){
					result.setError(12001);
					result.setMessage("已存在该订阅！");
				}else{
					model = sysSubscribeService.saveNewSysSubscribe(model);
					result.getMap().put("model", model);
					result.setError(0);
					result.setMessage("successful");
				}
				
			} catch (Exception e) {
				log.error(e);
				result.setError(100);
				result.setMessage(e.getMessage());
			}
		}
	
		return result;
	}
	
	/**
	* 报警订阅
	*/
//	@ResponseBody
//	@RequestMapping("/saveOriginal")
//	public ResponseBean saveOriginal(@RequestBody JSONObject m) {
//		SysSubscribe model = JSONObject.toJavaObject(m, SysSubscribe.class);
//		ResponseBean result = new ResponseBean();
//		if(!StringUtils.isNotEmpty(model.getUid())){
//			result.setError(12003);
//			result.setMessage("订阅用户的uuid不能为空！");
//			return result;
//		}else if(!StringUtils.isNotEmpty(model.getJobId())){
//			result.setError(12003);
//			result.setMessage("任务的uuid不能为空！");
//			return result;
//		}else{
//			try {
//				model.setSubType(Constants.SUBSCRIBE_TYPE_SEC);
//				Jobs job = jobsService.findJobsById(model.getJobId());
//				if(job!=null){
//					model.setState(job.getState());
//				}else{
//					model.setState(0);
//				}
//				List<SysSubscribe> ssList  = sysSubscribeService.queryByParam(model.getUid(),model.getJobId(),model.getSubType());
//				if(ssList!=null && ssList.size()>0){
//					result.setError(12001);
//					result.setMessage("已存在该订阅！");
//				}else{
//					model = sysSubscribeService.saveNewSysSubscribe(model);
//					result.getMap().put("model", model);
//					result.setError(0);
//					result.setMessage("successful");
//				}
//				
//			} catch (Exception e) {
//				log.error(e);
//				result.setError(100);
//				result.setMessage(e.getMessage());
//			}
//		}
//		
//		return result;
//	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody String[] idarr,HttpServletRequest request , HttpServletResponse response) {
		ResponseBean result = new ResponseBean();
		String token = request.getHeader("token");
		//查询组织结构 和权限
		
		try {
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				sysSubscribeService.removeSysSubscribe(idarr[i]);
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
	
	/**
	 * 取消告警订阅
	 * author dongsl
	 * date 2017年8月8日下午3:20:03
	 */
	@ResponseBody
	@RequestMapping("/cancelAlarmSub")
	public ResponseBean cancelAlarmSub(@RequestBody JSONObject m) {
		SysSubscribe model = JSONObject.toJavaObject(m, SysSubscribe.class);
		model.setSubType(Constants.SUBSCRIBE_TYPE_ORIGINAL);
		ResponseBean result = new ResponseBean();
		if(!StringUtils.isNotEmpty(model.getUid())){
			result.setError(12002);
			result.setMessage("订阅用户的uuid不能为空！");
			return result;
		}else if(!StringUtils.isNotEmpty(model.getJobId())){
			result.setError(12002);
			result.setMessage("任务的uuid不能为空！");
			return result;
		}else{
			try {
				sysSubscribeService.cancleSub(model);
				result.setError(0);
				result.setMessage("successful");
			} catch (Exception e) {
				log.error(e);
				result.setError(100);
				result.setMessage(e.getMessage());
			}
		}
		
		return result;
	}
	
	/**
	 * 取消报警订阅
	 * author dongsl
	 * date 2017年8月8日下午3:24:35
	 */
	@ResponseBody
	@RequestMapping("/cancleWarningSub")
	public ResponseBean cancleWarningSub(@RequestBody JSONObject m) {
		SysSubscribe model = JSONObject.toJavaObject(m, SysSubscribe.class);
		model.setSubType(Constants.SUBSCRIBE_TYPE_SEC);
		ResponseBean result = new ResponseBean();
		if(!StringUtils.isNotEmpty(model.getUid())){
			result.setError(12004);
			result.setMessage("订阅用户的uuid不能为空！");
			return result;
		}else if(!StringUtils.isNotEmpty(model.getJobId())){
			result.setError(12004);
			result.setMessage("任务的uuid不能为空！");
			return result;
		}else{
			try {
				sysSubscribeService.cancleSub(model);
				result.setError(0);
				result.setMessage("successful");
			} catch (Exception e) {
				log.error(e);
				result.setError(100);
				result.setMessage(e.getMessage());
			}
		}
		
		return result;
	}
}
