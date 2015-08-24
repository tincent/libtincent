package com.tincent.demo.activity;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;

/**
 * 设置界面
 * 
 * @author hui.wang
 * 
 */
public class SettingActivity extends BaseActivity {

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back:
			finish();
			break;
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_setting);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("设置");

	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
