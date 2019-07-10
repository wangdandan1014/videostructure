package com.sensing.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wenbo
 */
@SuppressWarnings("all")
public class Regions implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String rId;
	private String regionName;
	private String regionDescription;
	private Integer parentId;
	private Integer regionLevel;
	private Integer regionSort;
	private String searchCode;
	private String nodeType;// 0：区域，1：通道
	private Integer state;
	private Short isDeleted;
	private Integer openCount;// 当前区域下打开的通道数量
	private Integer channelCount;// 当前区域下共有的通道数量
	private Integer channelState;// 给首页用的通道状态 0:布控中（在线）、1:布控中（离线）、2:未布控
	private Long createTime;

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getOpenCount() {
		return openCount == null ? 0 : openCount;
	}

	public void setOpenCount(Integer openCount) {
		this.openCount = openCount;
	}

	private List<Regions> childRegins;

	public List<Regions> getChildRegins() {
		return childRegins;
	}

	public void setChildRegins(List<Regions> childRegins) {
		this.childRegins = childRegins;
	}

	public Integer getChannelState() {
		return channelState;
	}

	public void setChannelState(Integer channelState) {
		this.channelState = channelState;
	}

	public Integer getChannelCount() {
		return channelCount == null ? 0 : channelCount;
	}

	public void setChannelCount(Integer channelCount) {
		this.channelCount = channelCount;
	}

	public Integer getId() {
		return id == null ? 0 : id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	public Integer getParentId() {
		return parentId == null ? 0 : parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}

	public Integer getRegionSort() {
		return regionSort;
	}

	public void setRegionSort(Integer regionSort) {
		this.regionSort = regionSort;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
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

	@Override
	public String toString() {
		return "Regions [id=" + id + ", rId=" + rId + ", regionName=" + regionName + ", regionDescription="
				+ regionDescription + ", parentId=" + parentId + ", regionLevel=" + regionLevel + ", regionSort="
				+ regionSort + ", searchCode=" + searchCode + ", nodeType=" + nodeType + ", state=" + state + "]";
	}

}