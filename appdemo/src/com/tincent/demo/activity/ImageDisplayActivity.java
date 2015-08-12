/**
 * 
 */
package com.tincent.demo.activity;

import java.util.List;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.adapter.ImageDisplayAdapter;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

/**
 * 图片展示类
 * 
 * @author hui.wang
 * @date 2015.5.26
 * 
 */
public class ImageDisplayActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager viewPager;
	private ImageDisplayAdapter adapter;

	private int position;// 当前显示的第几页图片
	private List<String> uriList;// 图片集
	private TextView tvTitle;
	private int type = 1;// 0本地，1网路

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_image_preview);
	}

	@Override
	public void initData() {

		if (getIntent().hasExtra("position")) {
			position = getIntent().getIntExtra("position", 0);
		}
		if (getIntent().hasExtra("uriList")) {
			uriList = getIntent().getStringArrayListExtra("uriList");
		}
		if (getIntent().hasExtra("type")) {
			type = getIntent().getIntExtra("type", 0);
		}
		adapter = new ImageDisplayAdapter(this, uriList, type);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);

		tvTitle.setText((position + 1) + "/" + uriList.size());

		if (type != 0) {
			findViewById(R.id.more).setVisibility(View.GONE);
		}
	}

	@Override
	public void initView() {
		tvTitle = (TextView) findViewById(R.id.title);

		View back = findViewById(R.id.back);
		back.setOnClickListener(this);

		View menu = findViewById(R.id.more);
		menu.setOnClickListener(this);
		menu.setVisibility(View.GONE);

		viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setOnPageChangeListener(this);

	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.more:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tvTitle.setText((position + 1) + "/" + uriList.size());
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

}
