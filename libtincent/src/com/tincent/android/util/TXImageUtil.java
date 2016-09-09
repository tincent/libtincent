/**
 * 
 */
package com.tincent.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import com.tincent.android.TXConstants;

/**
 * 图片异步加载缓存类
 * 
 * @author hui.wang
 * @date 2015.3.20
 * 
 */
public class TXImageUtil {
	private static TXImageUtil imageUtil;

	private Context context;

	private TXImageUtil() {

	}

	public static TXImageUtil getInstance() {
		if (imageUtil == null) {
			imageUtil = new TXImageUtil();
		}
		return imageUtil;
	}

	/**
	 * 初始化ImageLoader
	 * 
	 * @param ctx
	 * @param cacheDir
	 *            缓存目录
	 */
	public void init(Context ctx, String cacheDir) {
		context = ctx;
		initImageLoader(ctx, cacheDir);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	private void initImageLoader(Context context, String cacheDir) {
		if (TXConstants.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());

		}
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		String reserveCacheDir = "/data/data/" + context.getPackageName() + "/cache/";
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCache(new UnlimitedDiskCache((new File(cacheDir)), new File(reserveCacheDir)));
		config.memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024));
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	/**
	 * 图片下载
	 * 
	 * @param dir
	 *            下载文件存储目录
	 * @param path
	 *            网络文件路径
	 * @return 下载的文件
	 */
	public File downloadImage(String dir, String path) {

		// http协议连接对象
		HttpURLConnection conn;
		try {
			URL url = new URL(path);// 获取到路径
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");// 这里是不能乱写的，详看API方法
			conn.setConnectTimeout(6 * 1000);
			// 别超过10秒。
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				String filename = TXMd5Util.md5(url.toString()) + ".jpg";
				byte[] data = readStream(inputStream);
				File file = new File(dir + filename);// 给图片起名字
				FileOutputStream outStream = new FileOutputStream(file);// 写出对象
				outStream.write(data);// 写入
				outStream.close(); // 关闭流
				return file;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 读取文件流返回字节码
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	private byte[] readStream(InputStream inStream) throws IOException {

		byte[] buffer = new byte[1024]; // 用数据装װ
		int len = -1;
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		inStream.close();
		// 关闭流一定要记得。
		return outstream.toByteArray();
	}
}
