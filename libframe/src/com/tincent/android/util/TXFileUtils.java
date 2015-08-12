/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

/**
 * 听讯科技：文件处理工具类
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXFileUtils {

	/**
	 * 保存位图到文件
	 * 
	 * @param bm
	 * @param path
	 * @param picName
	 * @return
	 */
	public static String saveBitmap(Bitmap bm, String path, String picName) {
		try {
			createDir(path);// 创建目录
			File f = new File(path, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 60, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path + picName + ".JPEG";
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 * @return
	 */
	public static File createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				dir.mkdir();
			}
		}
		return dir;
	}

	/**
	 * 删除目录
	 * 
	 * @param path
	 */
	public static void deleteDir(String path) {
		File dir = new File(path);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(path); // 递规的方式删除文件夹2
		}
		dir.delete();// 删除目录本身
	}

	/**
	 * 删除目录内容
	 * 
	 * @param path
	 */
	public static void deleteDirContent(String path) {
		File dir = new File(path);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDirContent(file.getPath()); // 递规的方式删除文件夹
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * 文件转换为字符串
	 * 
	 * @param path
	 * @return
	 */
	public static String fileToString(String path) {

		FileInputStream fis = null;
		String uploadBuffer = "";

		try {
			fis = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			TXGZipUtils.compress(fis, baos);
			uploadBuffer = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT)); // 进行Base64编码
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return uploadBuffer;
	}
}
