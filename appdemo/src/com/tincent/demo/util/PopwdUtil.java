package com.tincent.demo.util;

import java.util.Date;
import java.util.List;

import com.tincent.android.util.TXTimeUtil;
import com.tincent.demo.R;
import com.tincent.demo.view.TXCalendarView;
import com.tincent.demo.view.TXCalendarView.OnDateChangeListener;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * popwd显示 工具类
 * 
 * @author jiawei.xue
 * 
 *         2015-5-18 下午5:15:04
 */
public class PopwdUtil {
	/**
	 * popwd显示日历
	
	 */

	public static PopupWindow showDatePopwd(final Context ctx, View parent, OnDateChangeListener l) {
		LayoutInflater inflater = LayoutInflater.from(ctx);
		View view = inflater.inflate(R.layout.activity_caleandar, null);
		final PopupWindow caleandarPopwd = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		final TXCalendarView calendarView = (TXCalendarView) view.findViewById(R.id.caleandarday);
		final TextView titleDate = (TextView) view.findViewById(R.id.titleDate);
		titleDate.setText(TXTimeUtil.format(System.currentTimeMillis(), "yyyy年MM月"));
		caleandarPopwd.setFocusable(true);
		view.findViewById(R.id.lastMonth).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击上一月 同样返回年月
				String leftYearAndmonth = calendarView.clickLeftMonth();
				String[] lastweek = leftYearAndmonth.split("-");
				titleDate.setText(lastweek[0] + "年" + lastweek[1] + "月");
			}
		});
		view.findViewById(R.id.nextMonth).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击下一月
				String rightYearAndmonth = calendarView.clickRightMonth();
				String[] nextweek = rightYearAndmonth.split("-");
				titleDate.setText(nextweek[0] + "年" + nextweek[1] + "月");
			}
		});
		view.findViewById(R.id.lastYear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击去年 同样返回年月
				String leftYearAndYear = calendarView.clickLeftYear();
				String[] lastYear = leftYearAndYear.split("-");
				titleDate.setText(lastYear[0] + "年" + lastYear[1] + "月");
			}
		});
		view.findViewById(R.id.nextYear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击明年
				String rightYearAndYear = calendarView.clickRightYear();
				String[] nextYear = rightYearAndYear.split("-");
				titleDate.setText(nextYear[0] + "年" + nextYear[1] + "月");
			}
		});
		view.findViewById(R.id.rl_popwed).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				caleandarPopwd.dismiss();
			}
		});
		calendarView.setSelectMore(false); // 单选
		calendarView.setOnDateChangeListener(l);

		caleandarPopwd.setFocusable(true);
		caleandarPopwd.setOutsideTouchable(true);
		caleandarPopwd.setBackgroundDrawable(new BitmapDrawable()); //
		caleandarPopwd.showAtLocation(parent, Gravity.CENTER, 0, 0);
		return caleandarPopwd;
	}

	/**
	 * popwd显示日历
	 
	 */

	public static PopupWindow showDatePopwd(final Context ctx, long time, View parent, OnDateChangeListener l) {
		LayoutInflater inflater = LayoutInflater.from(ctx);
		View view = inflater.inflate(R.layout.activity_caleandar, null);
		final PopupWindow caleandarPopwd = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		final TXCalendarView calendarView = (TXCalendarView) view.findViewById(R.id.caleandarday);
		final TextView titleDate = (TextView) view.findViewById(R.id.titleDate);
		calendarView.setData(new Date(time));
		titleDate.setText(TXTimeUtil.format(time, "yyyy年MM月"));
		caleandarPopwd.setFocusable(true);
		view.findViewById(R.id.lastMonth).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击上一月 同样返回年月
				String leftYearAndmonth = calendarView.clickLeftMonth();
				String[] lastweek = leftYearAndmonth.split("-");
				titleDate.setText(lastweek[0] + "年" + lastweek[1] + "月");
			}
		});
		view.findViewById(R.id.nextMonth).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击下一月
				String rightYearAndmonth = calendarView.clickRightMonth();
				String[] nextweek = rightYearAndmonth.split("-");
				titleDate.setText(nextweek[0] + "年" + nextweek[1] + "月");
			}
		});
		view.findViewById(R.id.lastYear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击去年 同样返回年月
				String leftYearAndYear = calendarView.clickLeftYear();
				String[] lastYear = leftYearAndYear.split("-");
				titleDate.setText(lastYear[0] + "年" + lastYear[1] + "月");
			}
		});
		view.findViewById(R.id.nextYear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击明年
				String rightYearAndYear = calendarView.clickRightYear();
				String[] nextYear = rightYearAndYear.split("-");
				titleDate.setText(nextYear[0] + "年" + nextYear[1] + "月");
			}
		});
		view.findViewById(R.id.rl_popwed).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				caleandarPopwd.dismiss();
			}
		});
		calendarView.setSelectMore(false); // 单选
		calendarView.setOnDateChangeListener(l);
		calendarView.setCalendarData(new Date(time));
		caleandarPopwd.setFocusable(true);
		caleandarPopwd.setOutsideTouchable(true);
		caleandarPopwd.setBackgroundDrawable(new BitmapDrawable()); //
		caleandarPopwd.showAtLocation(parent, Gravity.CENTER, 0, 0);
		return caleandarPopwd;
	}

	/**
	 * popwd显示 分享
	
	 */
	public static PopupWindow showShareByWeixin(final Context ctx, View parent, OnClickListener l) {
		LayoutInflater inflater = LayoutInflater.from(ctx);
		View view = inflater.inflate(R.layout.share_popwed_show, null);
		final PopupWindow sharePopwd = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.findViewById(R.id.sendfrient).setOnClickListener(l);
		view.findViewById(R.id.sharefriend).setOnClickListener(l);
		view.findViewById(R.id.share_popwd).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sharePopwd.dismiss();
			}
		});
		sharePopwd.setFocusable(true);
		sharePopwd.setOutsideTouchable(true);
		sharePopwd.setBackgroundDrawable(new BitmapDrawable()); //
		sharePopwd.showAtLocation(parent, Gravity.CENTER, 0, 0);

		return sharePopwd;
	}
	
}
