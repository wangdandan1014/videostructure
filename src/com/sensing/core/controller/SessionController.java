package com.sensing.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysUser;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.MD5Utils;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.WebUtil;

/**
 * 
 * com.sensing.core.controller
 * 用户登录Controller类
 * @author haowenfeng
 * @date 2018年4月23日 下午4:25:10
 */
@Controller
@RequestMapping("/session")
@SuppressWarnings("all")
public class SessionController extends BaseController {

	private static final Log log = LogFactory.getLog(SessionController.class);
	
	@ResponseBody
	@RequestMapping("/test")
	public ResponseBean test(@RequestBody JSONObject params,HttpServletRequest request) {
		log.info("session/test请求的参数为:"+params.toString());
		
		String seconds = params.getString("seconds");
		if ( StringUtils.isEmptyOrNull( seconds ) ) {
			request.getSession().setMaxInactiveInterval( Integer.parseInt(seconds) );
		}
		
		ResponseBean result = new ResponseBean();
		result.setError(0);
		result.setMessage("successful");
		return result;
	}
}
