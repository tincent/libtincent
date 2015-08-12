/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 听讯科技：ScrollView检讨ListView
 * 
 * @author hui.wang
 * @date 2015.3.24
 * 
 */
public class TXListViewForScrollView extends ListView {

	public TXListViewForScrollView(Context context) {
		super(context);
	}

	public TXListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TXListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
