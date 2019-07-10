package com.sensing.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *@author wenbo
 */
@SuppressWarnings("all")
public class RegionsTree implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String regionName;
	private Integer parentId;
	private String nodeType;
	private Integer state;
	private Short isDeleted;
	private List<RegionsTree> children;
	public Short getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<RegionsTree> getChildren() {
		return children;
	}
	public void setChildren(List<RegionsTree> children) {
		this.children = children;
	}
	public String getRegionName(){
		return regionName;
	}
	public void setRegionName(String regionName){
		this.regionName=regionName;
	}
	
	public Integer getParentId(){
		return parentId;
	}
	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}