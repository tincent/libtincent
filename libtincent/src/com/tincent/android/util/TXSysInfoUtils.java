/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 听讯科技：获取手机系统和sim卡相关信息
 * 
 * @author peng.zhang
 * @date 2015.3.17
 */
@SuppressLint("NewApi")
public class TXSysInfoUtils {
	private static TelephonyManager telephonyManager = null;// 电话服务

	/**
	 * 获取当前操作系统的语言
	 * 
	 * @return String 系统语言
	 */
	public static String getSysLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return String 手机型号
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取操作系统的版本号
	 * 
	 * @return String 系统版本号
	 */
	public static String getSysRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 读取sim卡序列号
	 */
	public static String readSimSerialNum(Context con) {
		String number = getTelephonyManager(con).getSimSerialNumber();
		return number != null ? number : "";
	}

	/**
	 * 获得电话管理实例对象
	 * 
	 * @param con
	 *            上下文
	 * @return 实例对象
	 */
	private static TelephonyManager getTelephonyManager(Context con) {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
			return telephonyManager;
		} else {
			return telephonyManager;
		}
	}

	/**
	 * 读唯一的设备ID
	 * 
	 * @param con
	 *            上下文
	 * @return 唯一的设备ID IMEI GSM手机的 IMEI 和 CDMA手机的 MEID. 如果获取不到返回一个默认字符串
	 */
	public static String readTelephoneSerialNum(Context con) {
		String telephoneSerialNumber = getTelephonyManager(con).getDeviceId();
		return telephoneSerialNumber != null ? telephoneSerialNumber : "000000000000000";
	}

	/**
	 * 获取系统当前时间，精确到秒
	 * 
	 * @return 返回当前时间字符串
	 */
	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取系统当前时间戳
	 * 
	 * @return
	 */
	public static long getNowTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取运营商信息
	 * 
	 * @param con
	 *            上下文
	 * @return String 运营商信息
	 */
	public static String getCarrier(Context con) {
		TelephonyManager telManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId();
		if (imsi != null && imsi.length() > 0) {
			// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				return "China Mobile";
			} else if (imsi.startsWith("46001")) {
				return "China Unicom";
			} else if (imsi.startsWith("46003")) {
				return "China Telecom";
			}
		}
		return "未能识别";
	}

	/**
	 * 是否存在Sd卡
	 * 
	 * @return true 存在 ；false 不存在
	 */
	public static boolean getSDState() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取SD卡剩余空间的大小
	 * 
	 * @return long SD卡剩余空间的大小（单位：byte）
	 */
	public static long getSDSize() {
		String str = Environment.getExternalStorageDirectory().getPath();
		StatFs localStatFs = new StatFs(str);
		long blockSize = localStatFs.getBlockSize();
		return localStatFs.getAvailableBlocks() * blockSize;
	}

	/**
	 * 获取SD的路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return "";
	}

	/**
	 * 电话状态
	 * 
	 * @param con
	 *            上下文
	 * @return 0：无活动 1：响铃 2：待机
	 */
	public static int getPhoneState(Context con) {
		return getTelephonyManager(con).getCallState();
	}

	/**
	 * 获得电话方位
	 * 
	 * @param con
	 *            上下文
	 * @return 方位对象
	 */
	public static CellLocation getPhoneLoaction(Context con) {
		CellLocation cellLocation = getTelephonyManager(con).getCellLocation();
		return (CellLocation) (cellLocation != null ? cellLocation : "");
	}

	/**
	 * 设备的软件版本号： the IMEI/SV(software version) for GSM phones.
	 * 
	 * @param con
	 *            上下文
	 * @return 不支持返回“not available”
	 */
	public static String getDeviceSoftVersion(Context con) {
		String version = getTelephonyManager(con).getDeviceSoftwareVersion();
		return version != null ? version : "not available";
	}

	/**
	 * 获得手机号
	 * 
	 * @param con
	 *            上下文
	 * @return 手机号 不支持就返回“12322344345”
	 */
	public static String getPhoneNumber(Context con) {
		String number = getTelephonyManager(con).getLine1Number();
		return number != null ? number : "12322344345";
	}

	/**
	 * 获得SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字. SIM卡的状态必须是
	 * SIM_STATE_READY(使用getSimState()判断).
	 * 
	 * @param con
	 *            上下文
	 * @return 例：46002
	 */
	public static String getSimCode(Context con) {
		if (getTelephonyManager(con).getSimState() == 5) {
			String code = getTelephonyManager(con).getSimOperator();
			return code != null ? code : "";
		} else {
			return "";
		}
	}

	/**
	 * 服务商名称：例如：中国移动、联通 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
	 * 
	 * @param con
	 *            上下文
	 * @return 服务商名称
	 */
	public static String getSimPrivatorName(Context con) {
		if (getTelephonyManager(con).getSimState() == 5) {
			String name = getTelephonyManager(con).getSimOperatorName();
			return name != null ? name : "";
		} else {
			return "";
		}
	}

	/**
	 * 唯一的用户ID 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
	 * 
	 * @param con
	 *            上下文
	 * @return
	 */
	public static String getUserPhoneId(Context con) {
		return getTelephonyManager(con).getSubscriberId();
	}

	/**
	 * 获取屏幕管理类
	 * 
	 * @return DisplayMetrics 屏幕管理对象
	 */
	public static DisplayMetrics getDisplayMetrics(Activity context) {
		DisplayMetrics displayMetrics = null;
		if (displayMetrics == null) {
			displayMetrics = new DisplayMetrics();
		}
		context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	/**
	 * 获取屏幕密度
	 * 
	 * 
	 * @return DisplayMetrics 屏幕管理对象
	 */
	public static float getScaledDensity(Activity context) {
		DisplayMetrics displayMetrics = getDisplayMetrics(context);
		// 获取失败则返回中320x480分辨率密度
		if (displayMetrics == null) {
			return 1.0f;
		}
		return displayMetrics.scaledDensity;
	}

	/**
	 * 读取设备的网卡地址
	 * 
	 * @param context
	 * @return mac地址
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null && info.getMacAddress() != null) {
			return info.getMacAddress();
		} else {
			return "";
		}

	}

	/**
	 * 读取设备的蓝牙地址
	 * 
	 * @return 蓝牙地址
	 */
	@SuppressLint("NewApi")
	public static String getBluetoothAddress() {
		return BluetoothAdapter.getDefaultAdapter().getAddress();
	}

	/**
	 * 是否存在sim卡
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isExistSim(Context context) {
		TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		if (mTelephonyManager.getSimState() != TelephonyManager.SIM_STATE_READY) // SIM卡没有就绪
		{
			return false;
		}
		return true;
	}

	/**
	 * 判断当前运行环境是否为模拟器
	 * 
	 * @return true 当前设备为模拟器 false 当前设备为真机
	 */
	public static boolean isEmulator() {
		return ("unknown".equals(Build.BOARD)) && ("generic".equals(Build.DEVICE)) && ("generic".equals(Build.BRAND));
	}

	/**
	 * 判断获取当前屏幕的密度
	 * 
	 * @return 屏幕密度
	 */
	public static float getDensity(Activity activity) {
		/** 初始化设备屏幕参数 */
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.density;
	}

	/**
	 * @return 屏幕宽度
	 */
	public static int getScreenWidth(Activity activity) {
		int screenWidth = 0;
		if (getDisplayMetrics(activity).widthPixels > 900) {
			screenWidth = 1080;
		} else if (getDisplayMetrics(activity).widthPixels > 700 && getDisplayMetrics(activity).widthPixels <= 900) {
			screenWidth = 720;
		} else if (getDisplayMetrics(activity).widthPixels >= 500 && getDisplayMetrics(activity).widthPixels <= 700) {
			screenWidth = 640;
		} else {
			screenWidth = 480;
		}

		return screenWidth;
	}

	public static boolean hasEclair() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
	}

	public static boolean hasFroyo() {

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param act
	 * @param v
	 */
	public static void hideSoftInput(final Activity act) {

		((InputMethodManager) act.getSystemService(act.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(act.getWindow().peekDecorView().getWindowToken(), 0);
	}

	/**
	 * 显示软键盘
	 * 
	 * @param context
	 * @param et
	 */
	public static void showSoftInput(Context context, final EditText et) {
		if (context == null || et == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 更具包名判断是否已经安装该app
	 */
	public static boolean isApkExit(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
		} catch (NameNotFoundException e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取软件版本号
	 *
	 * @param context
	 * @return 版本号,如：100
	 */
	public static int getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取版本名
	 *
	 * @param context
	 * @return 版本名,如：1.0.0
	 */
	public static String getVersionName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
