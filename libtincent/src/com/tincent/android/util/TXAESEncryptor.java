/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 数据加密与解密算法（AES）类
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXAESEncryptor {
	/**
	 * 听讯科技：AES加密
	 * 
	 * @param seed
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String seed, String cleartext)
			throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	/**
	 * AES解密
	 * 
	 * @param seed
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String seed, String encrypted)
			throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	// private static byte[] getRawKey(byte[] seed) throws Exception {
	// KeyGenerator kgen = KeyGenerator.getInstance("AES");
	// SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	// sr.setSeed(seed);
	// kgen.init(128, sr); // 192 and 256 bits may not be available
	// SecretKey skey = kgen.generateKey();
	// byte[] raw = skey.getEncoded();
	// return raw;
	// }

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
		SecureRandom sr = null;
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		} else {
			sr = SecureRandom.getInstance("SHA1PRNG");
		}
		sr.setSeed(seed);
		kgen.init(128, sr); // 256 bits or 128 bits,192bits
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	/**
	 * 字符串转换为十六进制
	 * 
	 * @param txt
	 * @return
	 */
	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	/**
	 * 十六进制转换为字符串
	 * 
	 * @param hex
	 * @return
	 */
	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	/**
	 * 十六进制字符串转换为字节数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	/**
	 * 字节数组转换为字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}