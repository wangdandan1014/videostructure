package com.sensing.core.utils.Exception;

/**
 * 自定义异常，参数不全
 */
public class InValidParamException extends RuntimeException {
    public InValidParamException(String msg) {
        super(msg);
    }

    public InValidParamException() {
        super();
    }
}
