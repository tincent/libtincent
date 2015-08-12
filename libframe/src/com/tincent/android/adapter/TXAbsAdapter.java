/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.adapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * 抽象适配器类
 * 
 * @author hui.wang
 * @date 2015.3.5
 */
public abstract class TXAbsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;

	public ImageLoader mImageLoader;

	public TXAbsAdapter(Context ctx) {
		context = ctx;
		layoutInflater = LayoutInflater.from(ctx);
		mImageLoader = ImageLoader.getInstance();
	}

	public LayoutInflater getLayoutInflater() {
		return layoutInflater;
	}

	public Context getContext() {
		return context;
	}
}
