package com.sensing.core.utils.Exception;

/**
 * 自定义异常，抓拍服务器异常
 */
public class CapServerException extends RuntimeException {
    public CapServerException(String msg) {
        super(msg);
    }

    public CapServerException() {
        super();
    }
}
