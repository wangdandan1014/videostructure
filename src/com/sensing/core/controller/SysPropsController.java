package com.sensing.core.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sensing.core.bean.SysProps;
import com.sensing.core.service.ISysPropsService;

@Controller
@RequestMapping("/sysprops")
public class SysPropsController extends BaseController {

	@Resource
	public ISysPropsService sysPropsService;

	@RequestMapping("/query")
	public ModelAndView query(Pager pager) {
		ModelAndView modelAndView = new ModelAndView("sysprops/list");
		try {
			pager = sysPropsService.queryPage(pager);
			modelAndView.addObject("pager", pager);
		} catch (Exception e) {
			modelAndView.setViewName("error/error");
			modelAndView.addObject("errors", e);
		}
		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(SysProps model) {
		ModelAndView modelAndView = new ModelAndView("sysprops/update");
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = sysPropsService.findSysPropsById(model.getUuid());
				modelAndView.addObject("model",model);
			}else{
				modelAndView.addObject("model",new SysProps());
			}
		} catch (Exception e) {
			modelAndView.setViewName("error/error");
			modelAndView.addObject("errors", e);
		}
		return modelAndView;
	}

	/**
	* save data
	*/
	@RequestMapping("/save")
	public ModelAndView save(SysProps model) {
		ModelAndView modelAndView = new ModelAndView("sysprops/update");
		try {
			if(model!=null && model.getUuid()!=null && !model.getUuid().equals("")){
				model = sysPropsService.updateSysProps(model);
			}else{
				model = sysPropsService.saveNewSysProps(model);
			}
		} catch (Exception e) {
			modelAndView.setViewName("error/error");
			modelAndView.addObject("errors", e);
			return modelAndView;
		}
		return query(new Pager());
	}

	@RequestMapping("/delete")
	public ModelAndView delete(String[] idarr) {
		ModelAndView modelAndView = new ModelAndView("sysprops/list");
		try {
			for(int i=0;idarr!=null&&i<idarr.length;i++){
				sysPropsService.removeSysProps(idarr[i]);
			}
		} catch (Exception e) {
			modelAndView.setViewName("error/error");
			modelAndView.addObject("errors", e);
			return modelAndView;
		}
		return query(new Pager());
	}
}
