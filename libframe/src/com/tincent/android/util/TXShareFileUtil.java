/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 听讯科技：SharedPreferences工具类，生命周期与application同在，免去每次修改数据后commit的麻烦
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXShareFileUtil {
	private static TXShareFileUtil shareFileUtil;

	private SharedPreferences preferences;

	private TXShareFileUtil() {

	}

	public void init(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static TXShareFileUtil getInstance() {
		if (shareFileUtil == null) {
			shareFileUtil = new TXShareFileUtil();
		}
		return shareFileUtil;
	}

	/**
	 * 从SharePreferences中获取字符串
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		return preferences.getString(key, defValue);
	}

	/**
	 * 从SharePreferences中获取整数
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getInt(String key, int defValue) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		return preferences.getInt(key, defValue);
	}

	/**
	 * 从SharePreferences中获取长整数
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long getLong(String key, long defValue) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		return preferences.getLong(key, defValue);
	}

	/**
	 * 从SharePreferences中获取符点数
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloat(String key, float defValue) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		return preferences.getFloat(key, defValue);
	}

	/**
	 * 从SharePreferences中获取布尔值
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defValue) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		return preferences.getBoolean(key, defValue);
	}

	/**
	 * 设置字符串到ShareProferences
	 * 
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * 设置整数到ShareProferences
	 * 
	 * @param key
	 * @param value
	 */
	public void putInt(String key, int value) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().putInt(key, value).commit();
	}

	/**
	 * 设置长整数到ShareProferences
	 * 
	 * @param key
	 * @param value
	 */
	public void putLong(String key, long value) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().putLong(key, value).commit();
	}

	/**
	 * 设置符点数到ShareProferences
	 * 
	 * @param key
	 * @param value
	 */
	public void putFloat(String key, float value) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().putFloat(key, value).commit();
	}

	/**
	 * 设置布尔值到ShareProferences
	 * 
	 * @param key
	 * @param value
	 */
	public void putBoolean(String key, boolean value) {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 清除SharePreferences中的数据
	 */
	public void clear() {
		if (preferences == null) {
			throw new NullPointerException(
					"should call init() before any other method!!!");
		}
		preferences.edit().clear();
	}

}
