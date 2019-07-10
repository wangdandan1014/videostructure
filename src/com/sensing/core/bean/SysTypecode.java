package com.sensing.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *@author wenbo
 */
public class SysTypecode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String typeCode;
	private int    itemId;
	private String itemCode;
	private String itemValue;
	private Integer parentId;
	private String searchCode;
	private String memo;
	
	List<SysTypecode> subList = new ArrayList<SysTypecode>();//子属性列表

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid=uuid;
	}
	public String getTypeCode(){
		return typeCode;
	}
	public void setTypeCode(String typeCode){
		this.typeCode=typeCode;
	}
	public String getItemCode(){
		return itemCode;
	}
	public void setItemCode(String itemCode){
		this.itemCode=itemCode;
	}
	public String getItemValue(){
		return itemValue;
	}
	public void setItemValue(String itemValue){
		this.itemValue=itemValue;
	}
	public Integer getParentId(){
		return parentId;
	}
	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}
	public String getSearchCode(){
		return searchCode;
	}
	public void setSearchCode(String searchCode){
		this.searchCode=searchCode;
	}
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo=memo;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public List<SysTypecode> getSubList() {
		return subList;
	}
	public void setSubList(List<SysTypecode> subList) {
		this.subList = subList;
	}
	@Override
	public String toString() {
		return "SysTypecode [uuid=" + uuid + ", typeCode=" + typeCode
				+ ", itemId=" + itemId + ", itemCode=" + itemCode
				+ ", itemValue=" + itemValue + ", parentId=" + parentId
				+ ", searchCode=" + searchCode + ", memo=" + memo + "]";
	}
	
	
	
}