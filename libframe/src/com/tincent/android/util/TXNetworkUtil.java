/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

/**
 * 听讯科技：网络工具类
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXNetworkUtil {

	/**
	 * 检测网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 检测是否WIWI网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 检测是否移动网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable()) {
				return networkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param acti
	 *            .nUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static String post(String actionUrl, Map<String, String> params, Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

		// 请求结束标志־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			int ch;
			while ((ch = br.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2.toString();

	}

	/**
	 * Http基本认证（post）
	 * 
	 * @param path
	 * @param username
	 * @param pwd
	 */
	public static void postHttpBasicAuth(String path, String username, String pwd) {
		try {
			URL url = new URL(path);
			String authStr = username + ":" + pwd;
			String encoding = Base64.encodeToString(authStr.getBytes(), Base64.DEFAULT);
			TXDebug.o(new Exception(), "encoding = " + encoding);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Http基本认证(get)
	 * 
	 * @param path
	 * @param username
	 * @param pwd
	 */
	public static void getHttpBasicAuth(String path, String username, String pwd) {
		try {
			DefaultHttpClient Client = new DefaultHttpClient();
			Client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, pwd));

			HttpGet httpGet = new HttpGet("https://httpbin.org/basic-auth/user/passwd");
			HttpResponse response = Client.execute(httpGet);

			TXDebug.o(new Exception(), "response = " + response);

			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = "";
			while ((line = breader.readLine()) != null) {
				responseString.append(line);
			}
			breader.close();
			String responseStr = responseString.toString();

			TXDebug.o(new Exception(), "responseStr = " + responseStr);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
