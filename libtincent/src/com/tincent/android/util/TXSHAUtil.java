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
			MessageDigest md5 = MessageDigest.getInstance("SHA-1");
			md5.update(str.getBytes());
			byte[] m = md5.digest();// 加密
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < m.length; i++) {
				sb.append(m[i]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
}
