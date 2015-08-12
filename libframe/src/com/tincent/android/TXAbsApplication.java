/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android;

import java.util.ArrayList;
import java.util.List;

import com.tincent.android.util.TXDebug;
import com.tincent.android.util.TXShareFileUtil;
import com.tincent.android.util.TXToastUtil;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 听讯科技：抽象应用全局类
 * 
 * @author hui.wang
 * @date 2015.3.5
 */
public abstract class TXAbsApplication extends Application implements Runnable {
	private static final String TAG = TXAbsApplication.class.getName();
	// 当前打开的Activity列表
	private List<Activity> activities;
	// 后台线程消息处理器
	private Handler workHandler;
	// 线程的循环体
	private Looper looper;
	// 具有循环对列的后台线程
	private HandlerThread handlerThread;

	@Override
	public void onCreate() {
		super.onCreate();
		TXDebug.o(new Exception());
		// 创建列表容器
		activities = new ArrayList<Activity>();
		// 注册事件理处回调
		EventBus.getDefault().register(this);
		// 初始化ShareFile文件实例
		TXShareFileUtil.getInstance().init(this);
		// 初始化提示工具实例
		TXToastUtil.getInstatnce().init(this);
		// 创建具有循环对列的后台线程
		handlerThread = new HandlerThread(TAG);
		// 开始后台线程体
		handlerThread.start();
		// 获取后台线程的循环体
		looper = handlerThread.getLooper();
		// 创建后台线程相关的Handler外理器
		workHandler = new Handler(looper);
		// 投递给后台线程一个runnable运行体，用来做一些初使化较耗时的动作
		workHandler.post(this);

	}

	/**
	 * 添加己打开的Activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	/**
	 * 删除关闭的 Activity
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	/**
	 * 退出APP
	 */
	public void exitApp() {
		TXDebug.o(new Exception());
		for (Activity act : activities) {
			act.finish();
		}
	}

	/**
	 * 结束除过当前Activity以外的其它Activity
	 * 
	 * @param activity
	 */
	public void finishOthers(Activity activity) {
		for (Activity act : activities) {
			if (!act.equals(activity)) {
				act.finish();
			}
		}
	}

	/**
	 * 结束当前Activity
	 * 
	 * @param activity
	 */
	public void finishSelf(Activity activity) {
		activity.finish();
	}

	/**
	 * 返回后台线程的Handler处理器
	 * 
	 * @return
	 */
	public Handler getWorkHandler() {
		return workHandler;
	}

	/**
	 * 返回后台线程的循环体
	 * 
	 * @return
	 */
	public Looper getWorkLooper() {
		return looper;
	}

	/**
	 * 消息处理回调函数，用于入理EventBus.getDefault().post(evt)传递过来的事件
	 * 
	 * @param evt
	 */
	public abstract void onEventMainThread(Object evt);

}
