package com.sensing.core.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ResponseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long error;
	private String message;
	private Map map = new HashMap();

    public ResponseBean(long error, String message, Map map) {
        this.error = error;
        this.message = message;
        this.map = map;
    }

    public ResponseBean(long error, String message) {
        this.error = error;
        this.message = message;
    }

    public ResponseBean() {
    }

    public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public long getError() {
		return error;
	}
	public void setError(long error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
