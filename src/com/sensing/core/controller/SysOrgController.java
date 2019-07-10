package com.sensing.core.controller;

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
import com.sensing.core.bean.SysOrg;
import com.sensing.core.service.ISysOrgService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
@Controller
@RequestMapping("/sysOrg")
public class SysOrgController extends BaseController {

	private static final Log log = LogFactory.getLog(SysOrgController.class);

	
	@Resource
	public ISysOrgService sysOrgService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		log.info("调用/sysOrg/query接口参数："+p);
		Pager pager = new Pager();
		if(!p.isEmpty()){
			pager = JSONObject.toJavaObject(p, Pager.class);
		}	
		ResponseBean result = new ResponseBean();
		try {
			pager = sysOrgService.queryPage(pager);
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
		SysOrg model = JSONObject.toJavaObject(m, SysOrg.class);
		ResponseBean result = new ResponseBean();
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = sysOrgService.updateSysOrg(model);
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
		SysOrg model = JSONObject.toJavaObject(m, SysOrg.class);
		ResponseBean result = new ResponseBean();
		try {
			model = sysOrgService.saveNewSysOrg(model);
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
	public ResponseBean delete(@RequestBody String[] idarr,HttpServletRequest request , HttpServletResponse response) {
		ResponseBean result = new ResponseBean();
		String token = request.getHeader("token");
		//查询组织结构 和权限
		
		try {
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				sysOrgService.removeSysOrg(idarr[i]);
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
