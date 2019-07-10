package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
public class JobsCap implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String jobsUuid;
	private Integer capType;
	private String capUuid;
	private Integer rectX;
	private Integer rectY;
	private Integer rectCx;
	private Integer rectCy;
	private String seceneUrl;

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getJobsUuid(){
		return jobsUuid;
	}
	public void setJobsUuid(String jobsUuid){
		this.jobsUuid=jobsUuid;
	}
	public Integer getCapType(){
		return capType;
	}
	public void setCapType(Integer capType){
		this.capType=capType;
	}
	public String getCapUuid(){
		return capUuid;
	}
	public void setCapUuid(String capUuid){
		this.capUuid=capUuid;
	}
	public Integer getRectX(){
		return rectX;
	}
	public void setRectX(Integer rectX){
		this.rectX=rectX;
	}
	public Integer getRectY(){
		return rectY;
	}
	public void setRectY(Integer rectY){
		this.rectY=rectY;
	}
	public Integer getRectCx(){
		return rectCx;
	}
	public void setRectCx(Integer rectCx){
		this.rectCx=rectCx;
	}
	public Integer getRectCy(){
		return rectCy;
	}
	public void setRectCy(Integer rectCy){
		this.rectCy=rectCy;
	}
	public String getSeceneUrl(){
		return seceneUrl;
	}
	public void setSeceneUrl(String seceneUrl){
		this.seceneUrl=seceneUrl;
	}
}