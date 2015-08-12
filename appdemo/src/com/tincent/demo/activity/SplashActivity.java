/**
 * 
 */
package com.tincent.demo.activity;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;

import android.content.Intent;
import android.os.Message;
import android.view.View;

/**
 * 闪屏页面
 * 
 * @author hui.wang
 * 
 */
public class SplashActivity extends BaseActivity implements Runnable {

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_splash);

	}

	@Override
	public void initData() {
		mMainHandler.postDelayed(this, 2000);
	}

	@Override
	public void initView() {

	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onEventMainThread(Object evt) {

	}


	@Override
	public void run() {
		startActivity(new Intent(this, HomeActivity.class));
		finish();
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		
	}

}
