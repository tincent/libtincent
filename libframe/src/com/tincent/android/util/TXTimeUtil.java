/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.format.Time;

/**
 * 听讯科技：时间转换工具类
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXTimeUtil {

	/**
	 * 把输入格式化为年月日
	 * 
	 * @param millis
	 * @return
	 */
	public static String format2YMD(long millis) {
		Time time = new Time();
		time.set(millis);
		return time.format3339(true);
	}

	/**
	 * 把输入格式化为年月日时人秒
	 * 
	 * @param millis
	 * @return
	 */
	public static String format2YMDHMS(long millis) {
		Time time = new Time();
		time.set(millis);
		return time.format2445();
	}

	/**
	 * 格式化日期到时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}

	/**
	 * 格式化毫秒到时间
	 * 
	 * @param millis
	 * @param format
	 * @return
	 */
	public static String format(long millis, String format) {

		return new SimpleDateFormat(format).format(new Date(millis));
	}

	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";

	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 日期格式
	public static String TIME_FORMAT_M_D_H_M = "MM-dd HH:mm";

	// 长日期格式
	public static String TIME_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2long(String date, String format) {

		format = TIME_FORMAT;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0l;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String convert2String(long time) {
		if (time > 0l) {
			String format = TIME_FORMAT;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String convert2String2(long time) {
		if (time > 0l) {
			String format = TIME_FORMAT_M_D_H_M;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 将长整型数字转换为年月日
	 * 
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String convert2StringYMD(long time) {
		if (time > 0l) {
			String format = DATE_FORMAT;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 将长整型数字转换为年月日时分
	 * 
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String convert2StringYMDHM(long time) {
		if (time > 0l) {
			String format = TIME_FORMAT_YMDHM;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 获取当前系统的日期
	 * 
	 * @return
	 */
	public static long curTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 人性化显示时间，如：几小时前
	 * 
	 * @param ctime
	 * @return
	 */
	public static String showTime(long ctime) {
		String r = "";

		long nowtimelong = System.currentTimeMillis();
		long result = Math.abs(nowtimelong - ctime);

		if (result < 60000)// 一分钟内
		{
			long seconds = result / 1000;
			r = seconds + "秒钟前";
		} else if (result >= 60000 && result < 3600000)// 一小时内
		{
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000)// 一天内
		{
			long seconds = result / 3600000;
			r = seconds + "小时前";
		} else
		// 日期格式
		{
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT_M_D_H_M);
			Date date = new Date(ctime);
			r = sf.format(date);
		}
		return r;
	}

}
