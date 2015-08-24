/**
 * 
 */
package com.tincent.demo.adapter;

import com.tincent.demo.fragment.MainFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 个人中心适配器
 * 
 * @author hui.wang
 * 
 */
public class MimeFragmentAdapter extends FragmentPagerAdapter {
	// 基本信息
	public static final int FRAGMENT_BASIC_INFO = 0;
	// 医疗信息
	public static final int FRAGMENT_MEDICAL_INFO = 1;
	// 出院小结
	public static final int FRAGMENT_DISCHARGE_ABSTRACT = 2;

	public static final int FRAGMENT_ARRAY[] = { FRAGMENT_BASIC_INFO, FRAGMENT_MEDICAL_INFO, FRAGMENT_DISCHARGE_ABSTRACT };

	public static final String[] FRAGMENT_NAME = { "基本信息", "医疗信息", "出院小结" };

	public MimeFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return FRAGMENT_NAME[position];
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case FRAGMENT_BASIC_INFO:
			return new MainFragment();
		case FRAGMENT_MEDICAL_INFO:
			return new MainFragment();
		case FRAGMENT_DISCHARGE_ABSTRACT:
			return new MainFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return FRAGMENT_ARRAY.length;
	}

}
