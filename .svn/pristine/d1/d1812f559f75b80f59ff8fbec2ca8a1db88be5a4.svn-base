package com.sensing.core.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.props.PropUtils;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/props")
public class PropsController extends BaseController {

	private static final Log log = LogFactory.getLog(PropsController.class);
	@RequestMapping("/reloadPorperties")
	public void reloadPorperties() {
		try {
			log.error("重新加载remoteutil.propeitis文件");
			PropUtils.reloadProperties();
		} catch (Exception e) {
			
		}
	}
	
	@RequestMapping("/reloadLog4jConfig")
	public void reloadLog4jConfig(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		try {
			log.error("调用该reloadLog4jConfig方法重新加载log4j文件");
			PropertyConfigurator.configure(request.getServletContext().getRealPath("/") + "WEB-INF/classes/properties"+PropUtils.ENV+"/log4j.properties");
		} catch (Exception e) {
			
		}
	}

}
