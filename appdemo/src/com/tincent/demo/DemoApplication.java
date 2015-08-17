/**
 * 
 */
package com.tincent.demo;

import com.lidroid.xutils.bitmap.BitmapGlobalConfig;
import com.tincent.android.TXAbsApplication;
import com.tincent.android.util.TXImageUtil;
import com.tincent.android.util.TXToastUtil;
import com.tincent.demo.manager.DemoManager;
import com.tincent.demo.util.DatabaseUtil;

/**
 * 应用全局类
 * 
 * @author hui.wang
 * 
 */
public class DemoApplication extends TXAbsApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		// 初始应用管理器
		DemoManager.getInstance().init(this);
		// 初始化图片加载器，并且创建缓存目录
		TXImageUtil.getInstance().init(this, Constants.CACHE_DIR);
		// 初始化Xutils图片加载器
		BitmapGlobalConfig.getInstance(this, Constants.CACHE_DIR);
	}

	@Override
	public void run() {
		// 初始化耗时操作，如：创建或更新数据库
		DatabaseUtil.getInstatnce().init(this);
		TXToastUtil.getInstatnce().showMessage("听讯科技示例程序己启动");
		
	}

	@Override
	public void onEventMainThread(Object evt) {
		// 接收感兴趣的全部事件
	}

}
