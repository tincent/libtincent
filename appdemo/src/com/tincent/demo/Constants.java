/**
 * 
 */
package com.tincent.demo;

import android.os.Environment;

/**
 * 常量类
 * 
 * @author hui.wang
 * 
 */
public class Constants {
	/**
	 * 是否开启应用调试模式
	 */
	public static final boolean DEBUG = true;
	/**
	 * 网络超时消息
	 */
	public static final int MSG_NETWORK_TIMEOUT = 0;
	/**
	 * 隐藏加载框
	 */
	public static final int MSG_HIDE_LOADING = 1;
	/**
	 * 显示加载框
	 */
	public static final int MSG_SHOW_LOADING = 2;
	/**
	 * 网络请求超时周期
	 */
	public static final int HTTP_REQUEST_TIMEOUT_DURATION = 10000;
	/**
	 * 微信分享APP标识
	 */
	public static final String WEIXIN_APP_ID = "wx22520fdd33fd806a";
	/**
	 * 网页标题
	 */
	public static final String KEY_WEBVIEW_TITLE = "webview_title";
	/**
	 * 网页地址
	 */
	public static final String KEY_WEB_URL = "web_url";
	/**
	 * 内存卡 根目录
	 */
	public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
	/**
	 * app根文件夹
	 */
	public static final String APP_DIR = ROOT_DIR + "/appdemo/";
	/**
	 * app音乐文件夹
	 */
	public static final String MUSIC_DIR = APP_DIR + "music/";
	/**
	 * app图片文件夹
	 */
	public static final String IMAGE_DIR = APP_DIR + "image/";
	/**
	 * app视频文件夹
	 */
	public static final String VIDEO_DIR = APP_DIR + "video/";
	/**
	 * app缓冲区文件夹
	 */
	public static final String CACHE_DIR = APP_DIR + "cache/";
	/**
	 * app安装包文件夹
	 */
	public static final String APK_DIR = APP_DIR + "apk/";
	/**
	 * 安装包后缀
	 */
	public static final String APK_SUBFIX = ".apk";
	/**
	 * app log文件夹
	 */
	public static final String LOG_DIR = APP_DIR + "log/";
	/**
	 * app 数据库文件夹
	 */
	public static final String DB_DIR = APP_DIR + "db/";
	/**
	 * 安装包地址
	 */
	public static final String KEY_APK_URL = "apk_url";
	/**
	 * 安装包版本号
	 */
	public static final String KEY_APK_CODE = "apk_code";
	/**
	 * 安装包MD5
	 */
	public static final String KEY_APK_MD5 = "apk_md5";
	/**
	 * 文件目录
	 */
	public static final String KEY_FILE_DIR = "file_dir";
	/**
	 * 文件后缀
	 */
	public static final String KEY_FILE_SUBFIX = "file_subfix";
	/**
	 * 自动下载
	 */
	public static final String KEY_AUTO_DOWNLOAD = "auto_download";

}
