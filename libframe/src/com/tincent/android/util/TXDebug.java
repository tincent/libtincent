/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import com.tincent.android.TXConstants;

import android.util.Log;

/**
 * 听讯科技：调试工具类
 * 
 * @author hui.wang
 * @data 2015.3.15
 * 
 */
public class TXDebug {
	private static final String TAG = "tincent";

	/**
	 * 获取行号
	 * 
	 * @param ex
	 * @return
	 */
	public static int lineNumber(Exception ex) {
		return ex.getStackTrace()[0].getLineNumber();
	}

	/**
	 * 获取函数名
	 * 
	 * @param ex
	 * @return
	 */
	public static String funcName(Exception ex) {
		return ex.getStackTrace()[0].getMethodName();
	}

	/**
	 * 获取类名
	 * 
	 * @param ex
	 * @return
	 */
	public static String className(Exception ex) {
		return ex.getStackTrace()[0].getClassName();
	}

	/**
	 * 获取文件名
	 * 
	 * @param ex
	 * @return
	 */
	public static String fileName(Exception ex) {
		return ex.getStackTrace()[0].getFileName();
	}

	/**
	 * Print log message <filename>: <line number> : <method name> : msg
	 * 
	 * @param ex
	 * @param msg
	 */
	public static void o(Exception ex, String msg) {
		if (TXConstants.DEBUG) {
			Log.d(TAG, fileName(ex) + " : " + lineNumber(ex) + " : " + funcName(ex) + " : " + msg);
		}
	}

	/**
	 * Print log message <filename>: <line number> : <method name> : msg
	 * 
	 * @param ex
	 */
	public static void o(Exception ex) {
		if (TXConstants.DEBUG) {
			Log.d(TAG, fileName(ex) + " : " + lineNumber(ex) + " : " + funcName(ex));
		}
	}

	/**
	 * Print log message <filename>: <line number> : <method name> : msg
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void o(String tag, String msg) {
		if (TXConstants.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * Print log message <filename>: <line number> : <method name> : msg
	 * 
	 * @param tag
	 * @param msg
	 * @param tr
	 */
	public static void o(String tag, String msg, Throwable tr) {
		if (TXConstants.DEBUG) {
			Log.d(tag, msg, tr);
		}
	}
}
