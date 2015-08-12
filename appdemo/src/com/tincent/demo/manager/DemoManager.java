/**
 * 
 */
package com.tincent.demo.manager;

import com.tincent.android.util.TXFileUtils;
import com.tincent.demo.Constants;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * 示例程序管理器
 * 
 * @author hui.wang
 * 
 */
public class DemoManager {

	private Context context;

	private static DemoManager instance;

	public static DemoManager getInstance() {
		if (instance == null) {
			instance = new DemoManager();
		}
		return instance;
	}

	private DemoManager() {

	}

	public void init(Context ctx) {
		context = ctx;
		createAppDir(ctx);
	}

	/**
	 * 创建APP常用目录
	 * 
	 * @param ctx
	 */
	public static void createAppDir(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().canWrite()) {
			TXFileUtils.createDir(Constants.APP_DIR);
			TXFileUtils.createDir(Constants.IMAGE_DIR);
			TXFileUtils.createDir(Constants.MUSIC_DIR);
			TXFileUtils.createDir(Constants.VIDEO_DIR);
			TXFileUtils.createDir(Constants.CACHE_DIR);
			TXFileUtils.createDir(Constants.APK_DIR);
			TXFileUtils.createDir(Constants.LOG_DIR);
			TXFileUtils.createDir(Constants.DB_DIR);
		} else {
			Toast.makeText(context, "SD卡不可写", Toast.LENGTH_LONG).show();
		}
	}

}
