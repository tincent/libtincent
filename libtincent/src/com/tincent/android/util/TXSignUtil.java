/**
 * 
 */
package com.tincent.android.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;

import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.tincent.android.util.TXSHAUtil;

/**
 * 签名工具类，仅限于配合听讯科技后台接口规范使用
 * 
 * @author hui.wang
 * @date 2015.12.3
 * 
 */
public class TXSignUtil {

	private static TXSignUtil instance;

	private TXSignUtil() {

	}

	public static TXSignUtil getInstance() {
		if (instance == null) {
			instance = new TXSignUtil();
		}

		return instance;
	}

	/**
	 * 生成签名字符串
	 * 
	 * @param url
	 *            网络地址
	 * @param method
	 *            请求方法
	 * @param params
	 *            请求参数
	 * @param appsecret
	 *            签名密钥
	 * @param dbg
	 *            是否开启调试：调式模式可打印签名日志
	 * @return
	 */
	public String makeSign(String url, String method, RequestParams params, String appsecret, boolean dbg) {
		StringBuilder sb = new StringBuilder();
		sb.append(method).append("&");
		sb.append(Uri.encode(url)).append("&");
		// 排序
		List<BasicNameValuePair> basicNameValuePairs = params.getParamsList();
		Collections.sort(basicNameValuePairs, paramsCompre);
		String paramStr = URLEncodedUtils.format(basicNameValuePairs, "UTF-8");
		sb.append(Uri.encode(paramStr));

		String digit = TXSHAUtil.getSHA1(sb.toString());

		byte[] encrypt = TXXXTEA.encrypt(digit.getBytes(), appsecret.getBytes());

		String base64 = Base64.encodeToString(encrypt, Base64.DEFAULT);

		String sign = Uri.encode(base64);

		if (dbg) {
			TXDebug.o(new Exception(), "url : " + sb.toString());
			TXDebug.o(new Exception(), "digit : " + digit);
			TXDebug.o(new Exception(), "base64 : " + base64);
			TXDebug.o(new Exception(), "sign : " + sign);
		}
		
		params.put("sign", sign);

		return sign;
	}

	/**
	 * 把请求参数的KEY按字典顺序排序
	 */
	private static Comparator<BasicNameValuePair> paramsCompre = new Comparator<BasicNameValuePair>() {

		@Override
		public int compare(BasicNameValuePair lhs, BasicNameValuePair rhs) {
			if (lhs != null && rhs != null && lhs.getName() != null && rhs.getName() != null) {
				return (lhs.getName()).compareTo(rhs.getName());
			}
			return 0;
		}
	};
}
