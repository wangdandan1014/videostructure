package com.sensing.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sensing.core.bean.UserContext;
import com.sensing.core.utils.Exception.InValidParamException;
import com.sensing.core.utils.props.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InvalidClassException;

@SuppressWarnings("all")
public class BaseController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    //是否是测试环境
    public static boolean isDebug = false;

    static {
        if ("dev".equals(PropUtils.ENV)) {
            isDebug = true;
        }
    }

    public UserContext user;

    @ModelAttribute
    public void initPath(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        String base = request.getContextPath();
        String fullPath = request.getScheme() + "://" + request.getServerName() + base;
        if (fullPath.contains("localhost") || "dev".equals(PropUtils.ENV)) {
            isDebug = true;
        } else {
            isDebug = false;
        }
        model.addAttribute("base", base);
        model.addAttribute("fullPath", fullPath);
        String uuid = request.getHeader("uuid");
        user = new UserContext();
        user.setUuid(uuid);
    }

    public UserContext getUser() {
        if (user == null || StringUtils.isEmptyOrNull(user.getUuid()) || user.getUuid().contains("Object")) {
            throw new InValidParamException("header的uuid异常");
        }
        return user;
    }

    public String getUuid() {
        return getUser().getUuid();
    }

    /**
     * 异常处理类，和RequestInfoLog的切面冲突，暂时去掉
     * 因HttpServletRequest body 无法多次读取，request中的reader会为空，需添加RequestFilter
     */
//    @ExceptionHandler
//    @ResponseBody
//    public ResponseBean handleAndReturnData(HttpServletRequest request, HttpServletResponse response, Exception ex) {
//        /****返回实体构建****/
//        ResponseBean bean = new ResponseBean();
//        bean.setError(100);
//        bean.setMessage("fail");
//
//        /****  从request中拿到入参和header  ****/
//        String header = request.getHeader("uuid");
//        BufferedReader reader = null;
//        StringBuffer json = new StringBuffer();
//        String line = null;
//        try {
//            reader = request.getReader();
//            while (null != (line = reader.readLine())) {
//                json.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        logger.error("==请求地址==" + request.getRequestURL().toString() + "\n" + "==入参============" + json + "\n" + "==header里的uuid==" + header + "\n" + StringUtils.getStackTrace(ex));
//        return bean;
//
//    }


}
