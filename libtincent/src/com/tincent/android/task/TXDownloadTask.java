/**
 * 
 */
package com.tincent.android.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.tincent.android.util.TXDebug;
import com.tincent.android.util.TXFileUtils;
import com.tincent.android.util.TXTimeUtil;

import android.os.AsyncTask;

/**
 * 文件下载任务
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXDownloadTask extends AsyncTask<String, Integer, String> {
	private DownloadListener downlaodListener;

	@Override
	protected String doInBackground(String... params) {
		/*
		 * int count = urls.length; long totalSize = 0; for (int i = 0; i <
		 * count; i++) { totalSize += downloadApk(urls[i]); //
		 * publishProgress((int) ((i / (float) count) * 100)); // Escape early
		 * if cancel() is called if (isCancelled()) break; } return totalSize;
		 */

		return downloadFile(params[0], params[1], params[2]);
	}

	public void setDownloadListener(DownloadListener listener) {
		downlaodListener = listener;
	}

	@Override
	protected void onPreExecute() {
		TXDebug.o(new Exception());
		if (downlaodListener != null) {
			downlaodListener.onPreExecute();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		// Debug.o(new Exception(), "progress = " + progress[0]);
		if (downlaodListener != null) {
			downlaodListener.onProgressUpdate(progress[0]);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		TXDebug.o(new Exception(), "result = " + result);
		if (downlaodListener != null) {
			downlaodListener.onPostExecute(result);
		}
	}

	/**
	 * 文件下载方法
	 * 
	 * @param path
	 *            网络文件路径
	 * @param dir
	 *            文件本地存储路径
	 * @param suffix
	 *            下载的文件后缀名
	 * @return 下载后的文件路径
	 */
	private String downloadFile(String path, String dir, String suffix) {
		try {
			URL url;
			if (path.startsWith("http://")) {
				url = new URL(path);
			} else {
				url = new URL("http://" + path);
			}

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();

			TXFileUtils.createDir(dir);
			String apkName = TXTimeUtil.format2YMD(System.currentTimeMillis())
					+ suffix;
			File file = new File(dir, apkName);

			TXDebug.o(new Exception(), "file = " + file);

			FileOutputStream fos = new FileOutputStream(file);

			int count = 0;
			byte buf[] = new byte[1024];

			int lastProgress = -1;

			do {
				int numread = is.read(buf);

				if (numread == -1) {
					break;
				}

				count += numread;

				int progress = (int) (((float) count / length) * 100);
				if (progress != lastProgress) {
					publishProgress(progress);
					lastProgress = progress;
				}

				fos.write(buf, 0, numread);
			} while (!isCancelled());

			fos.close();
			is.close();

			return file.getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 下载监听器接口
	 * 
	 * @author hui.wang
	 * @date 2015.3.16
	 */
	public interface DownloadListener {

		/**
		 * 下载开始前回调
		 */
		public void onPreExecute();

		/**
		 * 下载过程中进度更新
		 * 
		 * @param progress
		 */
		public void onProgressUpdate(int progress);

		/**
		 * 下载成功后回调
		 * 
		 * @param file
		 *            下载后文件存存储路径
		 */
		public void onPostExecute(String file);
	}

}
