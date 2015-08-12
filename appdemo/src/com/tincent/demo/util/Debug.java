/**
 * 
 */
package com.tincent.demo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.tincent.android.util.TXTimeUtil;
import com.tincent.demo.Constants;

import android.util.Log;

/**
 * 调试工具类
 * 
 * @author hui.wang
 * @date 2015.3.18
 * 
 */
public class Debug {
	private static final String TAG = "appdemo";

	public static int lineNumber(Exception ex) {
		return ex.getStackTrace()[0].getLineNumber();
	}

	public static String funcName(Exception ex) {
		return ex.getStackTrace()[0].getMethodName();
	}

	public static String className(Exception ex) {
		return ex.getStackTrace()[0].getClassName();
	}

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
		if (Constants.DEBUG) {
			Log.d(TAG, fileName(ex) + " : " + lineNumber(ex) + " : " + funcName(ex) + " : " + msg);
		}
	}

	public static void o(Exception ex) {
		if (Constants.DEBUG) {
			Log.d(TAG, fileName(ex) + " : " + lineNumber(ex) + " : " + funcName(ex));
		}
	}

	public static void o(String tag, String msg) {
		if (Constants.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void o(String tag, String msg, Throwable tr) {
		if (Constants.DEBUG) {
			Log.d(tag, msg, tr);
		}
	}

	public static void dump(String msg) {
		String filename = TXTimeUtil.convert2StringYMD(System.currentTimeMillis()) + ".log";

		File log = new File(Constants.LOG_DIR, filename);

		if (!log.exists()) {
			try {
				// 在指定的文件夹中创建文件
				log.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Debug.o(new Exception(), "log = " + log.toString());

		FileWriter fw = null;

		BufferedWriter bw = null;

		try {
			fw = new FileWriter(log, true);
			bw = new BufferedWriter(fw);
			bw.write(TXTimeUtil.convert2String(System.currentTimeMillis()));
			bw.newLine();
			bw.write(msg);
			bw.newLine();
			bw.flush(); // 刷新该流的缓冲

			bw.close();

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void dump(Exception ex) {
		dump(ex.toString());
	}

	public static void dump(Exception ex, String msg) {
		dump(fileName(ex) + " : " + lineNumber(ex) + " : " + funcName(ex) + " : " + msg);
	}
}
