/**
 * 
 */
package com.tincent.demo.activity;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.view.slidemenu.SlidingMenu;
import com.tincent.demo.R;
import com.tincent.demo.adapter.HomeFragmentAdapter;

import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

/**
 * @author hui.wang
 * 
 */
public class HomeActivity extends BaseActivity implements OnPageChangeListener, OnTabChangeListener {
	private TabHost tabHost;
	private PagerAdapter pageAdapter;
	private ViewPager viewPager;
	private LayoutInflater layoutInflater;
	private SlidingMenu slideLeftMenu;
	private SlidingMenu slideRightMenu;

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_home);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pageAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(2);
		layoutInflater = getLayoutInflater();

		tabHost.setup();
		tabHost.setOnTabChangedListener(this);
		tabHost.addTab(tabHost.newTabSpec("主页").setIndicator(layoutInflater.inflate(R.layout.tab_home, null)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("资讯").setIndicator(layoutInflater.inflate(R.layout.tab_news, null)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("我的").setIndicator(layoutInflater.inflate(R.layout.tab_mine, null)).setContent(android.R.id.tabcontent));

		// configure the SlidingMenu
		slideLeftMenu = new SlidingMenu(this);
		slideLeftMenu.setMode(SlidingMenu.LEFT);
		slideLeftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slideLeftMenu.setShadowWidthRes(R.dimen.shadow_width);
		slideLeftMenu.setShadowDrawable(R.drawable.shadow);
		slideLeftMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slideLeftMenu.setFadeDegree(0.35f);
		slideLeftMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slideLeftMenu.setMenu(R.layout.sliding_menu);
		slideLeftMenu.findViewById(R.id.notice).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.medical_info).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.leave_summary).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.recover_note).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.online_book).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.love_info).setOnClickListener(this);
		slideLeftMenu.findViewById(R.id.visitor_note).setOnClickListener(this);
		
	}
	
	public void toggleLeftMenu() {
		slideLeftMenu.toggle();
	}
	
	public boolean isLeftMenuShowing() {
		return slideLeftMenu.isMenuShowing();
	}

	private boolean mExitConfirm = false;
	private long mLastTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long time = event.getEventTime();
			if (time - mLastTime > 2000) {
				mExitConfirm = false;
			}
			mLastTime = time;
			if (!mExitConfirm) {
				slideLeftMenu.showContent();
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitConfirm = true;
				return true;
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

	@Override
	public void onTabChanged(String tabId) {
		viewPager.setCurrentItem(tabHost.getCurrentTab());

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tabHost.setCurrentTab(position);
	}

}
