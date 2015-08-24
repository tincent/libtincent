/**
 * 
 */
package com.tincent.demo.adapter;

import com.tincent.demo.fragment.HomeFragment;
import com.tincent.demo.fragment.MineFragment;
import com.tincent.demo.fragment.NewsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 主页适配器
 * 
 * @author hui.wang
 * @date 2015.4.2
 * 
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
	// 首页
	public static final int FRAGMENT_HOME = 0;
	// 资讯页面
	public static final int FRAGMENT_NEWS = 1;
	// 个人中心页面
	public static final int FRAGMENT_MINE = 2;

	public static final int FRAGMENT_ARRAY[] = { FRAGMENT_HOME, FRAGMENT_NEWS, FRAGMENT_MINE };

	public HomeFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case FRAGMENT_HOME:
			return new HomeFragment();
		case FRAGMENT_NEWS:
			return new NewsFragment();
		case FRAGMENT_MINE:
			return new MineFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return FRAGMENT_ARRAY.length;
	}

}
