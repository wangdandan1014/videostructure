package com.sensing.core.alarm;

import java.util.Date;

/**
 * 比对结果封装对象
 * <p>Title: CmpResultBean</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月22日
 * @version 1.0
 */
public class CmpResultBean {

	private String uuid;//比对的uuid
	private String objPlateNo;//命中的目标的车牌号码
	private String capPlateNo;//抓拍的车牌号码
	private String templateUuid;//命中的模板的uuid
	private Integer templateDbId;//命中的模板所属的模板库的id
	private String mainTemplateUrl;//命中的目标的主模板的图片的url
	private String templateObjUuid;//命中的目标人的uuid
	private Double score;//比对的分值
	private Date cmpDate;//比对时间
	
	private String capUuid;//抓拍uuid
	private String deviceUuid;//通道的uuid
	private String identityId;//抓拍的唯一标识
	private Long capTime;//抓拍时间
	private String capUrl;//抓拍图路径
	private String sceneUrl;//场景图路径
	
	//抓拍属性
	private Integer vehicleColor;//机动车颜色
	private Integer orientation;//朝向
	private Integer plateColor;//车牌颜色
	private Integer plateClass;//车牌类型
	private Integer vehicleClass;//机动车类型
	private String vehicleBrandTag;//车辆品牌
	private String vehicleModelTag;//车辆型号
	private String vehicleStylesTag;//车辆年款
	private Integer vehicleMarkerMot;//年检标
	private Integer vehicleMarkerTissuebox;//纸巾盒
	private Integer vehicleMarkerPendant;//挂饰
	private Integer sunvisor;//遮阳板
	private Integer safetyBelt;//主驾驶带安全带
	private Integer safetyBeltCopilot;//副驾驶带安全带
	private Integer calling;//主驾驶打电话
	private String capLocation;//抓拍图的位置
	
	private Long receiveTime;//接收到抓拍的时间
	private byte[] fea;//特征文件
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getObjPlateNo() {
		return objPlateNo;
	}
	public void setObjPlateNo(String objPlateNo) {
		this.objPlateNo = objPlateNo;
	}
	public String getCapPlateNo() {
		return capPlateNo;
	}
	public void setCapPlateNo(String capPlateNo) {
		this.capPlateNo = capPlateNo;
	}
	public String getTemplateUuid() {
		return templateUuid;
	}
	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}
	public Integer getTemplateDbId() {
		return templateDbId;
	}
	public void setTemplateDbId(Integer templateDbId) {
		this.templateDbId = templateDbId;
	}
	public String getTemplateObjUuid() {
		return templateObjUuid;
	}
	public void setTemplateObjUuid(String templateObjUuid) {
		this.templateObjUuid = templateObjUuid;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Date getCmpDate() {
		return cmpDate;
	}
	public void setCmpDate(Date cmpDate) {
		this.cmpDate = cmpDate;
	}
	public String getCapUuid() {
		return capUuid;
	}
	public void setCapUuid(String capUuid) {
		this.capUuid = capUuid;
	}
	public String getDeviceUuid() {
		return deviceUuid;
	}
	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public Long getCapTime() {
		return capTime;
	}
	public void setCapTime(Long capTime) {
		this.capTime = capTime;
	}
	public String getCapUrl() {
		return capUrl;
	}
	public void setCapUrl(String capUrl) {
		this.capUrl = capUrl;
	}
	public String getSceneUrl() {
		return sceneUrl;
	}
	public void setSceneUrl(String sceneUrl) {
		this.sceneUrl = sceneUrl;
	}
	public String getMainTemplateUrl() {
		return mainTemplateUrl;
	}
	public void setMainTemplateUrl(String mainTemplateUrl) {
		this.mainTemplateUrl = mainTemplateUrl;
	}
	public Integer getVehicleColor() {
		return vehicleColor;
	}
	public void setVehicleColor(Integer vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
	public Integer getOrientation() {
		return orientation;
	}
	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	public Integer getPlateColor() {
		return plateColor;
	}
	public void setPlateColor(Integer plateColor) {
		this.plateColor = plateColor;
	}
	public Integer getPlateClass() {
		return plateClass;
	}
	public void setPlateClass(Integer plateClass) {
		this.plateClass = plateClass;
	}
	public Integer getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(Integer vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getVehicleBrandTag() {
		return vehicleBrandTag;
	}
	public void setVehicleBrandTag(String vehicleBrandTag) {
		this.vehicleBrandTag = vehicleBrandTag;
	}
	public String getVehicleModelTag() {
		return vehicleModelTag;
	}
	public void setVehicleModelTag(String vehicleModelTag) {
		this.vehicleModelTag = vehicleModelTag;
	}
	public String getVehicleStylesTag() {
		return vehicleStylesTag;
	}
	public void setVehicleStylesTag(String vehicleStylesTag) {
		this.vehicleStylesTag = vehicleStylesTag;
	}
	public Integer getVehicleMarkerMot() {
		return vehicleMarkerMot;
	}
	public void setVehicleMarkerMot(Integer vehicleMarkerMot) {
		this.vehicleMarkerMot = vehicleMarkerMot;
	}
	public Integer getVehicleMarkerTissuebox() {
		return vehicleMarkerTissuebox;
	}
	public void setVehicleMarkerTissuebox(Integer vehicleMarkerTissuebox) {
		this.vehicleMarkerTissuebox = vehicleMarkerTissuebox;
	}
	public Integer getVehicleMarkerPendant() {
		return vehicleMarkerPendant;
	}
	public void setVehicleMarkerPendant(Integer vehicleMarkerPendant) {
		this.vehicleMarkerPendant = vehicleMarkerPendant;
	}
	public Integer getSunvisor() {
		return sunvisor;
	}
	public void setSunvisor(Integer sunvisor) {
		this.sunvisor = sunvisor;
	}
	public Integer getSafetyBelt() {
		return safetyBelt;
	}
	public void setSafetyBelt(Integer safetyBelt) {
		this.safetyBelt = safetyBelt;
	}
	public Integer getSafetyBeltCopilot() {
		return safetyBeltCopilot;
	}
	public void setSafetyBeltCopilot(Integer safetyBeltCopilot) {
		this.safetyBeltCopilot = safetyBeltCopilot;
	}
	public Integer getCalling() {
		return calling;
	}
	public void setCalling(Integer calling) {
		this.calling = calling;
	}
	public String getCapLocation() {
		return capLocation;
	}
	public void setCapLocation(String capLocation) {
		this.capLocation = capLocation;
	}
	public Long getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}
	public byte[] getFea() {
		return fea;
	}
	public void setFea(byte[] fea) {
		this.fea = fea;
	}
}
