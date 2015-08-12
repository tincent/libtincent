/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 听讯科技：ScrollView嵌套GridView
 * 
 * @author hui.wang
 * @date 2015.3.24
 * 
 */
public class TXGridViewForScrollView extends GridView {
	public TXGridViewForScrollView(Context context) {
		super(context);
	}

	public TXGridViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}