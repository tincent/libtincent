/**
 * 
 */
package com.tincent.demo.activity;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.activity.BaseActivity;
import com.tincent.demo.adapter.NumberAdapter;

import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 数字列表
 * 
 * @author hui.wang
 * @date 2015.12.14
 *
 */
public class NumListActivity extends BaseActivity {
	private ListView listView;
	private NumberAdapter adapter;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_num_list);

	}

	@Override
	public void initData() {
		adapter = new NumberAdapter(this);
		listView.setAdapter(adapter);

	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("数字列表");

		listView = (ListView) findViewById(R.id.lvNumList);

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
