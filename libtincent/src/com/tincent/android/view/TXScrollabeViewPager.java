/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 听讯科技：可控制是否滑动的ViewPager
 * 
 * @author hui.wang
 * @date 2015.6.11
 * 
 */
public class TXScrollabeViewPager extends ViewPager {
	/**
	 * 是否可滑动
	 */
	private boolean scrollble = true;

	public TXScrollabeViewPager(Context context) {
		super(context);
	}

	public TXScrollabeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (scrollble) {
			return super.onTouchEvent(ev);
		} else {
			return false;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (scrollble) {
			return super.onInterceptTouchEvent(ev);
		} else {
			return false;
		}
	}

	/**
	 * 是否可滑动
	 * 
	 * @return
	 */
	public boolean isScrollble() {
		return scrollble;
	}

	/**
	 * 设置是否可滑动
	 * 
	 * @param scrollble
	 */
	public void setScrollble(boolean scrollble) {
		this.scrollble = scrollble;
	}
}
