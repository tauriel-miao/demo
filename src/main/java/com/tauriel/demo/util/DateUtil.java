package com.tauriel.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @ClassName: DateUtil
 * @Description: TODO 日期处理工具类
 * @author wanggengwu
 * @date 2014-3-12 上午10:31:25
 * @version V1.0
 */
public class DateUtil {
	private final static String DEFAULTFORMATMODEL = "yyyyMMddHHmmss";

	public static String getCountDate(String date, String Pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(Pattern);
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			try {
				cal.setTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return sdf.format(cal.getTime());
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getCurrentDate
	 * @Description: TODO 按照指定的格式对字符串类型的时间进行格式化
	 * @param @param formatModel 格式化的日期模型
	 * @param @param time 需要格式化的日期
	 * @param @return
	 * @param @throws ParseException
	 * @return Date
	 * @throws
	 */
	public static Date getCurrentDate(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		date = dateFormat.parse(time);
		return date;
	}

	/**
	 * 
	 * @Title: getCurrentDate
	 * @Description: TODO 获得指定毫秒数时间对应的字符串时间
	 * @param @param formatModel 格式化的日期模型
	 * @param @param Millisecond 毫秒数
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCurrentDate(String formatModel, long Milliseconds) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Milliseconds);
		return dateFormat.format(c.getTime());
	}

	/**
	 * 
	 * @Title: getCurrentDate
	 * @Description: TODO 把date类型转化成相应的String类型
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCurrentDate(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		return dateFormat.format(time);
	}

	/**
	 * 获取现在时间
	 * @return返回字符串格式yyyyMMddHHmmss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 由年月日时分秒+3位随机数
	 * 生成流水号
	 * @return
	 */
	public static String getNum(){
		String t = getStringDate();
		int x=(int)(Math.random()*900)+100;
		String serial = t + x;
		return serial;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getNum());
	}

	/**
	 * 
	 * @Title: getCurrentDate
	 * @Description: TODO 把相应的毫秒数转换成对应的date类型
	 * @param @param Milliseconds
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date getCurrentDate(long Milliseconds) {
		Date date = null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Milliseconds);
		date = c.getTime();
		return date;
	}

	/**
	 * 
	 * @Title: getCurrentMilliseconds
	 * @Description: TODO 获得对应日期的毫秒数
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @return long
	 * @throws
	 */
	public static long getCurrentMilliseconds(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.getTimeInMillis();
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getCurrentMilliseconds
	 * @Description: TODO 获得对应日期的毫秒数
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return long
	 * @throws
	 */
	public static long getCurrentMilliseconds(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		date = dateFormat.parse(time);
		return date.getTime();
	}

	/**
	 * 获得当前日期的最后一天
	 * 
	 * @Title:getLastDayOfCurrentMonth
	 * @Description: TODO
	 * @param formatModel
	 * @param time
	 * @return String
	 * @throws ParseException
	 * @throws
	 * @author wanggengwu 2014-5-14 上午11:22:30
	 */
	public static String getLastDayOfCurrentMonth(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String lastDay = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, -1);
		time = dateFormat.format(calendar.getTime());
		return lastDay;
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: TODO 获取上个月的月底
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfLastMonth(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String lastday = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		lastday = dateFormat.format(calendar.getTime());
		return lastday;
	}

	/**
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: TODO 获取上个月的月底
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfLastMonth(String formatModel, long time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return getCurrentDate(formatModel, calendar.getTime());
	}

	/**
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: TODO 获取上个月的月底
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfLastMonth(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return getCurrentDate(formatModel, calendar.getTime());
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getLastDayOfNextMonth
	 * @Description: TODO
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfNextMonth(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String lastday = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		lastday = dateFormat.format(calendar.getTime());
		return lastday;
	}

	/**
	 * 
	 * @Title: getLastDayOfNextMonth
	 * @Description: TODO
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfNextMonth(String formatModel, long time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return dateFormat.format(calendar.getTime());
	}

	public static String getLastDayOfNextMonth(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getCurrentDayOfNextMonth
	 * @Description: TODO 获得下个月的今天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextMonth(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String currentDay = "";

		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		currentDay = dateFormat.format(calendar.getTime());
		return currentDay;
	}

	public static Date getCurrentDayOfNextMonth(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: getCurrentDayOfNextMonth
	 * @Description: TODO 获得下个月的今天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextMonth(String formatModel, long time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 
	 * @Title: getCurrentDayOfNextMonth
	 * @Description: TODO 获得下个月的今天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextMonth(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}

	public static String getFirstDayOfLastMonth(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String firstDay = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		firstDay = dateFormat.format(calendar.getTime());
		return firstDay;
	}

	/**
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: TODO 获取上个月的月底
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfLastMonth(String formatModel, long time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: TODO 获取上个月的月底
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfLastMonth(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getCurrentDayOfNextWeek
	 * @Description: TODO 获得下周的这天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextWeek(String formatModel, String time) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String current = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Date date = dateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		current = dateFormat.format(calendar.getTime());
		return current;
	}

	/**
	 * 
	 * @Title: getCurrentDayOfNextWeek
	 * @Description: TODO 获得下周的这天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextWeek(String formatModel, long time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 
	 * @Title: getCurrentDayOfNextWeek
	 * @Description: TODO 获得下周的这天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getCurrentDayOfNextWeek(String formatModel, Date time) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		return dateFormat.format(calendar.getTime());
	}

	public static Date getCurrentDayOfNextWeek(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getNextDay
	 * @Description: TODO 获得下一天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */
	public static String getNextDay(String formatModel, String time, int days) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String nextDay = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(time));
		calendar.add(Calendar.DATE, days);
		nextDay = dateFormat.format(calendar.getTime());
		return nextDay;
	}

	/**
	 * 
	 * @Title: getNextDay
	 * @Description: TODO 获得下一天
	 * @param @param formatModel
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getNextDay(String formatModel, Date time, int days) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, days);
		return dateFormat.format(calendar.getTime());
	}

	public static String getNextDay(String formatModel, long time, int days) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.DATE, days);
		return dateFormat.format(calendar.getTime());
	}

	public static String getLastDay(String formatModel, String time, int days) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String nextDay = "";
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(time));
		calendar.add(Calendar.DATE, days < 0 ? days : 0 - days);
		nextDay = dateFormat.format(calendar.getTime());
		return nextDay;
	}

	public static String getLastDay(String formatModel, long time, int days) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.DATE, days < 0 ? days : 0 - days);
		return dateFormat.format(calendar.getTime());
	}

	public static String getLastDay(String formatModel, Date time, int days) {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		DateFormat dateFormat = new SimpleDateFormat(formatModel);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, days < 0 ? days : 0 - days);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 
	 * @Title: getCurrentQuarter
	 * @Description: TODO 获得相应时间对应的季度
	 * @param @param formatModel
	 * @param @param date
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCurrentQuarter(String formatModel, String date) throws ParseException {
		formatModel = formatModel == null || "".equals(formatModel.trim()) ? DEFAULTFORMATMODEL : formatModel.trim();
		String quarter = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate(formatModel, date));
		if (calendar.get(Calendar.MONTH) > 1 && calendar.get(Calendar.MONTH) <= 3) {
			quarter = calendar.get(Calendar.YEAR) + "01";
		} else if (calendar.get(Calendar.MONTH) > 3 && calendar.get(Calendar.MONTH) <= 6) {
			quarter = calendar.get(Calendar.YEAR) + "02";
		} else if (calendar.get(Calendar.MONTH) > 6 && calendar.get(Calendar.MONTH) <= 9) {
			quarter = calendar.get(Calendar.YEAR) + "03";
		} else if (calendar.get(Calendar.MONTH) > 9 && calendar.get(Calendar.MONTH) <= 12) {
			quarter = calendar.get(Calendar.YEAR) + "04";
		}
		return quarter;
	}

	/**
	 * 
	 * @Title:reversalDate
	 * @Description:TODO 日期翻转
	 * @param formatModel
	 * @param date
	 * @return
	 * @throws ParseException
	 * @return:long
	 * @author:blueCrush
	 * @date:2016-3-19下午1:09:00
	 */
	public static long reversalDate(String formatModel, String date) throws ParseException {
		long result = 0L;
		result = Long.MAX_VALUE - getCurrentMilliseconds(formatModel, date);
		return result;
	}
}
