/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;


import com.tincent.android.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 听讯科技：提示工具类，方便以后自定义
 * 
 * @author hui.wang
 * @date 2015.3.18
 * 
 */
public class TXToastUtil {

	private static TXToastUtil instance;

	private Context context;

	private TXToastUtil() {

	}

	/**
	 * 获取单子实例
	 * 
	 * @return
	 */
	public static TXToastUtil getInstatnce() {
		if (instance == null) {
			instance = new TXToastUtil();
		}

		return instance;
	}

	/**
	 * 初始化
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		context = ctx;
	}

	/**
	 * 提示内容来自资源文件
	 * 
	 * @param resId
	 */
	public void showMessage(int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

	/**
	 * 提示内容来自字符串
	 * 
	 * @param resId
	 */
	public void showMessage(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public void showToastDialog(String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast_dialog, null);
		TextView txtHint = (TextView) view.findViewById(R.id.txtHint);
		txtHint.getBackground().setAlpha(150);
		txtHint.setText(msg);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, -300);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(view);
		toast.show();

	}

}
