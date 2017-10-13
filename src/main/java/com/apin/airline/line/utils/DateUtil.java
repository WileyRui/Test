package com.apin.airline.line.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author wiley
 *
 * @2017年7月14日 下午4:09:59
 */
public class DateUtil {
	/**
	 * 获取当前日期的星期
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 字符串转date
	 * 
	 * @param date
	 * @return
	 */
	public static Date String2Date(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date1 = sdf.parse(date);
			return date1;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 返回当前日期是否符合所选班期
	 * 
	 * @param dt
	 * @return
	 */
	public static boolean theWeekOfDateIsMatch(Date dt, List<String> banqi) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		String theWeekOfDate = w + "";
		if (banqi.contains(theWeekOfDate))
			return true;
		return false;
	}

	/**
	 * 返回当前日期是否符合所选班期
	 * 
	 * @param dt
	 * @return
	 */
	public static List<String> getMatchDayList(List<String> dateList, List<String> banqi) {
		List<String> newDateList = new ArrayList<>();
		for (String dateStr : dateList) {
			if (theWeekOfDateIsMatch(String2Date(dateStr, "yyyy-MM-dd"), banqi)) {
				newDateList.add(dateStr);
			}
		}
		return newDateList;
	}

	/**
	 * 将时间段分割（2017-07-16/2017-07-17）
	 * 
	 * @param timePeriod
	 * @return
	 */
	public static String[] getDateArrayByTimePeriod(String timePeriod) {
		String[] datePeriod = timePeriod.split("/");
		return datePeriod;
	}

	/**
	 * 日期加减
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dateAddSub(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		Date dateAdd = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return dateAdd;
	}

	/**
	 * date2String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getNow(String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(date);
		return now;
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 * @param date2
	 * @param pattern
	 * @return
	 */
	public static int compareDate(String date1, String date2, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date11 = sdf.parse(date1);
			Date date22 = sdf.parse(date2);
			if (date11.getTime() > date22.getTime()) {
				return 1;
			} else if (date11.getTime() < date22.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 生成航线编号
	 * 
	 * @return
	 */
	public static String createAirlineNo() {
		String seq = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		seq = sdf.format(date);
		return seq;
	}
}
