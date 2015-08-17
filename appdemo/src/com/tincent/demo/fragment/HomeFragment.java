/**
 * 
 */
package com.tincent.demo.fragment;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.view.TXPageIndicator;
import com.tincent.demo.R;
import com.tincent.demo.activity.FoodAndDrinkActivity;
import com.tincent.demo.activity.HomeActivity;
import com.tincent.demo.activity.IPCheckActivity;
import com.tincent.demo.activity.TestActivity;
import com.tincent.demo.adapter.AdBannerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author hui.wang
 * 
 */
public class HomeFragment extends BaseFragment {
	private AdBannerAdapter adapter;
	private ViewPager adViewPager;
	private TXPageIndicator indicator;
	private HomeActivity homeActivity;

	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void initView(View rootView) {
		ImageButton menu = (ImageButton) rootView.findViewById(R.id.back);
		menu.setImageResource(R.drawable.icon_menu);
		menu.setOnClickListener(this);

		rootView.findViewById(R.id.more).setVisibility(View.GONE);

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText("我的主页");

		adViewPager = (ViewPager) rootView.findViewById(R.id.image_pager);
		adapter = new AdBannerAdapter(getActivity());
		adViewPager.setAdapter(adapter);
		indicator = (TXPageIndicator) rootView.findViewById(R.id.indicator);
		indicator.setViewPager(adViewPager);
		
		rootView.findViewById(R.id.txtFuction1).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction2).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction3).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction4).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction5).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction6).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction7).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction8).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction9).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			homeActivity.toggleLeftMenu();
			break;
		case R.id.txtFuction1:
			startActivity(new Intent(getActivity(), IPCheckActivity.class));
			break;
		case R.id.txtFuction2:
			startActivity(new Intent(getActivity(), FoodAndDrinkActivity.class));
			break;
		case R.id.txtFuction3:
		case R.id.txtFuction4:
		case R.id.txtFuction5:
		case R.id.txtFuction6:
		case R.id.txtFuction7:
		case R.id.txtFuction8:
		case R.id.txtFuction9:
			startActivity(new Intent(getActivity(), TestActivity.class));
			break;
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initData() {
		homeActivity = (HomeActivity) getActivity();

	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		// TODO Auto-generated method stub

	}

}
