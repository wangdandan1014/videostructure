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
import com.mysql.jdbc.StringUtils;
import com.sensing.core.bean.SysResource;
import com.sensing.core.service.ISysResourceService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;


@Controller
@RequestMapping("/sysResource")
@SuppressWarnings("all")
public class SysResourceController extends BaseController {

    private static final Log log = LogFactory.getLog(SysResourceController.class);


    @Resource
    public ISysResourceService sysResourceService;

    
    /**
     * 查询操作日志的二级联通的选项
     * @param p
     * @return
     * @author mingxingyu
     * @date   2019年4月11日 下午1:58:34
     */
    @ResponseBody
    @RequestMapping("/queryOpeLogList")
    public ResponseBean queryOpeLogList(@RequestBody JSONObject p) {
        Pager pager = JSONObject.toJavaObject(p, Pager.class);
        ResponseBean result = new ResponseBean();
        try {
//        	pager.getF().put(key, value);
            pager = sysResourceService.queryOpeLog();
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
    @RequestMapping("/query")
    public ResponseBean query(@RequestBody JSONObject p) {
        Pager pager = JSONObject.toJavaObject(p, Pager.class);
        ResponseBean result = new ResponseBean();
        try {
            pager = sysResourceService.queryPage(pager);
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
        SysResource model = JSONObject.toJavaObject(m, SysResource.class);
        ResponseBean result = new ResponseBean();
        try {
            if (model != null && model.getId() != null && !model.getId().equals("")) {
                model = sysResourceService.updateSysResource(model);
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
        SysResource model = JSONObject.toJavaObject(m, SysResource.class);
        ResponseBean result = new ResponseBean();
        try {
            model = sysResourceService.saveNewSysResource(model);
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

    /**
     * 新建角色的时候，根据当前用户uuid查询所拥有的资源，返回的是子父集合的形式
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getsysresobyuuid")
    public ResponseBean getSysResoByUuid() {
        ResponseBean result = new ResponseBean();
        List<SysResource> list = sysResourceService.getSysResoByUuid(getUser().getUuid());
        result.getMap().put("resultList", list);
        result.setError(0);
        result.setMessage("successful");
        return result;
    }

    /**
     * 得到用户的权限所对应的searchcode集合
     * @return
     */
    @ResponseBody
    @RequestMapping("/getsysresosearchcode")
    public ResponseBean getSysResoSearchCode() {
        ResponseBean result = new ResponseBean();
        List<String> list = sysResourceService.getSysResoSearchCode(getUser().getUuid());
        result.getMap().put("resultList", list);
        result.setError(0);
        result.setMessage("successful");
        return result;
    }


}
