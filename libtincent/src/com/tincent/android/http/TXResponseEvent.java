/**
 * 
 */
package com.tincent.android.http;

/**
 * 网络请求结果
 * 
 * @author hui.wang
 * @date 2015.8.5
 */
public class TXResponseEvent {
	/**
	 * 网络请求成功码
	 */
	public static final int REQUET_OK_CODE = 200;
	/**
	 * 业务请求标签
	 */
	public String requestTag;

	/**
	 * 业务状态码
	 */
	public int statusCode;

	/**
	 * 错误信息
	 */
	public String message;

	/**
	 * 业务数据
	 */
	public Object[] object;

	public TXResponseEvent(String tag, int code) {
		requestTag = tag;
		statusCode = code;
	}

	public TXResponseEvent(String tag, int code, String msg) {
		requestTag = tag;
		statusCode = code;
		message = msg;
	}

	public TXResponseEvent(String tag, int code, Object... obj) {
		requestTag = tag;
		statusCode = code;
		object = obj;
	}

	public TXResponseEvent(String tag, int code, String msg, Object... obj) {
		requestTag = tag;
		statusCode = code;
		message = msg;
		object = obj;
	}

	public String toString() {
		return "requestTag : " + requestTag + ", statusCode : " + statusCode + ", message : " + message;
	}
}
