package com.sensing.core.bean;

import java.io.Serializable;
/**
 *@author wenbo
 */
public class SysProps implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String module;
	private String path;
	private String propName;
	private String propKey;
	private Integer orde;
	private Integer leve;
	private Integer isReadonly;
	private Integer needsReboot;
	private String descreption;
	private String enums;
	private String fileName;
	
	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getModule(){
		return module;
	}
	public void setModule(String module){
		this.module=module;
	}
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path=path;
	}
	public String getPropName(){
		return propName;
	}
	public void setPropName(String propName){
		this.propName=propName;
	}
	public String getPropKey(){
		return propKey;
	}
	public void setPropKey(String propKey){
		this.propKey=propKey;
	}
	public Integer getOrde(){
		return orde;
	}
	public void setOrde(Integer orde){
		this.orde=orde;
	}
	public Integer getLeve(){
		return leve;
	}
	public void setLeve(Integer leve){
		this.leve=leve;
	}
	public Integer getIsReadonly(){
		return isReadonly;
	}
	public void setIsReadonly(Integer isReadonly){
		this.isReadonly=isReadonly;
	}
	public Integer getNeedsReboot(){
		return needsReboot;
	}
	public void setNeedsReboot(Integer needsReboot){
		this.needsReboot=needsReboot;
	}
	public String getDescreption(){
		return descreption;
	}
	public void setDescreption(String descreption){
		this.descreption=descreption;
	}
	public String getEnums(){
		return enums;
	}
	public void setEnums(String enums){
		this.enums=enums;
	}
}