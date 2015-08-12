/**
 * 
 */
package com.tincent.demo.activity;

import com.tincent.android.activity.TXAbsActivity;
import com.tincent.android.http.TXResponseEvent;

/**
 * 界面基类
 * 
 * @author hui.wang
 * 
 */
public abstract class BaseActivity extends TXAbsActivity {
	@Override
	public void onEventMainThread(Object evt) {
		if (evt instanceof TXResponseEvent) {
			TXResponseEvent event = (TXResponseEvent) evt;
			if (event.statusCode == TXResponseEvent.REQUET_OK_CODE) {
				onResponseSuccess(event);
			} else {
				onResponseFailed(event);
			}
		}
	}

	/**
	 * 网格请求成功，子类分别处理
	 * 
	 * @param evt
	 */
	public abstract void onResponseSuccess(TXResponseEvent evt);

	/**
	 * 网络请求失败，基类统计处理
	 * 
	 * @param evt
	 */
	public void onResponseFailed(TXResponseEvent evt) {
		hideLoading();
	}
}
