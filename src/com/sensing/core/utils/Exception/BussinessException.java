package com.sensing.core.utils.Exception;

/**
 * BussinessException
 */
public class BussinessException extends RuntimeException {

	private static final long	serialVersionUID	= 42717324355691506L;
	private Exception			exception;
	private String				message;
	private String				errorCode;
	private String				keyValue;


	public BussinessException() {
		super();
	}


	public BussinessException(Exception e) {
		this.exception = e;
	}

	/**
	 * @param keyValue
	 * @param errorCode
	 */
	public BussinessException(String keyValue, String errorCode) {
		this.keyValue = keyValue;
		this.errorCode = errorCode;
	}

	/**
	 * @param errorCode
	 */
	public BussinessException(String errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode;
	}

	/**
	 * @param msg
	 * @param e
	 */
	public BussinessException(String msg, Exception e) {
		this.exception = e;
		this.message = msg;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * @param exception
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
