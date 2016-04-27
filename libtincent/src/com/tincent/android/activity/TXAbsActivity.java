/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tincent.android.R;
import com.tincent.android.TXAbsApplication;
import com.tincent.android.view.TXProgressDialog;

import de.greenrobot.event.EventBus;

/**
 * 听讯科技：抽象界面类
 * 
 * @author hui.wang
 * @date 2015.3.5
 */
public abstract class TXAbsActivity extends FragmentActivity implements OnClickListener, Callback {
	/** 应用程序引用 **/
	public TXAbsApplication mApplication;
	/** 主线程消息处理器 **/
	public Handler mMainHandler;
	/** 后台线程消息处理器 **/
	public Handler mWorkHandler;
	/** 常用进度条，如：网络加载时所用的加载框 **/
	private TXProgressDialog progressDialog;
	/** 返回类型 默认 1:返回上个页面 2:连续返回两次就可以退出 3:返回时退出，但是需要二次确认 4:页面自行处理返回操作 **/
	private int backType = 1;
	/** 返回两次就退出app **/
	private int exitCount = 0;
	/** 屏幕参数 */
	public DisplayMetrics mDisplayMetrics = null;
	/** 根布局 */
	public FrameLayout containerRoot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (TXAbsApplication) getApplication();
		// 添加Activity
		mApplication.addActivity(this);
		// 注册事件理处回调
		EventBus.getDefault().register(this);
		// 创建主线程消息处理器
		mMainHandler = new Handler(this);

		mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

		Looper looper = mApplication.getWorkLooper();
		// 创建后台线程消息处理器
		mWorkHandler = new Handler(looper, new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				return handleAsynMsg(msg);
			}
		});

		// 初始化加载框
		progressDialog = TXProgressDialog.createDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		setContentView(R.layout.activity_main);
		//containerRoot = (FrameLayout) findViewById(R.id.container);
		// 子类设置布局
		//View contentView = inflateContentView();
		//containerRoot.addView(contentView);
		// 子类设置布局
		setContentView();
		// 子类初始化界面
		initView();
		// 子类初始化数据
		initData();

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
		// 删除Activity
		mApplication.removeActivity(this);
	}

	/**
	 * 设置acitivty的布局文件
	 */
	//public abstract View inflateContentView();
	
	/**
	 * 设置acitivty的布局文件
	 */
	public abstract void setContentView();

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	/**
	 * 实始化界面，如:findViewById, setOnClickListener
	 */
	public abstract void initView();

	/**
	 * 更新界面，如:activity从后台进入时在onResume中调用
	 */
	public abstract void updateView();

	/**
	 * 处理后台线程中的消息
	 * 
	 * @param msg
	 * @return
	 */
	public abstract boolean handleAsynMsg(Message msg);

	/**
	 * 消息处理回调函数，用于入理EventBus.getDefault().post(evt)传递过来的事件
	 * 
	 * @param evt
	 */
	public abstract void onEventMainThread(Object evt);

	/**
	 * 显示dialog 但是返回时，留在当前页面
	 */
	public void showLoadingAndStay() {
		progressDialog.setMessage(getString(R.string.tip_loading));
		progressDialog.show();
	}

	/**
	 * 显示dialog 并且屏蔽返回按钮
	 */
	public void showLoadingAndDisableKey() {
		progressDialog.setMessage(getString(R.string.tip_loading));
		progressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_MENU)) {
					return true;
				}
				return false;
			}
		});
		progressDialog.show();

	}

	/**
	 * 显示dialog但是返回时，返回上个页面
	 */
	public void showLoading() {
		progressDialog.setMessage(getString(R.string.tip_loading));
		progressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.dismiss();
					backPage();
					return true;
				}
				return false;
			}
		});
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
	 * 显示dialog 并且屏蔽返回按钮
	 * 
	 * @param content
	 *            加载显示的内容
	 */
	public void showLoadingAndDisableKey(String content) {
		progressDialog.setMessage(content);
		progressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_MENU)) {
					return true;
				}
				return false;
			}
		});
		progressDialog.show();
	}

	/**
	 * 显示dialog但是返回时，返回上个页面
	 * 
	 * @param content
	 *            加载显示的内容
	 */
	public void showLoading(String content) {
		progressDialog.setMessage(content);
		progressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.dismiss();
					backPage();
					return true;
				}
				return false;
			}
		});
		progressDialog.show();
	}

	/**
	 * 隐藏dialog
	 */
	public void hideLoading() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 返回上个页面
	 */
	public void backPage() {
		if (backType == 1) {
			// 普通的返回
			finish();
		} else if (backType == 2) {
			// 需要连续点击两次返回
			exitCount++;
			if (exitCount == 1) {
				Toast.makeText(this, getString(R.string.tip_exit), Toast.LENGTH_SHORT).show();
				new CountDownTimer(2000, 500) {
					public void onTick(long millisUntilFinished) {

					}

					public void onFinish() {
						exitCount = 0;
					}

				}.start();
			}
			if (exitCount == 2) {
				// 退出程序
				exitApp();
			}
		} else if (backType == 3) {
			// 返回需要二次确认
			showExitDialogBy();
		} else if (backType == 4) {
			// 页面自行处理
			processBackPage();
		}

	}

	/**
	 * 退出确认框
	 */
	public void showExitDialogBy() {
		// 系统默认类型
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.tip_exit);
		builder.setTitle(getString(R.string.dialog_title));
		builder.setPositiveButton(getString(R.string.btn_sure), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				exitApp();
			}
		});
		builder.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();

	}

	/**
	 * 退出整个APP，做一些清理工作
	 */
	public void exitApp() {
		// mApplication.exitApp();
	}

	/**
	 * 处理点击返回键时的操作
	 */
	public void processBackPage() {

	}

	/**
	 * 设置页面返回类型
	 * 
	 * @param backType
	 *            1:返回上个页面 <br>
	 *            2:连续返回两次就可以退出 <br>
	 *            3:返回时退出，但是需要二次确认 <br>
	 *            4:页面自行处理返回操作
	 */
	public void setBackType(int backType) {
		this.backType = backType;
	}

}
