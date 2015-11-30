package com.tincent.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;

/**
 * 字符处理工具类
 * 
 * @author chen.zhang
 * @version 1.0.0
 */
public class TXStringUtils {

	public SharedPreferences mPreference = null;// 共享文件

	private static final TXStringUtils utils = new TXStringUtils();

	public static TXStringUtils getInstance() {
		return utils;
	}

	/**
	 * 保留小数点后指定位数小数
	 * 
	 * @param pDouble
	 *            格式化之前的数值
	 * @param saveNum
	 *            保存多少位小数点
	 * @return 格式化之后的数字
	 */
	public static double numberFormat(double pDouble, int saveNum) {
		BigDecimal bd = new BigDecimal(pDouble);
		BigDecimal bd1 = bd.setScale(saveNum, BigDecimal.ROUND_HALF_UP);
		pDouble = bd1.doubleValue();
		return pDouble;
	}

	/**
	 * 格式化价格：1,000,000.00
	 * 
	 * @param str
	 *            需要格式化的价格,参数传入之前，请确认是一个正常的价格数字
	 * @return 格式化之后的字符串
	 */
	public static String formatPrice(String str) {
		String[] strArray = (str + "").split("\\.");
		str = strArray[0];
		if (strArray.length >= 1) {
			if (!TextUtils.isEmpty(str)) {
				StringBuilder builder = new StringBuilder(str);
				int interval = 3;
				for (int i = str.length() - 1; i > 0; i--) {
					interval--;
					if (0 == interval) {
						interval = 3;
						builder.insert(i, ",");
					}
				}
				if (strArray.length > 1) {
					String temp = strArray[1];
					if (null == temp || 0 == temp.length()) {
					} else if (1 == temp.length()) {
						builder.append(".").append(temp);
					} else {
						builder.append("." + temp.substring(0, 1));
					}
				}
				return builder.toString();
			}
		}

		return "";
	}

	/**
	 * 格式化速率: 23'15"\00'12"\00'00"
	 * 
	 * @param s
	 *            输入速率
	 * @return 格式化之后的速率字符串
	 */
	public String formatRate(double s) {
		String sb = "";
		String[] str = (s + "").split("\\.");
		if (str.length == 2) {
			String str1 = "";
			String str2 = "";
			if (str[0].length() == 0) {
				str1 = "00";
			} else if (str[0].length() == 1) {
				str1 = "0" + str[0];
			} else {
				str1 = str[0];
			}

			if (str[1].length() == 0) {
				str2 = "00";
			} else if (str[1].length() == 1) {
				str2 = "0" + str[1].substring(0);
			} else if (str[1].length() > 1) {
				str2 = str[1].substring(0, 2);
			}
			sb = str1 + "'" + str2 + "&apos;&apos;";
		} else if (str.length == 1) {
			String str1 = "";
			if (str[0].length() == 0) {
				str1 = "00";
			} else if (str[0].length() == 1) {
				str1 = "0" + str[0];
			} else {
				str1 = str[0];
			}
			sb = str1 + "'" + "00&apos;&apos;";
		} else if (str.length == 0) {
			sb = "00'" + "00&apos;&apos;";
		}
		return Html.fromHtml(sb).toString();
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>Util.isEmpty(null) = true</li>
	 * <li>Util.isEmpty("") = true</li>
	 * <li>Util.isEmpty("   ") = true</li>
	 * <li>Util.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将Assists 中文件读取为字符串
	 * 
	 * @param context
	 *            当前调用的activity
	 * @param assistFile
	 *            需要转化的assets文件夹下的文件名称
	 * @return
	 * @throws IOException
	 */
	public String getAssetsFileAsString(Context context, String assistFile) throws IOException {
		String result = null;
		AssetManager assetManager = context.getAssets();
		result = inputStreamToString(assetManager.open(assistFile));
		return result;
	}

	/**
	 * 将inputStream 以系统默认编码转换为字符串
	 * 
	 * @param 字节流
	 * 
	 */
	public String inputStreamToString(InputStream is) throws IOException {
		byte arr[] = new byte[1024];
		ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(1024);
		int len = 0;
		try {
			while ((len = is.read(arr)) != -1) {
				byteArrayBuffer.append(arr, 0, len);
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// do nothing
			}
		}
		return new String(byteArrayBuffer.toByteArray());
	}

	/***
	 * 判断字符串是不是integer
	 * 
	 * @param str
	 *            需验证的字符串
	 * @return 是否是数字
	 */
	public static boolean isNumber(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		if (str.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

	/**
	 * 半角字符转换为全角字符。
	 * 
	 * @param input
	 *            需转换的字符串
	 * @return
	 */
	public static String ToFullCharacters(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 对字符串MD5加密
	 * 
	 * @param 加密前的字符串
	 * @return 加密后的字符串
	 */
	public static String encryptMd5(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(str.getBytes());
			byte[] mess = digest.digest();
			return toHexString(mess);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 转化规则
	 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 加密辅助类。提供将byte数组转换为ASCII文本
	 * 
	 * @param 转换前的byte字符
	 * @return 转换之后的字符
	 */
	public static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 空字符串 格式化
	 * 
	 * @param text
	 *            内容
	 * @return
	 */
	public static String formatNull(String text) {
		return text == null ? "" : text;
	}

	/**
	 * 检查手机格式
	 * 
	 * @param
	 */
	public static boolean isCellPhoneNumber(String cellPhoneNumber) {
		// 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
		String strPattern = "^[1][\\d]{10}";
		// 利用了Java里面的正则表达式
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(cellPhoneNumber);
		// 当目标string与传入的string完全匹配，返回true,否则返回false
		return m.matches();
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String doEmpty(String str) {
		return doEmpty(str, "");
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String doEmpty(String str, String defaultValue) {
		if (str == null || str.equalsIgnoreCase("null")
				|| str.trim().equals("") || str.trim().equals("－请选择－")) {
			str = defaultValue;
		} else if (str.startsWith("null")) {
			str = str.substring(4, str.length());
		}
		return str.trim();
	}

	/**
	 * 请选择
	 */
	final static String PLEASE_SELECT = "请选择...";

	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o.toString().trim())
				&& !"null".equalsIgnoreCase(o.toString().trim())
				&& !"undefined".equalsIgnoreCase(o.toString().trim())
				&& !PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean empty(Object o) {
		return o == null || "".equals(o.toString().trim())
				|| "null".equalsIgnoreCase(o.toString().trim())
				|| "undefined".equalsIgnoreCase(o.toString().trim())
				|| PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean num(Object o) {
		int n = 0;
		try {
			n = Integer.parseInt(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean decimal(Object o) {
		double n = 0;
		try {
			n = Double.parseDouble(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0.0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给JID返回用户名
	 * 
	 * @param Jid
	 * @return
	 */
	public static String getUserNameByJid(String Jid) {
		if (empty(Jid)) {
			return null;
		}
		if (!Jid.contains("@")) {
			return Jid;
		}
		return Jid.split("@")[0];
	}

	/**
	 * 给用户名返回JID
	 * 
	 * @param jidFor
	 *            域名//如ahic.com.cn
	 * @param userName
	 * @return
	 */
	public static String getJidByName(String userName, String jidFor) {
		if (empty(jidFor) || empty(jidFor)) {
			return null;
		}
		return userName + "@" + jidFor;
	}

	/**
	 * 根据给定的时间字符串，返回月 日 时 分 秒
	 * 
	 * @param allDate
	 *            like "yyyy-MM-dd hh:mm:ss SSS"
	 * @return
	 */
	public static String getMonthTomTime(String allDate) {
		return allDate.substring(5, 19);
	}

	/**
	 * 根据给定的时间字符串，返回月 日 时 分 月到分钟
	 * 
	 * @param allDate
	 *            like "yyyy-MM-dd hh:mm:ss SSS"
	 * @return
	 */
	public static String getMonthTime(String allDate) {
		return allDate.substring(5, 16);
	}

	/**
	 * user java reg to check phone number and replace 86 or +86 only check
	 * start with "+86" or "86" ex +8615911119999 13100009999 replace +86 or 86
	 * with ""
	 * 
	 * @param phoneNum
	 * @return
	 * @throws Exception
	 */
	public static String getPhoneNum(String phoneNum) {

		// phoneNum = phoneNum.trim();
		phoneNum = phoneNum.replaceAll("\\s+", ""); // 去掉空格
		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
		Matcher m1 = p1.matcher(phoneNum);
		if (m1.matches()) {
			Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
			Matcher m2 = p2.matcher(phoneNum);
			StringBuffer sb = new StringBuffer();
			while (m2.find()) {
				m2.appendReplacement(sb, "");
			}
			m2.appendTail(sb);
			return sb.toString();

		} else {
			return "";
		}

	}

	public static String hideMobile(String moblie) {
		String newMoblie = moblie.substring(0,
				moblie.length() - (moblie.substring(3)).length())
				+ "****" + moblie.substring(7);
		return newMoblie;
	}

	/**
	 * 设置剪切板
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            剪切内容
	 */
	public static void setClipboard(Context context, String content) {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(content);
	}

	/**
	 * 读取剪切板内容
	 * 
	 * @param context
	 *            上下文
	 * @return 剪切内容
	 */
	public String getClipboard(Context context) {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		return clipboard.getText().toString();
	}

}
