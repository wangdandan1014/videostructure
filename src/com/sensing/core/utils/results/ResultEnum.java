package com.sensing.core.utils.results;

/**
 * 枚举定义异常类型以及相对于的错误信息 有助于返回码的唯一性
 */
public enum ResultEnum {
	OK(0, "成功"), FAIL(-1, "失败"), UNKONW_ERROR(100, "未知业务错误"), REQUIRED_EMPTY_ERROR(101, "必填项为空"),
	UNIQUE_VIOLATION(102, "违反唯一约束"), DATA_ERROR(103, "数据格式错误"), BEYOND_CHAR_LIMIT(104, "超出字符限制"),
	OtherError(1, "其他未知错误"), DeviceBusy(2, "设备忙"), DeviceError(3, "设备错"), InvalidOperation(4, "无效操作"),
	InvalidXMLFormat(5, "XML格式无效"), InvalidXMLContent(6, "XML内容无效"), InvalidJSONFormat(7, "JSON 格式无效"),
	InvalidJSONContent(8, "JSON 内容无效"), Reboot(9, "系统重启中"), NONE(10, "未找到数据"), EMPTY(11, "暂无结果"), InvalidSession(-200, "session无效，请重新登录"),
	InvalidUser(-300, "您无权访问该网站，请与管理员联系"), LogintokenErr(-400, "登录信息验证失败，请重新登录！");
	private Integer code;
	private String msg;

	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}