package com.sensing.core.bean;

import java.io.Serializable;
import java.util.Date;

public class CountBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String day;
	private Integer dayCount;

	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getDayCount() {
		return dayCount;
	}
	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	
	
}
