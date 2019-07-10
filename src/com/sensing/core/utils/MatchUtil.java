package com.sensing.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {

	/**
	 * 判断是否是十位数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTenPositive(String str) {
		// 匹配模式,\\d代表匹配数字,{10}代表正好匹配次数
		String regex = "\\d{10}";
		boolean b = Pattern.matches(regex, str);
		return b;
	}

	/**
	 * 校验车牌号码是否正确
	 * 
	 * @param carNo
	 * @return
	 */
	public static boolean isCarNo(String carNo) {
		Pattern p = Pattern.compile(
				"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(?:(?![A-Z]{4})[A-Z0-9]){4}[A-Z0-9挂学警港澳]{1}$");
//		String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
		Matcher m = p.matcher(carNo);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
//		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p1 = Pattern.compile("0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}"); // 验证区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的

		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
}
