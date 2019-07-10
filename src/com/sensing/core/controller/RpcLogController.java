package com.sensing.core.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sensing.core.utils.*;
import com.sensing.core.utils.results.ResultUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.RpcLog;
import com.sensing.core.bean.RpcLogLogin;
import com.sensing.core.bean.RpcLogRun;
import com.sensing.core.service.IRpcLogService;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.utils.time.DateUtil;

@Controller
@RequestMapping("/rpclog")
public class RpcLogController extends BaseController {

    private static final Log log = LogFactory.getLog(RpcLogController.class);
    @Resource
    public IRpcLogService rpcLogService;

    @RequestMapping("/query")
    @ResponseBody
    public ResponseBean query(@RequestBody JSONObject p, HttpServletRequest request) {
        ResponseBean result = new ResponseBean();
        try {
//            String uuid = request.getHeader("uuid");
            Pager pager = JSONObject.toJavaObject(p, Pager.class);
            if (pager != null && pager.getF() != null && StringUtils.isNotEmpty(pager.getF().get("errorMsg"))) {
                if (ValidationUtils.isSpecialChar(pager.getF().get("errorMsg"))) {
                    return ResultUtils.error(100, "搜索框内不能含有特殊字符");
                }
            }
            pager = rpcLogService.queryPage(pager);
            result.setError(0);
            result.getMap().put("pager", pager);
            result.setMessage("successful");
        } catch (Exception e) {
            result.setError(-1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseBean save(@RequestBody JSONObject p) {
        ResponseBean result = new ResponseBean();
        try {
            RpcLog model = JSONObject.toJavaObject(p, RpcLog.class);
            model = rpcLogService.saveNewRpcLog(model);
            result.setError(0);
            result.setMessage("successful");

        } catch (Exception e) {
            result.setError(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    @RequestMapping("/getModle")
    @ResponseBody
    public ResponseBean getModle(@RequestBody JSONObject p) {
        ResponseBean result = new ResponseBean();
        try {
            List<RpcLog> model = rpcLogService.getModle();
            result.setError(0);
            result.setMessage("successful");
            result.getMap().put("data", model);
        } catch (Exception e) {
            result.setError(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    @RequestMapping("/export")
    @ResponseBody
    public ResponseBean export(@RequestBody JSONObject p) {
        ResponseBean result = new ResponseBean();
        RpcLog rpcLog = JSONObject.toJavaObject(p, RpcLog.class);
        FileOutputStream out = null;
        String dateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String filePath = PropUtils.getString("cachedata_addr") + "/" + dateStr + ".xlsx";
        try {
            out = new FileOutputStream(filePath);
            if (rpcLog.getType() != null && rpcLog.getType().equals(1)) {
                ExcelHelper<RpcLogLogin> util = new ExcelHelper(RpcLogLogin.class);
                List<RpcLogLogin> datas = rpcLogService.queryRpcLoginLog(rpcLog);
                util.exportExcel(datas, "日志", 60000, out);
            }
            if (rpcLog.getType() != null && rpcLog.getType().equals(2)) {
                ExcelHelper<RpcLog> util = new ExcelHelper(RpcLog.class);
                List<RpcLog> datas = rpcLogService.queryRpcLog(rpcLog);
                util.exportExcel(datas, "日志", 60000, out);
            }
            if (rpcLog.getType() != null && rpcLog.getType().equals(3)) {
                ExcelHelper<RpcLogRun> util = new ExcelHelper(RpcLogRun.class);
                List<RpcLogRun> datas = rpcLogService.queryRpcRunLog(rpcLog);
                util.exportExcel(datas, "日志", 60000, out);
            }
            filePath = PropUtils.getString("watch_cachedata") + "/" + dateStr + ".xlsx";
            result.setError(0);
            result.setMessage("successful");
            result.getMap().put("filePath", filePath);
        } catch (Exception ex) {
            result.setError(-1);
            result.setMessage(ex.getMessage());
            logger.debug(ex.getMessage(), ex);
            return result;

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    result.setError(-1);
                    result.setMessage(e.getMessage());
                    logger.debug(e.getMessage(), e);
                    return result;
                }
            }
        }
        return result;
    }

    @RequestMapping("/getonlineusercount")
    @ResponseBody
    public ResponseBean getOnlineUserCount(@RequestBody JSONObject p) {
        ResponseBean result = rpcLogService.getOnlineUserCount();
        return result;
    }


}
