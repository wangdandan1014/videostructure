package com.sensing.core.bean;

import java.io.Serializable;
import java.util.List;

import com.sensing.core.utils.Constants;
import com.sensing.core.utils.ExcelVOAttribute;

/**
 * @author wenbo
 */
@SuppressWarnings("all")
public class Channel1 extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uuid;
	@ExcelVOAttribute(name = "通道名称", column = "A", isExport = true)
	private String channelName;
	@ExcelVOAttribute(name = "通道描述", column = "M", isExport = true)
	private String channelDescription;
	@ExcelVOAttribute(name = "视频编码格式", column = "E", isExport = true)
	private String channelTypeTag;
	private Integer channelType;
	@ExcelVOAttribute(name = "视频地址", column = "C", isExport = true)
	private String channelAddr;
	private String channelRtmp;// ------------视频rtmp流地址
	@ExcelVOAttribute(name = "视频端口", column = "D", isExport = true)
	private Integer channelPort;// 通道端口
	@ExcelVOAttribute(name = "账号", column = "F", isExport = true)
	private String channelUid;// 账号
	@ExcelVOAttribute(name = "视频密码", column = "G", isExport = true)
	private String channelPsw;// 视频密码
	@ExcelVOAttribute(name = "国际编码", column = "B", isExport = true)
	private String channelNo;// 通道号
	private String channelGuid; // 通道序列号
	private Integer minFaceWidth;
	private Integer minImgQuality;
	private Integer minCapDistance;
	private Integer maxSaveDistance;
	private Integer maxYaw;
	private Integer maxRoll;
	private Integer maxPitch;
	@ExcelVOAttribute(name = "通道经度", column = "J", isExport = true)
	private Double channelLongitude;
	@ExcelVOAttribute(name = "通道纬度", column = "K", isExport = true)
	private Double channelLatitude;
	@ExcelVOAttribute(name = "通道位置", column = "L", isExport = true)
	private String channelArea;
	private Integer channelDirect;
	@ExcelVOAttribute(name = "通道方向", column = "I", isExport = true)
	private String channelDirectTag;
	private Double channelThreshold;
	private Integer capStat;
	private Short isDel;
	private java.util.Date lastTimestamp;
	private String lastTimestampStr;
	private Integer regionId;
	private String regionName;
	private Integer reserved;
	private String sdkVer;
	private Short important;
	private String channelVerid;
	private String searchCode;
	private String channelState;// 通道状态

	private String nodeType;// 区域或通道
	private String parentId;

	private String orgName;

	private List<Channel1> childChannels;

	private String uid;// 创建用户的uuid
	private Integer type;// 通道类型，视频平台0；rtsp1
	private Integer sipNetType;// 通讯类型 0-tcp 1-udp
	private String sipNetTypeTag;// 通讯类型 0-tcp 1-udp

	private Long createTime;// 创建时间
	private Long modifyTime;// 修改时间
	private String createUserName;
	@ExcelVOAttribute(name = "视频预览地址", column = "H", isExport = true)
	private String previewAddr; // 预览地址
	private String analysisType;

	public String getChannelTypeTag() {
		return channelTypeTag;
	}

	public void setChannelTypeTag(String channelTypeTag) {
		this.channelTypeTag = channelTypeTag;
	}

	public String getSipNetTypeTag() {
		return sipNetTypeTag;
	}

	public void setSipNetTypeTag(String sipNetTypeTag) {
		this.sipNetTypeTag = sipNetTypeTag;
	}

	public String getChannelDirectTag() {
		return channelDirectTag;
	}

	public void setChannelDirectTag(String channelDirectTag) {
		this.channelDirectTag = channelDirectTag;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public String getPreviewAddr() {
		return previewAddr == null ? "" : previewAddr.replaceAll(" ", "");
	}

	public void setPreviewAddr(String previewAddr) {
		this.previewAddr = previewAddr;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getChannelRtmp() {
		return channelRtmp == null ? "" : channelRtmp.replaceAll(" ", "");
	}

	public void setChannelRtmp(String channelRtmp) {
		this.channelRtmp = channelRtmp;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getSipNetType() {
		return sipNetType;
	}

	public void setSipNetType(Integer sipNetType) {
		this.sipNetType = sipNetType;
	}

	public Integer getType() {
//		if (null != regionName && "手机通道".equals(regionName)) {
//			return Constants.CHANNEL_TYPE_RTSP;
//		} else {
//			return Constants.CHANNEL_TYPE_VIDEO;
//		}
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private String capStatTag; // 抓拍状态显示

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getChannelAddr() {
		return channelAddr == null ? "" : channelAddr.replaceAll(" ", "");
	}

	public void setChannelAddr(String channelAddr) {
		this.channelAddr = channelAddr;
	}

	public Integer getChannelPort() {
		if (channelPort != null) {
			return Integer.parseInt(channelPort.toString().replaceAll(" ", ""));
		} else {
			return channelPort;
		}
	}

	public void setChannelPort(Integer channelPort) {
		this.channelPort = channelPort;
	}

	public String getChannelUid() {
		return channelUid;
	}

	public void setChannelUid(String channelUid) {
		this.channelUid = channelUid;
	}

	public String getChannelPsw() {
		return channelPsw;
	}

	public void setChannelPsw(String channelPsw) {
		this.channelPsw = channelPsw;
	}

	public String getChannelNo() {
		return channelNo == null ? "" : channelNo.replaceAll(" ", "");
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelGuid() {
		return channelGuid;
	}

	public void setChannelGuid(String channelGuid) {
		this.channelGuid = channelGuid;
	}

	public Integer getMinFaceWidth() {
		return minFaceWidth;
	}

	public void setMinFaceWidth(Integer minFaceWidth) {
		this.minFaceWidth = minFaceWidth;
	}

	public Integer getMinImgQuality() {
		return minImgQuality;
	}

	public void setMinImgQuality(Integer minImgQuality) {
		this.minImgQuality = minImgQuality;
	}

	public Integer getMinCapDistance() {
		return minCapDistance;
	}

	public void setMinCapDistance(Integer minCapDistance) {
		this.minCapDistance = minCapDistance;
	}

	public Integer getMaxSaveDistance() {
		return maxSaveDistance;
	}

	public void setMaxSaveDistance(Integer maxSaveDistance) {
		this.maxSaveDistance = maxSaveDistance;
	}

	public Integer getMaxYaw() {
		return maxYaw;
	}

	public void setMaxYaw(Integer maxYaw) {
		this.maxYaw = maxYaw;
	}

	public Integer getMaxRoll() {
		return maxRoll;
	}

	public void setMaxRoll(Integer maxRoll) {
		this.maxRoll = maxRoll;
	}

	public Integer getMaxPitch() {
		return maxPitch;
	}

	public void setMaxPitch(Integer maxPitch) {
		this.maxPitch = maxPitch;
	}

	public Double getChannelLongitude() {
		return channelLongitude;
	}

	public void setChannelLongitude(Double channelLongitude) {
		this.channelLongitude = channelLongitude;
	}

	public Double getChannelLatitude() {
		return channelLatitude;
	}

	public void setChannelLatitude(Double channelLatitude) {
		this.channelLatitude = channelLatitude;
	}

	public String getChannelArea() {
		return channelArea;
	}

	public void setChannelArea(String channelArea) {
		this.channelArea = channelArea;
	}

	public Integer getChannelDirect() {
		return channelDirect;
	}

	public void setChannelDirect(Integer channelDirect) {
		this.channelDirect = channelDirect;
	}

	public Double getChannelThreshold() {
		return channelThreshold;
	}

	public void setChannelThreshold(Double channelThreshold) {
		this.channelThreshold = channelThreshold;
	}

	public Integer getCapStat() {
		return capStat == null ? 0 : capStat;
	}

	public void setCapStat(Integer capStat) {
		this.capStat = capStat;
	}

	public Short getIsDel() {
		return isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	public java.util.Date getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(java.util.Date lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getLastTimestampStr() {
		if (lastTimestamp != null) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(lastTimestamp);
		} else {
			return "";
		}
	}

	public void setLastTimestampStr(String lastTimestampStr) throws Exception {
		if (lastTimestampStr != null && !lastTimestampStr.trim().equals("")) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			this.lastTimestamp = sdf.parse(lastTimestampStr);
		} else
			this.lastTimestamp = null;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getReserved() {
		return reserved;
	}

	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}

	public String getSdkVer() {
		return sdkVer;
	}

	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}

	public Short getImportant() {
		return important;
	}

	public void setImportant(Short important) {
		this.important = important;
	}

	public String getChannelVerid() {
		return channelVerid;
	}

	public void setChannelVerid(String channelVerid) {
		this.channelVerid = channelVerid;
	}

	public String getCapStatTag() {
		return Constants.CAP_STAT_MAP.get(capStat);
	}

	public void setCapStatTag(String capStatTag) {
		this.capStatTag = capStatTag;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public List<Channel1> getChildChannels() {
		return childChannels;
	}

	public void setChildChannels(List<Channel1> childChannels) {
		this.childChannels = childChannels;
	}

	public String getChannelState() {
		return channelState;
	}

	public void setChannelState(String channelState) {
		this.channelState = channelState;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "Channel [uuid=" + uuid + ", channelName=" + channelName + ", channelDescription=" + channelDescription
				+ ", channelType=" + channelType + ", channelAddr=" + channelAddr + ", channelPort=" + channelPort
				+ ", channelUid=" + channelUid + ", channelPsw=" + channelPsw + ", channelNo=" + channelNo
				+ ", channelGuid=" + channelGuid + ", minFaceWidth=" + minFaceWidth + ", minImgQuality=" + minImgQuality
				+ ", minCapDistance=" + minCapDistance + ", maxSaveDistance=" + maxSaveDistance + ", maxYaw=" + maxYaw
				+ ", maxRoll=" + maxRoll + ", maxPitch=" + maxPitch + ", channelLongitude=" + channelLongitude
				+ ", channelLatitude=" + channelLatitude + ", channelArea=" + channelArea + ", channelDirect="
				+ channelDirect + ", channelThreshold=" + channelThreshold + ", capStat=" + capStat + ", isDel=" + isDel
				+ ", lastTimestamp=" + lastTimestamp + ", lastTimestampStr=" + lastTimestampStr + ", regionId="
				+ regionId + ", regionName=" + regionName + ", reserved=" + reserved + ", sdkVer=" + sdkVer
				+ ", important=" + important + ", channelVerid=" + channelVerid + ", searchCode=" + searchCode
				+ ", channelState=" + channelState + ", nodeType=" + nodeType + ", parentId=" + parentId
				+ ", childChannels=" + childChannels + ", capStatTag=" + capStatTag + "]";
	}

}