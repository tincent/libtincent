/**
 * 
 */
package com.tincent.android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希算法工具 类
 * 
 * @author hui.wang
 * @date 2015.12.3
 * 
 */
public class TXSHAUtil {

	/**
	 * 获取字符串SHA1摘要
	 * 
	 * @param str
	 * @return
	 */
	public static String getSHA1(String str) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(str.getBytes());
			byte[] digest = sha.digest();// 加密

			return byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs;
	}
}
