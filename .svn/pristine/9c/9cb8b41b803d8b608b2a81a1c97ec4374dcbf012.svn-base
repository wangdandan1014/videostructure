/**
 * <p>Title: StringUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: right (c) 2017</p>
 * <p>Company: www.sensingtech.com</p>
 *
 * @author mingxingyu
 * @date 2017年8月3日下午5:03:13
 * @version 1.0
 */
package com.sensing.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: StringUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 *
 * @author mingxingyu
 * @version 1.0
 * @date 2017年8月3日下午5:03:13
 */
public class StringUtils {

	private static Log log = LogFactory.getLog(StringUtils.class);

	/**
	 * base64的字符串转换为byte[],用于图片的byte数组和base64互转
	 *
	 * @param base64Str
	 * @return
	 * @author mingxingyu
	 * @date 2018年9月11日 上午10:42:07
	 */
	public static byte[] base64String2ByteFun(String base64Str) {
		return Base64.decodeBase64(base64Str);
	}

	/**
	 * byte[]转换为string,用于图片的byte数组和base64互转
	 *
	 * @param b
	 * @return
	 * @author mingxingyu
	 * @date 2018年9月11日 上午10:42:37
	 */
	public static String byte2Base64StringFun(byte[] b) {
		return Base64.encodeBase64String(b);
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @author mingxingyu
	 * @date 2017年8月3日下午5:11:22
	 */
	public static boolean isNotEmpty(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		return true;
	}

	public static boolean isEmptyOrNull(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉字符串末尾的最后一个字符
	 *
	 * @param str
	 * @return
	 * @author mingxingyu
	 * @date 2017年8月24日下午4:23:05
	 */
	public static String delLastChar(String str) {
		if (!isNotEmpty(str)) {
			return "";
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * 将带有","的字符串通过截取生成List author dongsl date 2017年8月9日上午9:48:12
	 */
	public static List<String> stringToList(String str) {
		if (str == null || str.equals("")) {
			return new ArrayList<String>();
		} else {
			String strArr[] = new String[1024];
			List<String> resList = new ArrayList<String>();
			strArr = str.split(",");
			Collections.addAll(resList, strArr);
			return resList;
		}
	}

	public static String listToString(List<String> mList) {
		String convertedListStr = "";
		if (null != mList && mList.size() > 0) {
			String[] mListArray = mList.toArray(new String[mList.size()]);
			for (int i = 0; i < mListArray.length; i++) {
				if (i < mListArray.length - 1) {
					convertedListStr += mListArray[i] + ",";
				} else {
					convertedListStr += mListArray[i];
				}
			}
			return convertedListStr;
		} else
			return "";
	}

	/**
	 * 将List<String>集合 转化为String 如{"aaa","bbb"} To 'aaa','bbb'
	 */
	public static String convertListToString(List<String> strlist) {
		StringBuffer sb = new StringBuffer();
		if (!CollectionUtils.isEmpty(strlist)) {
			for (int i = 0; i < strlist.size(); i++) {
				if (i == 0) {
					sb.append("'").append(strlist.get(i)).append("'");
				} else {
					sb.append(",").append("'").append(strlist.get(i)).append("'");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 根据身份证号获取该主人的年龄
	 *
	 * @param idCard
	 * @return 解析错误返回空值
	 * @author mingxingyu
	 * @date 2018年7月20日 上午9:49:51
	 */
	public static Integer getAgeByIdCard(String idCard) {
		// 身份证号码不能为空，超度不能低于10位
		if (idCard == null || idCard.equals("") || idCard.length() < 10) {
			log.error("getAgeByIdCard根据身份证号获取该主人的年龄，身份证号码解析错误！idCard：" + idCard);
			return null;
		}
		Integer year = null;
		// 截取出生年份，转换异常返回为空
		try {
			year = Integer.parseInt(idCard.substring(6, 10));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		int iCurrYear = cal.get(Calendar.YEAR);

		// 校验获取的出生日期
		if (year == null || year < 1920 || year > iCurrYear) {
			return null;
		}
		// 和当前年份计算
		int iAge = 0;
		iAge = iCurrYear - year;
		return iAge;
	}

	public static String getStackTrace(Throwable ex) {
		ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
		try {
			ex.printStackTrace(new java.io.PrintWriter(buf, true));
			return buf.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buf.close();
			} catch (Exception a) {
			}
		}
	}

	public static String getStackTraceSub(Throwable ex) {
		ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
		try {
			ex.printStackTrace(new java.io.PrintWriter(buf, true));
			String s = buf.toString();
			if (s.length() >= 1000) {
				return s.substring(0, 1000);
			} else {
				return s;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buf.close();
			} catch (Exception a) {
			}
		}
	}

	public static String getStringSub(String str) {
		if (str.length() >= 1000) {
			return str.substring(0, 1000);
		} else {
			return str;
		}
	}

	public static String getRequestParameter(HttpServletRequest request, HttpServletResponse response) {

		if (null == request) {
			return null;
		}

		String method = request.getMethod();
		String param = null;
		if (method.equalsIgnoreCase("GET")) {
			/**
			 * 获取?后面的字符串 http://127.0.0.1:8080/test?username=zhangsan&age=100
			 * -->username=zhangsan&age=100
			 * http://127.0.0.1:8080/test?{"username":"zhangsan"}
			 * -->{"username":"zhangsan"}是json字符串 有了json串就可以映射成对象了
			 */
			param = request.getQueryString();
			if (Base64.isBase64(param)) {
				param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
			}
		} else {
			param = request.getRemoteUser();
			if (Base64.isBase64(param)) {
				param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
			}
		}
		return param;
	}

	// 获取请求体中的字符串(POST)
	public static String getBodyData(HttpServletRequest request) {
		StringBuffer data = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
		} finally {
		}
		return data.toString();
	}

	/**
	 * 分割运行周期的工具类
	 *
	 * @param runWeek  "1,2,3,4"
	 * @param splitStr 返回的字符串的分隔符
	 * @return
	 */
	public static String getWeekString(String runWeek, String splitStr) {
		if (StringUtils.isEmptyOrNull(runWeek)) {
			return "";
		}
		// 区分 每天、工作日、周末的显示
		if ("1,2,3,4,5,6,7".equals(runWeek.trim())) {
			return "每天";
		}
		if ("1,2,3,4,5".equals(runWeek.trim())) {
			return "工作日";
		}
		if ("6,7".equals(runWeek.trim())) {
			return "周末";
		}

		StringBuilder b = new StringBuilder();
		String[] split = runWeek.split(",");
		for (int i = 0; i < split.length; i++) {
			int week = Integer.parseInt(split[i]);
			switch (week) {
			case 1:
				b.append("周一").append(splitStr);
				break;
			case 2:
				b.append("周二").append(splitStr);
				break;
			case 3:
				b.append("周三").append(splitStr);
				break;
			case 4:
				b.append("周四").append(splitStr);
				break;
			case 5:
				b.append("周五").append(splitStr);
				break;
			case 6:
				b.append("周六").append(splitStr);
				break;
			case 7:
				b.append("周日").append(splitStr);
				break;
			}
		}
		return b.subSequence(0, b.length() - 1).toString();
	}

	/**
	 * 利用正则表达式判断字符串是否是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

		boolean flag = false;
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			log.info(f.getName());
			if (f.get(obj) == null) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	public static boolean checkObjFieldIsNotNull(Object obj) throws IllegalAccessException {

		boolean flag = false;
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			log.info(f.getName());
			if (!f.getName().equals("serialVersionUID") && f.get(obj) != null) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	/**
	 * 将传入的字符串排序
	 *
	 * @param str 用，分割的字符串
	 * @return 也用，分割的已排列好序的字符串
	 */
	public static String orderString(String str) {
		if (!StringUtils.isEmptyOrNull(str) && str.contains(",")) {
			String[] array = str.split(",");
			List<String> list = Arrays.asList(array);
			Collections.sort(list);
			str = Joiner.on(",").join(list);
		}
		return str;
	}

	/**
	 * 判断两个list集合是否相等
	 * 
	 * @param A Integer 集合
	 * @param B Integer 集合
	 * @return
	 */
	public static boolean isEqualIntegerList(List<Integer> A, List<Integer> B) {
		if (CollectionUtils.isEmpty(A) && CollectionUtils.isEmpty(B)) {
			return true;
		}
		if (CollectionUtils.isEmpty(A) && !CollectionUtils.isEmpty(B)) {
			return false;
		}
		if (!CollectionUtils.isEmpty(A) && CollectionUtils.isEmpty(B)) {
			return false;
		}
		if (A.size() != B.size()) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < A.size(); i++) {
			if (B.contains(A.get(i))) {
				count++;
			}
		}
		if (count == A.size()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

}
