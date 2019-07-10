package com.sensing.core.utils;

public class LiCenseException extends Exception{

	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2018年1月10日 下午3:56:08
	 */
	private static final long serialVersionUID = -2620249985934409918L;

	public LiCenseException() {
		super();
	}

	public LiCenseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LiCenseException(String message, Throwable cause) {
		super(message, cause);
	}

	public LiCenseException(String message) {
		super(message);
	}

	public LiCenseException(Throwable cause) {
		super(cause);
	}

	
}
