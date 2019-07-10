package com.sensing.core.thrift.cmp.bean;

import java.io.Serializable;

public class CmpExtBean implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String   uuid;//主键id
	private String fcapId;//抓拍ID 
	private String channelId;//抓拍通道ID
	private long fcapTime;//抓拍时间
	private Long cmpTime;//比对时间
	private String cmpId;//比对服务id
	private String sort;//比对服务排序
	private double score;//比对分值
	private String faceObjectId;//人脸对象ID
	private int objectType;//人脸对象类型
	private String channelName;//通道名称
	private String regionId;//区域编号
	private int DBID;                    // 模板库ID
	private String DBIDName;        //  模板库名称
	private double threshold;      //阈值
	private String id_numb;      //证件编号
	private int idType;			//证件类型
	
	private String mainFtID;	// 人脸首选模板uuid
	private String topicName;  //推送消息主题的名称
	private String fcmpFobjName;//(faceObj.getName());// 姓名
	private double channelLongitude;// 视频源经度
	private double channelLatitude;// 视频源纬度
	private int channelDirect;// 视频源方向
	private int matchedCount;	//命中次数
	private String rulerName;		//策略名称
	private String channelArea;		//通道位置描述
	private int fcmpFobjSex;            //目标人的性别
	private double channelThreshold; //通道系数
	private int fcapFace_x;// 抓拍人脸x坐标
	private int fcapFace_y;// 抓拍人脸x坐标
	private int fcapFace_cx;// 抓拍人脸宽度
	private int fcapFace_cy;// 抓拍人脸高度
	
	
	private String personId;//用于区分是否是同一个人
	
	private int fcapQuality;//抓拍图像质量
	private int fcapType;//抓拍类型
	private int fcapAge;//抓拍识别到的年龄
	private int fcmpType;//比对类型
	private long ftdbId;//人像模板库id
	

	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFcapId() {
		return fcapId;
	}
	public void setFcapId(String fcapId) {
		this.fcapId = fcapId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public long getFcapTime() {
		return fcapTime;
	}
	public void setFcapTime(long fcapTime) {
		this.fcapTime = fcapTime;
	}
	public Long getCmpTime() {
		return cmpTime;
	}
	public void setCmpTime(Long cmpTime) {
		this.cmpTime = cmpTime;
	}
	public String getCmpId() {
		return cmpId;
	}
	public void setCmpId(String cmpId) {
		this.cmpId = cmpId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getFaceObjectId() {
		return faceObjectId;
	}
	public void setFaceObjectId(String faceObjectId) {
		this.faceObjectId = faceObjectId;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public int getDBID() {
		return DBID;
	}
	public void setDBID(int dBID) {
		DBID = dBID;
	}
	public String getDBIDName() {
		return DBIDName;
	}
	public void setDBIDName(String dBIDName) {
		DBIDName = dBIDName;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public String getId_numb() {
		return id_numb;
	}
	public void setId_numb(String id_numb) {
		this.id_numb = id_numb;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getFcmpFobjName() {
		return fcmpFobjName;
	}
	public void setFcmpFobjName(String fcmpFobjName) {
		this.fcmpFobjName = fcmpFobjName;
	}
	public double getChannelLongitude() {
		return channelLongitude;
	}
	public void setChannelLongitude(double channelLongitude) {
		this.channelLongitude = channelLongitude;
	}
	public double getChannelLatitude() {
		return channelLatitude;
	}
	public void setChannelLatitude(double channelLatitude) {
		this.channelLatitude = channelLatitude;
	}
	public int getChannelDirect() {
		return channelDirect;
	}
	public void setChannelDirect(int channelDirect) {
		this.channelDirect = channelDirect;
	}
	public String getMainFtID() {
		return mainFtID;
	}
	public void setMainFtID(String mainFtID) {
		this.mainFtID = mainFtID;
	}
	public int getMatchedCount() {
		return matchedCount;
	}
	public void setMatchedCount(int matchedCount) {
		this.matchedCount = matchedCount;
	}
	public String getRulerName() {
		return rulerName;
	}
	public void setRulerName(String rulerName) {
		this.rulerName = rulerName;
	}
	public String getChannelArea() {
		return channelArea;
	}
	public void setChannelArea(String channelArea) {
		this.channelArea = channelArea;
	}
	public int getFcmpFobjSex() {
		return fcmpFobjSex;
	}
	public void setFcmpFobjSex(int fcmpFobjSex) {
		this.fcmpFobjSex = fcmpFobjSex;
	}
	public double getChannelThreshold() {
		return channelThreshold;
	}
	public void setChannelThreshold(double channelThreshold) {
		this.channelThreshold = channelThreshold;
	}
	public int getFcapFace_x() {
		return fcapFace_x;
	}
	public void setFcapFace_x(int fcapFace_x) {
		this.fcapFace_x = fcapFace_x;
	}
	public int getFcapFace_y() {
		return fcapFace_y;
	}
	public void setFcapFace_y(int fcapFace_y) {
		this.fcapFace_y = fcapFace_y;
	}
	public int getFcapFace_cx() {
		return fcapFace_cx;
	}
	public void setFcapFace_cx(int fcapFace_cx) {
		this.fcapFace_cx = fcapFace_cx;
	}
	public int getFcapFace_cy() {
		return fcapFace_cy;
	}
	public void setFcapFace_cy(int fcapFace_cy) {
		this.fcapFace_cy = fcapFace_cy;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public int getFcapQuality() {
		return fcapQuality;
	}
	public void setFcapQuality(int fcapQuality) {
		this.fcapQuality = fcapQuality;
	}
	public int getFcapType() {
		return fcapType;
	}
	public void setFcapType(int fcapType) {
		this.fcapType = fcapType;
	}
	public int getFcapAge() {
		return fcapAge;
	}
	public void setFcapAge(int fcapAge) {
		this.fcapAge = fcapAge;
	}
	public int getFcmpType() {
		return fcmpType;
	}
	public void setFcmpType(int fcmpType) {
		this.fcmpType = fcmpType;
	}
	public long getFtdbId() {
		return ftdbId;
	}
	public void setFtdbId(long ftdbId) {
		this.ftdbId = ftdbId;
	}
	@Override
	public String toString() {
		return "CmpExtBean [fcapId=" + fcapId + ", channelId=" + channelId + ", fcapTime=" + fcapTime + ", cmpTime="
				+ cmpTime + ", cmpId=" + cmpId + ", sort=" + sort + ", score=" + score + ", faceObjectId="
				+ faceObjectId + ", objectType=" + objectType + ", channelName=" + channelName + ", DBID=" + DBID
				+ ", DBIDName=" + DBIDName + ", threshold=" + threshold + ", id_numb=" + id_numb + ", idType=" + idType
				+ ", mainFtID=" + mainFtID + ", topicName=" + topicName + ", fcmpFobjName=" + fcmpFobjName
				+ ", channelLongitude=" + channelLongitude + ", channelLatitude=" + channelLatitude + ", channelDirect="
				+ channelDirect + ", matchedCount=" + matchedCount + ", rulerName=" + rulerName + ", channelArea="
				+ channelArea + ", fcmpFobjSex=" + fcmpFobjSex + ", channelThreshold=" + channelThreshold
				+ ", fcapFace_x=" + fcapFace_x + ", fcapFace_y=" + fcapFace_y + ", fcapFace_cx=" + fcapFace_cx
				+ ", fcapFace_cy=" + fcapFace_cy + "]";
	}
	
	
	
}
