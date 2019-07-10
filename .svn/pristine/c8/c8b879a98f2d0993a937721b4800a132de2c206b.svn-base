package com.sensing.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UuidUtil {
	public static String getUuid() {
		return UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
	}

	/**
	 * 获取精确到毫秒的时间+5位随机数字
	 * 
	 * @return
	 */
	public static String getTime() {
		int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String res = simpleDateFormat.format(new Date());
		return res + rannum;
	}
}
