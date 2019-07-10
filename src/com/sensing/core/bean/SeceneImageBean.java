package com.sensing.core.bean;

import java.io.Serializable;

/**
 * 
 * <p>Title: SeceneImageBean</p>
 * <p>Description: 场景图辅助类 </p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	haowenfeng
 * @date	2017年8月9日下午4:46:22
 * @version 2.0
 */
public class SeceneImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7924075276869339076L;
	
	private String alertUuid;
	private String channelName;
	private String channelUuid;
	private String channelArea;//通道地址
	private Double channelThreshold;
	private int faceX;    //人脸X坐标
	private int faceY;	//人脸Y坐标
	private int faceCX;		//人脸宽度
	private int faceCY;    //人脸高度
	private String seceneUuid;//场景图ID
	private String seceneUrl;  //场景图URL
	
	private String fcmpCapId;//抓拍uuid
	private Long fcapTime;		//抓拍时间
	private String fcapTimeTag;//抓拍时间翻译
	private int channelDirect;  //视频源方向
	private String channelDirectTag;//视频源方向标签
	private String regionName;  //区域名称
	private int regionId;//通道所属区域id
	private String fcapImgUuid;//抓拍图片uuid
	private String fcapImgUrl;//抓拍图片URL
	private Double fcmpScore;//比对分数 
	private int uncheckedCount;
	
	public String getChannelUuid() {
		return channelUuid;
	}
	public void setChannelUuid(String channelUuid) {
		this.channelUuid = channelUuid;
	}
	public String getAlertUuid() {
		return alertUuid;
	}
	public void setAlertUuid(String alertUuid) {
		this.alertUuid = alertUuid;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public Double getChannelThreshold() {
		return channelThreshold;
	}
	public void setChannelThreshold(Double channelThreshold) {
		this.channelThreshold = channelThreshold;
	}
	public int getFaceX() {
		return faceX;
	}
	public void setFaceX(int faceX) {
		this.faceX = faceX;
	}
	public int getFaceY() {
		return faceY;
	}
	public void setFaceY(int faceY) {
		this.faceY = faceY;
	}
	public int getFaceCX() {
		return faceCX;
	}
	public void setFaceCX(int faceCX) {
		this.faceCX = faceCX;
	}
	public int getFaceCY() {
		return faceCY;
	}
	public void setFaceCY(int faceCY) {
		this.faceCY = faceCY;
	}
	public String getSeceneUuid() {
		return seceneUuid;
	}
	public void setSeceneUuid(String seceneUuid) {
		this.seceneUuid = seceneUuid;
	}
	public String getSeceneUrl() {
		return seceneUrl;
	}
	public void setSeceneUrl(String seceneUrl) {
		this.seceneUrl = seceneUrl;
	}
	public long getFcapTime() {
		return fcapTime;
	}
	public void setFcapTime(long fcapTime) {
		this.fcapTime = fcapTime;
	}
	public int getChannelDirect() {
		return channelDirect;
	}
	public void setChannelDirect(int channelDirect) {
		this.channelDirect = channelDirect;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getUncheckedCount() {
		return uncheckedCount;
	}
	public void setUncheckedCount(int uncheckedCount) {
		this.uncheckedCount = uncheckedCount;
	}
	public String getFcapImgUuid() {
		return fcapImgUuid;
	}
	public Double getFcmpScore() {
		return fcmpScore;
	}
	public void setFcapImgUuid(String fcapImgUuid) {
		this.fcapImgUuid = fcapImgUuid;
	}
	public void setFcmpScore(Double fcmpScore) {
		this.fcmpScore = fcmpScore;
	}
	public String getFcmpCapId() {
		return fcmpCapId;
	}
	public void setFcmpCapId(String fcmpCapId) {
		this.fcmpCapId = fcmpCapId;
	}
	public String getFcapImgUrl() {
		return fcapImgUrl;
	}
	public void setFcapImgUrl(String fcapImgUrl) {
		this.fcapImgUrl = fcapImgUrl;
	}
	public String getFcapTimeTag() {
		return fcapTimeTag;
	}
	public void setFcapTimeTag(String fcapTimeTag) {
		this.fcapTimeTag = fcapTimeTag;
	}
	public String getChannelArea() {
		return channelArea;
	}
	public void setChannelArea(String channelArea) {
		this.channelArea = channelArea;
	}
	public String getChannelDirectTag() {
		return channelDirectTag;
	}
	public void setChannelDirectTag(String channelDirectTag) {
		this.channelDirectTag = channelDirectTag;
	}
	@Override
	public String toString() {
		return "SeceneImageBean [alertUuid=" + alertUuid + ", channelName="
				+ channelName + ", channelUuid=" + channelUuid
				+ ", channelArea=" + channelArea + ", channelThreshold="
				+ channelThreshold + ", faceX=" + faceX + ", faceY=" + faceY
				+ ", faceCX=" + faceCX + ", faceCY=" + faceCY + ", seceneUuid="
				+ seceneUuid + ", seceneUrl=" + seceneUrl + ", fcmpCapId="
				+ fcmpCapId + ", fcapTime=" + fcapTime + ", fcapTimeTag="
				+ fcapTimeTag + ", channelDirect=" + channelDirect
				+ ", channelDirectTag=" + channelDirectTag + ", regionName="
				+ regionName + ", regionId=" + regionId + ", fcapImgUuid="
				+ fcapImgUuid + ", fcapImgUrl=" + fcapImgUrl + ", fcmpScore="
				+ fcmpScore + ", uncheckedCount=" + uncheckedCount + "]";
	}

	
}
