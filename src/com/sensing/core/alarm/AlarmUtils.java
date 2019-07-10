package com.sensing.core.alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>Title: AlarmUtils</p>
 * <p>Description:报警相关的通用工具类</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2018年11月28日
 * @version 1.0
 */
public class AlarmUtils {
	
	private static Log log = LogFactory.getLog(AlarmUtils.class);

	/**
	 * 判断当前时间所在的周几是否在runWeek字段中
	 * @param runWeek 周几数，多个使用,隔开。例如  1,2,7,3
	 * @param currDate 当前时间点
	 * @return 包含的情况,返回true;不包含的情况,返回false;
	 * @author mingxingyu
	 * @date   2018年11月28日 下午2:16:22
	 */
	public static boolean isRunWeek(String runWeek,Date currDate){
		try {
			String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(currDate);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0){w = 0;}
			
			if ( runWeek.contains(weekDays[w]) ) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断当前时间所在的周几是否在runWeek字段中,该方法发生异常.[runWeek:"+runWeek+";currDate:"+currDate+"]."+e.getMessage());
		}
		return false;
	}
	
	/**
	 * 判断时间点是否在两个时间点的范围之内
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param currTime  当前时间
	 * @return
	 * @author mingxingyu
	 * @date   2018年11月28日 下午2:09:10
	 */
	public static boolean isTimeRange(String startTime,String endTime,Date currDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String format = sdf.format(currDate);
			String[] split = format.split(" ");
			String currTime = split[1];
			
			String[] startTimeArr = startTime.split(":");
			String[] currTimeArr = currTime.split(":");
			
			int startTimeInt = Integer.parseInt(startTimeArr[0])*3600+Integer.parseInt(startTimeArr[1])*60+Integer.parseInt(startTimeArr[2]);
			int currTimeInt = Integer.parseInt(currTimeArr[0])*3600+Integer.parseInt(currTimeArr[1])*60+Integer.parseInt(currTimeArr[2]);

			Integer endTimeInt = null;
			if ( endTime != null && !"".equals(endTime) ) {
				String[] endTimeArr = endTime.split(":");
				endTimeInt = Integer.parseInt(endTimeArr[0])*3600+Integer.parseInt(endTimeArr[1])*60+Integer.parseInt(endTimeArr[2]);
			}
			
			if ( currTimeInt >= startTimeInt && ( endTimeInt==null?true:(currTimeInt <= endTimeInt)) ) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断时间点是否在两个时间点的范围之内,该方法发生异常.[startTime:"+startTime+";endTime:"+endTime+";currDate:"+currDate+"]."+e.getMessage());
		}
		return false;
	}
	
	/**
	 * 判断日期是否在两个日期的范围内
	 * @param beginDate  开始日期，该日期的当天凌晨，即2018-10-12 15:00:00 -->  2018-10-12 00:00:00
	 * @param endDate    结束日期，该日期的当天最后一秒，即2018-10-12 15:00:00 -->  2018-10-12 23:59:59
	 * @param currDate   判断是日期
	 * @return 在两个日期范围内,true;不在两个日期的范围内,fasle
	 * @author mingxingyu
	 * @date   2018年11月28日 下午1:51:30
	 */
	public static boolean isDateRange(Date beginDate,Date endDate,Date currDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String beginDateFormat = sdf.format(beginDate);
			String beginDateStr0 = beginDateFormat.substring(0,10)+" 00:00:00";
			Date beginDateFrom0 = null;
			try {
				beginDateFrom0 = sdf.parse(beginDateStr0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Date endDateEnd59 = null;
			
			if ( endDate != null ) {
				String endDateFormat = sdf.format(endDate);
				String endDateStr59 = endDateFormat.substring(0,10)+" 23:59:59";
				endDateEnd59 = sdf.parse(endDateStr59);
			}
			
			if ( currDate.getTime() >= beginDateFrom0.getTime() && ( endDateEnd59 == null?true:(currDate.getTime() <= endDateEnd59.getTime())) ) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断日期是否在两个日期的范围内，该方法发生异常.[beginDate:"+beginDate+";endDate:"+endDate+";currDate:"+currDate+"]."+e.getMessage());
		}
		return false;
	}
	
}
