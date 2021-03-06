package com.sensing.core.utils.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 查询日期的工具类
 * <p>
 * Title: QueryDateUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 * 
 * @author mingxingyu
 * @date 2019年3月18日
 * @version 1.0
 */
public class QueryDateUtils {

	/**
	 * 获取今天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] getToday() {
		SimpleDateFormat sdf12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currDate = new Date();
			Date oldDate = sdf12.parse(sdf1.format(currDate) + " 00:00:00");
			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取昨天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] getYesterday() {
		SimpleDateFormat sdf12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currDate);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date oldDate = calendar.getTime();

			return new Date[] { sdf12.parse(sdf1.format(oldDate) + " 23:59:59"),
					sdf12.parse(sdf1.format(oldDate) + " 00:00:00") };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取最近三天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] get3Day() {
		try {
			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -2);
			Date oldDate = calendar.getTime();

			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取最近七天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] get7Day() {
		try {
			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -6);
			Date oldDate = calendar.getTime();

			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取7天前的七天 大值在前，小值在后
	 * 
	 * @return
	 */
	public static Date[] getMinus7Day() {
		try {
//			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -6);
			Date currDate = calendar.getTime();
			calendar.add(Calendar.DAY_OF_YEAR, -6);
			Date oldDate = calendar.getTime();

			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取最近15天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] get15Day() {
		try {
			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -14);
			Date oldDate = calendar.getTime();

			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取30天前的30天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] getMinus30Day() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -29);
			Date currDate = calendar.getTime();
			calendar.add(Calendar.DAY_OF_YEAR, -30);
			Date oldDate = calendar.getTime();

			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取最近30天的日期。大值在前，小值在后
	 * 
	 * @return
	 * @author mingxingyu
	 * @date 2019年3月18日 上午11:49:10
	 */
	public static Date[] get30Day() {
		try {
			Date currDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDayBegin());
			calendar.add(Calendar.DAY_OF_YEAR, -29);
			Date oldDate = calendar.getTime();
			
			return new Date[] { currDate, oldDate };
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取当天的开始时间
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
