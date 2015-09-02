/**
 * 
 */
package com.tincent.demo.service;

import java.io.File;

import com.tincent.android.service.TXAbsService;
import com.tincent.android.task.TXDownloadTask;
import com.tincent.android.task.TXDownloadTask.DownloadListener;
import com.tincent.android.util.TXMd5Util;
import com.tincent.android.util.TXShareFileUtil;
import com.tincent.demo.Constants;
import com.tincent.demo.R;
import com.tincent.demo.activity.HomeActivity;
import com.tincent.demo.util.Debug;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 后台下载服务
 * 
 * @author hui.wang
 * @date 2015.4.25
 * 
 */
public class DownloadService extends TXAbsService implements DownloadListener {
	private Builder builder;

	private boolean autoDownload;

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void registerListener() {

	}

	@Override
	public void unregisterListener() {

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			handleIntent(intent);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void handleIntent(Intent intent) {
		String path = intent.getStringExtra(Constants.KEY_APK_URL);
		String dir = intent.getStringExtra(Constants.KEY_FILE_DIR);
		String subfix = intent.getStringExtra(Constants.KEY_FILE_SUBFIX);
		autoDownload = intent.getBooleanExtra(Constants.KEY_AUTO_DOWNLOAD, true);

		TXDownloadTask task = new TXDownloadTask();
		task.setDownloadListener(this);
		task.execute(path, dir, subfix);
	}

	@Override
	public void onPreExecute() {
		if (!autoDownload) {
			showNotification();
		}

	}

	@Override
	public void onProgressUpdate(int progress) {
		if (!autoDownload) {
			updateNotication(progress);
		}

	}

	@Override
	public void onPostExecute(String file) {
		if (!autoDownload) {
			hideNotification();
			if(!TextUtils.isEmpty(file)) {
				installApk(file);
			}
		} else {
			if(!TextUtils.isEmpty(file)) {
				String md5 = TXMd5Util.getFileMD5(new File(file));
				TXShareFileUtil.getInstance().putString(Constants.KEY_APK_URL, file);
				TXShareFileUtil.getInstance().putString(Constants.KEY_APK_MD5, md5);
			}
		}

		stopSelf();
	}

	/**
	 * 显示状态栏通知
	 */
	private void showNotification() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		builder = new Notification.Builder(this).setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.apk_downloading)).setAutoCancel(false)
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent).setProgress(100, 0, false);
		mNotificationManager.notify(1, builder.build());
	}

	/**
	 * 更新状态栏进度
	 * 
	 * @param progress
	 */
	private void updateNotication(int progress) {
		// Debug.o(new Exception(), "progress = " + progress);
		builder.setProgress(100, progress, false);
		mNotificationManager.notify(1, builder.build());
	}

	/**
	 * 隐藏状态栏通知
	 */
	private void hideNotification() {
		Toast.makeText(this, R.string.apk_download_finish, Toast.LENGTH_LONG).show();
		mNotificationManager.cancel(1);
	}

	/**
	 * 请求安装apk
	 * 
	 * @param file
	 */
	private void installApk(String file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(file));
		Debug.o(new Exception(), "uri = " + uri);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onEventMainThread(Object evt) {

	}

}
