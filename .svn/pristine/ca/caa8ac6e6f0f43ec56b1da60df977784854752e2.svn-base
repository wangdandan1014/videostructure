package com.sensing.core.controller;

import java.util.Map;

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
import com.sensing.core.bean.SysOrgObject;
import com.sensing.core.service.IAuthorizationService;
import com.sensing.core.service.ISysOrgObjectService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.ResponseBean;



@Controller
@RequestMapping("/sysOrgObject")
@SuppressWarnings("all")
public class SysOrgObjectController extends BaseController {

	private static final Log log = LogFactory.getLog(SysOrgObjectController.class);

	@Resource
	private IAuthorizationService authorizationService;
	@Resource
	public ISysOrgObjectService sysOrgObjectService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p,HttpServletRequest request, HttpServletResponse response) {
		log.info("调用/sysOrgObject/query接口参数"+p);
		ResponseBean result = new ResponseBean();
		String token = request.getHeader("token");
		try {
			String objectType = p.getString("objectType");
			String objectId = p.getString("objectId");
			//String orgId = p.getString("orgId");
			String orgId = authorizationService.getOrgArrFromToken(token)[0];
			int level = authorizationService.getObjectAuthAll(objectType,objectId,token);
			Map<String,Object> map= sysOrgObjectService.query(objectType,objectId,orgId,level);
			//map.putAll((Map) JSONObject.parse(p.toJSONString()));
			result.getMap().put("pager", map);
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
		SysOrgObject model = JSONObject.toJavaObject(m, SysOrgObject.class);
		ResponseBean result = new ResponseBean();
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = sysOrgObjectService.updateSysOrgObject(model);
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
	public ResponseBean save(@RequestBody JSONObject m,HttpServletRequest request, HttpServletResponse response) {
		log.info("调用/sysOrgObject/save接口参数："+m);
		String token = request.getHeader("token");
		String orgId = authorizationService.getOrgArrFromToken(token)[0];
		ResponseBean result = new ResponseBean();
		String objectType = m.getString("objectType");
		String objectId = m.getString("objectId");
		int ownLevel = m.getIntValue("ownLevel");
		try {
			//int level = authorizationService.getObjectAuth(objectType,objectId,token)<1?ownLevel:authorizationService.getObjectAuth(objectType,objectId,token);
			int level = authorizationService.getObjectAuthAll(objectType,objectId,token);
			sysOrgObjectService.saveSysOrgObject(m, orgId,level);
			
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
				sysOrgObjectService.removeSysOrgObject(idarr[i]);
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
