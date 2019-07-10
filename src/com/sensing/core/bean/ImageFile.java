package com.sensing.core.bean;

import java.io.Serializable;

/**
 * 
 * <p>Title: ImageFile</p>
 * <p>Description: 图片存储类 </p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	haowenfeng
 * @date	2017年8月21日下午8:38:11
 * @version 2.0
 */
public class ImageFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5508142142525514000L;
	
	private int error;
	private String message;//存放图片存放路径
	public int getError() {
		return error;
	}
	public String getMessage() {
		return message;
	}
	public void setError(int error) {
		this.error = error;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
