/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.http;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import de.greenrobot.event.EventBus;

/**
 * 听讯科技：网络响应处理类
 * 
 * @author hui.wang
 * @date 2015.8.5
 */
public class TXResponseHandler extends JsonHttpResponseHandler {
	/**
	 * 网络请求标记
	 */
	private String requestTag;

	/**
	 * 设置网络请求标记
	 * 
	 * @param tag
	 *            request tag
	 */
	public void setRequestTag(String tag) {
		requestTag = tag;
	}

	/**
	 * 获取网络请求标记
	 * 
	 * @return request tag
	 */
	public String getRequestTag() {
		return requestTag;
	}

	/**
	 * 服务器以对象的形式返回数据
	 * 
	 * @param instance
	 *            An instance of this response object
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param response
	 *            parsed response if any
	 */
	@Override
	public void onSuccess(ResponseHandlerInterface instance, int statusCode, Header[] headers, JSONObject response) {
		if (instance instanceof TXResponseHandler) {
			TXResponseHandler handler = (TXResponseHandler) instance;
			EventBus.getDefault().post(new TXResponseEvent(handler.getRequestTag(), statusCode, "success", response));
		}
	}

	/**
	 * 服务器返回数据出错
	 * 
	 * @param instance
	 *            An instance of this response object
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param throwable
	 *            throwable describing the way request failed
	 * @param errorResponse
	 *            parsed response if any
	 */
	@Override
	public void onFailure(ResponseHandlerInterface instance, int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
		if (instance instanceof TXResponseHandler) {
			TXResponseHandler handler = (TXResponseHandler) instance;
			EventBus.getDefault().post(new TXResponseEvent(handler.getRequestTag(), statusCode, "failure", errorResponse));
		}
	}

}
