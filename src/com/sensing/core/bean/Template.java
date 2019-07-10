package com.sensing.core.bean;

import java.io.Serializable;

/**
 * @author mingxingyu
 */
public class Template implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String objUuid;
	private Short index;
	private Short type;
	private String imageUrl;
	private byte[] fea;
	private Integer templatedbId;
	private Short isDeleted;
	private Long createTime;
	private String createUser;
	private Long modifyTime;
	private String modifyUser;
	private transient String imageData;

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getObjUuid() {
		return objUuid;
	}

	public void setObjUuid(String objUuid) {
		this.objUuid = objUuid;
	}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public byte[] getFea() {
		return fea;
	}

	public void setFea(byte[] fea) {
		this.fea = fea;
	}

	public Integer getTemplatedbId() {
		return templatedbId;
	}

	public void setTemplatedbId(Integer templatedbId) {
		this.templatedbId = templatedbId;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}