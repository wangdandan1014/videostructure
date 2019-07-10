package com.sensing.core.bean;

import java.io.Serializable;

/**
 * 
 * com.sensing.core.bean
 * @author haowenfeng
 * @date 2017年12月11日 上午10:45:03
 */
public class SysPara  implements Serializable {

	/**
	 * serialVersionUID
	 * @author haowenfeng
	 * @date 2017年12月11日 上午10:44:14
	 */
	private static final long serialVersionUID = 6029846856415924719L;
	
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "SysPara [key=" + key + ", value=" + value + "]";
	}
	
}
