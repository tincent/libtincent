/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tincent.android.R;
import com.tincent.android.util.TXDebug;
import com.tincent.android.view.TXProgressDialog;

import de.greenrobot.event.EventBus;

/**
 * 听讯科技：抽象页面类
 * 
 * @author hui.wang
 * @date 2015.3.5
 */
public abstract class TXAbsFragment extends Fragment implements OnClickListener, Callback {
	public Handler mHandler;

	private Context context;

	private TXProgressDialog progressDialog;

	/** 根布局 */
	public FrameLayout containerRoot;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		TXDebug.o(new Exception());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 注册事件处理回调
		EventBus.getDefault().register(this);
		// 创建主线程消息处理器
		mHandler = new Handler(this);

		progressDialog = TXProgressDialog.createDialog(context);
		progressDialog.setCanceledOnTouchOutside(false);

		// 子类初始化数据
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		containerRoot = (FrameLayout) inflater.inflate(R.layout.fragment_main, null);

		View contentView = createView(inflater);
		containerRoot.addView(contentView);
		initView(containerRoot);

		return containerRoot;
	}

	@Override
	public void onResume() {
		super.onResume();
		// 子类更新界面
		updateView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 解除事件处理回调
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		context = null;
		TXDebug.o(new Exception());
	}

	/**
	 * 显示dialog 但是返回时，留在当前页面
	 */
	public void showLoadingAndStay() {
		progressDialog.setMessage(getString(R.string.tip_loading));
		progressDialog.show();
	}

	/**
	 * 显示dialog 但是返回时，留在当前页面
	 * 
	 * @param content
	 *            加载显示的内容
	 */
	public void showLoadingAndStay(String content) {
		progressDialog.setMessage(content);
		progressDialog.show();
	}

	/**
	 * 隐藏加载框
	 */
	public void hideLoading() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 创建界面，需要子类实现
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	public abstract View createView(LayoutInflater inflater);

	/**
	 * 初始化界面，需要子类实现
	 * 
	 * @param rootView
	 */
	public abstract void initView(View rootView);

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	/**
	 * 更新界面
	 */
	public abstract void updateView();

	/**
	 * 事件处理回调
	 * 
	 * @param evt
	 */
	public abstract void onEventMainThread(Object evt);
}
