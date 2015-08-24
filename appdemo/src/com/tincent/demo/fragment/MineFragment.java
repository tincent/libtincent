/**
 * 
 */
package com.tincent.demo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.activity.SettingActivity;
import com.tincent.demo.adapter.MimeFragmentAdapter;
import com.tincent.demo.view.PagerSlidingTabStrip;

/**
 * @author hui.wang
 * 
 */
public class MineFragment extends BaseFragment {
	private PagerAdapter pagerAdapter;
	private ViewPager viewPager;
	private PagerSlidingTabStrip tabStrip;

	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, container, false);
	}

	@Override
	public void initView(View rootView) {
		rootView.findViewById(R.id.back).setVisibility(View.GONE);

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText("个人中心");

		rootView.findViewById(R.id.more).setVisibility(View.VISIBLE);
		rootView.findViewById(R.id.more).setOnClickListener(this);

		viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
		pagerAdapter = new MimeFragmentAdapter(getActivity().getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOffscreenPageLimit(2);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		tabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tab_strip);
		tabStrip.setTextColor(Color.parseColor("#2c8cc6"));
		tabStrip.setShouldExpand(true);
		tabStrip.setViewPager(viewPager);
		tabStrip.setVisibility(View.VISIBLE);
		tabStrip.setIndicatorColor(Color.parseColor("#2c8cc6"));
		// 设置 tab 控件底线高度
		tabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		tabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, dm));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more:
			startActivity(new Intent(getActivity(), SettingActivity.class));
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
	public void initData() {

	}

	@Override
	public void updateView() {

	}

}
