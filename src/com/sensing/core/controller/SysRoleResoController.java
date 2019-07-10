package com.sensing.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sensing.core.bean.SysResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.SysRoleReso;
import com.sensing.core.service.ISysRoleResoService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

import java.util.List;


@Controller
@RequestMapping("/sysRoleReso")
@SuppressWarnings("all")
public class SysRoleResoController extends BaseController {

    private static final Log log = LogFactory.getLog(SysRoleResoController.class);


    @Resource
    public ISysRoleResoService sysRoleResoService;

    @ResponseBody
    @RequestMapping("/query")
    public ResponseBean query(@RequestBody JSONObject p) {
        Pager pager = JSONObject.toJavaObject(p, Pager.class);
        ResponseBean result = new ResponseBean();
        try {
            pager = sysRoleResoService.queryPage(pager);
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
    public ResponseBean update(@RequestBody JSONObject m) {
        SysRoleReso model = JSONObject.toJavaObject(m, SysRoleReso.class);
        ResponseBean result = new ResponseBean();
        try {
            if (model != null && model.getId() != null && !model.getId().equals("")) {
                model = sysRoleResoService.updateSysRoleReso(model);
                result.getMap().put("model", model);
                result.setError(0);
                result.setMessage("successful");
            } else {
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
        SysRoleReso model = JSONObject.toJavaObject(m, SysRoleReso.class);
        ResponseBean result = new ResponseBean();
        try {
            model = sysRoleResoService.saveNewSysRoleReso(model);
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
