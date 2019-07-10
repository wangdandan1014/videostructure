package com.sensing.core.alarm;

/**
 * 通道对象
 * <p>Title: ChannelBean</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年12月3日
 * @version 1.0
 */
public class DeviceBean {
	private String uuid;
	private String deviceName;
	private String deviceArea;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceArea() {
		return deviceArea;
	}
	public void setDeviceArea(String deviceArea) {
		this.deviceArea = deviceArea;
	}

	@Override
	public String toString() {
		return "ChannelBean [uuid=" + uuid + ", deviceName=" + deviceName
				+ ", deviceArea=" + deviceArea + "]";
	}
}
