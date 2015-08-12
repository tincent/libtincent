/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 听讯科技：抽象视图类
 * 
 * @author hui.wang
 * @date 2015.8.5
 * 
 */
public abstract class TXAbsView extends View {

	/**
	 * @param context
	 */
	public TXAbsView(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TXAbsView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TXAbsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	/**
	 * 初始化视图
	 */
	public abstract void initView();

}
