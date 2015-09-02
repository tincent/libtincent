/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;


import com.tincent.android.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 听讯科技：常用对话框工具类
 * 
 * @author hui.wang
 * @date 2015.8.5
 * 
 */
public class TXDialogUtil {

	/**
	 * 显示两个按钮的对话框
	 * 
	 * @param ctx
	 *            应用上文文
	 * @param title
	 *            标题
	 * @param msg
	 *            消息
	 * @param listener
	 * @return
	 */
	public static Dialog showTwoBtnDialog(final Context ctx, String title, String msg, OnClickListener listener) {
		Dialog dialog = new Dialog(ctx, R.style.alert_dialog);
		dialog.setContentView(R.layout.alert_dialog);
		dialog.setCancelable(true);
		Window window = dialog.getWindow();
		TextView tvTitle = (TextView) window.findViewById(R.id.title);
		TextView tvMsg = (TextView) window.findViewById(R.id.message);
		TextView yes = (TextView) window.findViewById(R.id.yes);
		TextView no = (TextView) window.findViewById(R.id.no);
		// yes.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		// no.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		if (!TextUtils.isEmpty(title)) {
			tvTitle.setText(title);
			tvTitle.setVisibility(View.VISIBLE);
		} else {
			tvTitle.setVisibility(View.GONE);
		}
		tvMsg.setText(msg);
		yes.setOnClickListener(listener);
		no.setOnClickListener(listener);

		dialog.show();
		return dialog;
	}

}
