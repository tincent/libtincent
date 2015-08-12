/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.io.BufferedOutputStream;
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
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tincent.android.TXConstants;

/**
 * 听讯科技：图片异步加载缓存类
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
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024))
				// .diskCacheSize(50 * 1024 * 1024)
				.diskCache(new UnlimitedDiscCache((new File(cacheDir)), new File(reserveCacheDir))).diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
																					// for
																					// release
																					// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
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

	/**
	 * 
	 * @param bitmap存文件
	 */
	public static String saveImage(Bitmap bitmap, String dir) {
		File file = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null; // 字节数组输出流
		try {
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] byteArray = baos.toByteArray();// 字节数组输出流转换成字节数组
			String picName = dir + System.currentTimeMillis() + ".JPEG";
			file = new File(picName);
			// 将字节数组写入到刚创建的图片文件中
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(byteArray);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return file.getPath();

	}

	/**
	 * 压缩图片尺寸
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] compressImageFromFile(String file) {
		TXDebug.o(new Exception(), "image file size = " + new File(file).length() / 1024);
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(file, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		TXDebug.o(new Exception(), "origin image width : " + w + ", height : " + h);
		float height = 960f;//
		float weight = 540f;//
		// float height = 800f;//
		// float weight = 480f;//
		int ratio = 1;
		if (w > h && w > weight) {
			ratio = (int) (newOpts.outWidth / weight);
		} else if (w < h && h > height) {
			ratio = (int) (newOpts.outHeight / height);
		}
		if (ratio <= 0) {
			ratio = 1;
		}

		newOpts.inSampleSize = ratio;// 设置采样率

		newOpts.inPreferredConfig = Config.RGB_565;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(file, newOpts);
		TXDebug.o(new Exception(), "after measure compress image width : " + bitmap.getWidth() + ", height : " + bitmap.getHeight() + ", ratio : " + ratio);
		return compressImageFromBitmap(bitmap);
	}

	/**
	 * 压缩图片质量
	 * 
	 * @param bmp
	 * @return
	 */
	public static byte[] compressImageFromBitmap(Bitmap bmp) {
		TXDebug.o(new Exception(), "before quality compress, size = " + bmp.getByteCount() / 1024);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 个人喜欢从80开始,
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		TXDebug.o(new Exception(), "quality options = " + options + ", size = " + baos.toByteArray().length / 1024);
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			TXDebug.o(new Exception(), "quality options = " + options + ", size = " + baos.toByteArray().length / 1024);
		}
		return baos.toByteArray();
	}
}
