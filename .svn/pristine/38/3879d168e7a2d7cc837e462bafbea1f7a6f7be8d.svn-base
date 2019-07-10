package com.sensing.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.sensing.core.bean.SysPara;
import com.sensing.core.service.ISysParaService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.time.TransfTimeUtil;


/**
 * 
 * com.sensing.core.controller
 * 系统参数Controller类
 * @author haowenfeng
 * @date 2017年12月11日 下午2:47:32
 * 
 */
@Controller
@RequestMapping("/sysPara")
@SuppressWarnings("all")
public class SysParaController extends BaseController {

	private static final Log log = LogFactory.getLog(SysParaController.class);
	
	@Autowired
	private ISysParaService sysParaService;
	
	
	/**
	 * 开启/关闭无人值守
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/openOrClose")
	public ResponseBean openOrClose(@RequestBody JSON json) {
		log.info("无人值守开关被调用,参数:  "+json);
		ResponseBean result = new ResponseBean();
		//1.判断参数是否正常
		if(StringUtils.isNullOrEmpty(json+"")){
			result.setError(-1);
			result.setMessage("打开或者关闭无人值守,传递的参数为空!");
			return result;
		}else{
		//2. 执行业务处理	
			List<SysPara> list = JSONObject.parseArray(json.toString(),SysPara.class);
			Boolean falg=true;
			try {
				falg = sysParaService.openOrClose(list);
			} catch (Exception e) {
				falg=false;
				e.printStackTrace();
			}
			if(falg){
				//修改无人值守数据的情况，通知更新facejob
//				strategyService.updateNobodyWork("更新无人值守数据","modify");
				//通知同步更新无人值守数据
//				strategyService.notifySystem("sys_para", 2, null);
				result.setError(0);
				result.setMessage("success");
				return result;
			}else{
				result.setError(-1);
				result.setMessage("false");
				return result;
			}
		}
	}	
	
	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject json) {
		
		Long sTime = System.currentTimeMillis();
		log.info("调用 sysPara/query接口,传递参数为: " + json);
		//1.校验接口参数
		if (StringUtils.isNullOrEmpty(json.toJSONString())) {
			log.error("请求参数为空!");
			return retInvalidResult();
		}
		//2.转换参数为后台分页对象
		Pager pager = JSONObject.toJavaObject(json, Pager.class);
		//3.创建返回对象
		ResponseBean result = new ResponseBean();
		
		//4.调用业务接口查询后台数据
		try {
			pager = sysParaService.queryPage(pager);
			result.getMap().put("pager", pager);
			result.setError(0);
			result.setMessage("success");
		} catch (Exception e) {
			log.error("分页查询异常!",e);
			e.printStackTrace();
			result.setError(-1);
			result.setMessage("query pager exception");
			return result;
		}
		Long eTime = System.currentTimeMillis();
		TransfTimeUtil.getInterfaceUsedTime(sTime, eTime, "调用系统参数查询query()接口耗时:");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/querySysParaByKey")
	public ResponseBean querySysParaByKey(@RequestBody JSONObject json) {
		
		Long sTime = System.currentTimeMillis();
		log.info("调用 sysPara/querySysParaByKey接口,传递参数为: " + json);
		//1.校验接口参数
		if (StringUtils.isNullOrEmpty(json.toJSONString())) {
			log.error("请求参数为空!");
			return retInvalidResult();
		}
		
		//2.转换参数为后台参数
		String key = json.getString("key");
		//3.创建返回对象
		ResponseBean result = new ResponseBean();
		SysPara sysPara = null;
		
		//4.调用业务层处理
		try {
			sysPara = sysParaService.querySysParaByKey(key);
			result.getMap().put("sysPara", sysPara);
			result.setError(0);
			result.setMessage("success");
		} catch (Exception e) {
			log.error("根据主键查询系统参数信息异常!",e);
			e.printStackTrace();
			result.setError(-1);
			result.setMessage("query syspara exception");
			return result;
		}
		
		Long eTime = System.currentTimeMillis();
		TransfTimeUtil.getInterfaceUsedTime(sTime, eTime, "调用系统参数查询querySysParaByKey()接口耗时:");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public ResponseBean save(@RequestBody JSONObject json) {
		Long sTime = System.currentTimeMillis();
		log.info("调用 save 接口,传递参数为: " + json);
		//1.校验接口参数
		if (StringUtils.isNullOrEmpty(json.toJSONString())) {
			log.error("请求参数为空!");
			return retInvalidResult();
		}
		//2.转换参数为后台对象
		SysPara sysPara = JSONObject.toJavaObject(json, SysPara.class);
		//3.创建返回对象
		ResponseBean result = new ResponseBean();
		
		//4.调用业务层处理
		try {
			sysPara = sysParaService.saveSysPara(sysPara);
			result.getMap().put("sysPara", sysPara);
			result.setError(0);
			result.setMessage("success");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			result.setError(-1);
			result.setMessage("query syspara exception");
			return result;
		}
		Long eTime = System.currentTimeMillis();
		TransfTimeUtil.getInterfaceUsedTime(sTime, eTime, "调用系统参数新增save()接口耗时:");
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/modify")
	public ResponseBean modify(@RequestBody JSONObject json){
		
		Long sTime = System.currentTimeMillis();
		log.info("调用 modify 接口,传递参数为: " + json);
		//1.校验接口参数
		if (StringUtils.isNullOrEmpty(json.toJSONString())) {
			log.error("请求参数为空!");
			return retInvalidResult();
		}
		//2.转换参数为后台对象
		SysPara sysPara = JSONObject.toJavaObject(json, SysPara.class);
		//3.创建返回对象
		ResponseBean result = new ResponseBean();
		
		//4.调用业务层处理
		try {
			sysPara = sysParaService.updateSysPara(sysPara);
			result.getMap().put("sysPara", sysPara);
			result.setError(0);
			result.setMessage("success");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			result.setError(-1);
			result.setMessage("query syspara exception");
			e.printStackTrace();
		}
		
		Long eTime = System.currentTimeMillis();
		TransfTimeUtil.getInterfaceUsedTime(sTime, eTime, "调用系统参数新增modify()接口耗时:");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody String[] keys,HttpServletRequest request , HttpServletResponse response){
		
		Long sTime = System.currentTimeMillis();
		ResponseBean result = new ResponseBean();
		String token = request.getHeader("token");
		//查询组织结构 和权限
		
		if(keys!=null&&keys.length>0){
			
			for (int i = 0; i < keys.length; i++) {
				try {
					sysParaService.removeSysPara(keys[i]);
				} catch (Exception e) {
					e.printStackTrace();
					result.setError(-1);
					result.setMessage("delete SysPara exception!");
					return result;
				}
			}
			result.setError(0);
			result.setMessage("success");
		}else{
			return retInvalidResult();
		}
		
		Long eTime = System.currentTimeMillis();
		TransfTimeUtil.getInterfaceUsedTime(sTime, eTime, "调用系统参数delete()接口耗时:");
		return result;
	}
	
	
	// 请求参数异常
	public ResponseBean retInvalidResult() {

		ResponseBean result = new ResponseBean();
		result.setError(1000);
		result.setMessage("非法请求参数!");

		return result;
	}
}
