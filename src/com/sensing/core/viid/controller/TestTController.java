package com.sensing.core.viid.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensing.core.utils.BaseController;

/**
 * VIID测试
 * <p>Title: TestController</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年6月25日
 * @version 1.0
 */
@Controller
@RequestMapping("/VIID")
public class TestTController extends BaseController{

	@RequestMapping(value="/test12/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public String test12(@PathVariable("id") String id){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = id+":"+sdf.format(new Date());
		return s;
	}
	
	
}
