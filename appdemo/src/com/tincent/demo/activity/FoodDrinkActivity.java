package com.tincent.demo.activity;

import java.util.ArrayList;
import java.util.List;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.adapter.FoodDrinkAdapter;
import com.tincent.demo.model.TextBean;
import com.tincent.demo.util.DatabaseUtil;
import com.tincent.demo.util.Debug;

import android.database.Cursor;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 推荐饮食界面
 * 
 * @author jiawei.xue
 * 
 *         2015-8-12 上午10:25:41
 */
public class FoodDrinkActivity extends BaseActivity {
	private FoodDrinkAdapter adapter;
	private ListView adviseListView;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_fooddring);

	}

	@Override
	public void initData() {
		adapter = new FoodDrinkAdapter(this);
		adviseListView.setAdapter(adapter);
		String selection = getIntent().getStringExtra("selection");
		String[] selectionArgs = getIntent().getStringArrayExtra("selectionArgs");
		Debug.o(new Exception(), "selection = " + selection + " selectionArgs = " + selectionArgs);
		Cursor cursor = DatabaseUtil.getInstatnce().query(selection, selectionArgs);

		if (cursor != null) {
			Debug.o(new Exception(), "cursor.size = " + cursor.getCount());
			int tipIdx = cursor.getColumnIndex("TIPS_CONTENT");
			List<TextBean> data = new ArrayList<TextBean>();
			while (cursor.moveToNext()) {
				// Debug.o("dbg", "tip = " + cursor.getString(tipIdx));
				TextBean bean = new TextBean();
				bean.name = cursor.getString(tipIdx);
				data.add(bean);
			}
			adapter.updateDate(data);
			cursor.close();
		}
	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("饮食建议");

		adviseListView = (ListView) findViewById(R.id.adviseListView);

	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void exitApp() {

	}


	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		
	}

}
