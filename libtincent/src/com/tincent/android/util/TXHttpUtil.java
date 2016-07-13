/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.tincent.android.http.TXResponseHandler;

/**
 * 听讯科技：网络请求工具类
 * 
 * @author hui.wang
 * @date 2015.8.5
 */
public class TXHttpUtil {

	private static TXHttpUtil httpUtil;

	private AsyncHttpClient asyncHttpClient;

	private TXHttpUtil() {
		asyncHttpClient = new AsyncHttpClient();
	}

	/**
	 * 获取对象单子实例
	 * 
	 * @return
	 */
	public static TXHttpUtil getInstance() {
		if (httpUtil == null) {
			httpUtil = new TXHttpUtil();
		}
		return httpUtil;
	}

	/**
	 * 以Get方式进行络请求
	 * 
	 * @param url
	 *            网络地址
	 * @param params
	 *            请求参数
	 * @param responseHandler
	 *            结果处理器
	 */
	public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.get(url, params, responseHandler);
	}

	/**
	 * 以Post方式进行络请求
	 * 
	 * @param url
	 *            网络地址
	 * @param params
	 *            请求参数
	 * @param responseHandler
	 *            结果处理器
	 */
	public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.post(url, params, responseHandler);
	}

	/**
	 * 以Post方式进行络请求(参数entity类型)
	 * 
	 * @param context
	 * @param url
	 *            网络地址
	 * @param entity
	 *            请求参数
	 * @param contentType
	 * @param responseHandler
	 */
	public void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.post(context, url, entity, contentType, responseHandler);
	}

	/**
	 * 以GET方式进行网络请求
	 * 
	 * @param url
	 *            网络地址
	 * @param params
	 *            请求参数
	 * @param requestTag
	 *            请求标记
	 */
	public void get(String url, RequestParams params, String requestTag) {
		TXResponseHandler handler = new TXResponseHandler();
		handler.setRequestTag(requestTag);
		get(url, params, handler);
	}

	/**
	 * 以delete方式进行络请求(参数entity类型)
	 * 
	 * @param context
	 * @param url
	 *            网络地址
	 * @param entity
	 *            请求参数
	 * @param contentType
	 * @param responseHandler
	 */
	public void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.delete(url, params, responseHandler);
	}

	/**
	 * 以POST方式进行网络请求
	 * 
	 * @param url
	 *            网络地址
	 * @param params
	 *            请求参数
	 * @param requestTag
	 *            请求标记
	 */
	public void post(String url, RequestParams params, String requestTag) {
		TXResponseHandler handler = new TXResponseHandler();
		handler.setRequestTag(requestTag);
		post(url, params, handler);
	}

	/**
	 * @param context
	 * @param url
	 * @param entity
	 * @param contentType
	 * @param requestTag
	 */
	public void post(Context context, String url, HttpEntity entity, String contentType, String requestTag) {
		TXResponseHandler handler = new TXResponseHandler();
		handler.setRequestTag(requestTag);
		post(context, url, entity, contentType, handler);
	}

	/**
	 * @return 异步网络请求客户端
	 */
	public AsyncHttpClient getAsyncHttpClient() {
		return asyncHttpClient;
	}

	/**
	 * @return 同步网络请求客户端
	 */
	public AsyncHttpClient getSyncHttpClient() {
		return new SyncHttpClient();
	}

	/**
	 * 设置网络请求头信息
	 * 
	 * @param context
	 */
	public void setHttpClientHeadInfo(Context context) {

		// 平台（ios,android）
		asyncHttpClient.addHeader("platform", "android");
		// 时间戳
		asyncHttpClient.addHeader("timestamp", String.valueOf(System.currentTimeMillis()));
		// 网卡地址
		asyncHttpClient.addHeader("mac", TXSysInfoUtils.getMacAddress(context));
		// 设备操作系统
		asyncHttpClient.addHeader("osversion", "android");
		// 设备序列号
		asyncHttpClient.addHeader("udid", TXSysInfoUtils.readTelephoneSerialNum(context));
		// 客户端版本
		asyncHttpClient.addHeader("clientversion", TXSysInfoUtils.getVersionCode(context) + "");
		// 手机型号
		asyncHttpClient.addHeader("model", TXSysInfoUtils.getModel());
		// 网络模式
		asyncHttpClient.addHeader("carrier", TXSysInfoUtils.getCarrier(context));
	}

	/**
	 * 设置headers
	 * 
	 * @param content_type
	 */
	public void addContentType(String content_type) {
		asyncHttpClient.addHeader("Content-Type", content_type);
	}

	/**
	 * 设置headers
	 * 
	 * @param UID
	 */
	public void addHeader(String UID) {
		asyncHttpClient.addHeader("UID", UID);
	}
}
