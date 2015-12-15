/**
 * 
 */
package com.tincent.demo.fragment;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.util.TXDialogUtil;
import com.tincent.android.util.TXNetworkUtil;
import com.tincent.android.util.TXToastUtil;
import com.tincent.android.view.TXPageIndicator;
import com.tincent.demo.Constants;
import com.tincent.demo.R;
import com.tincent.demo.activity.AppListMenuActivity;
import com.tincent.demo.activity.FoodAndDrinkActivity;
import com.tincent.demo.activity.HomeActivity;
import com.tincent.demo.activity.IPCheckActivity;
import com.tincent.demo.activity.ListMenuActivity;
import com.tincent.demo.activity.LocationActivity;
import com.tincent.demo.activity.NotesActivity;
import com.tincent.demo.activity.NumListActivity;
import com.tincent.demo.activity.TestActivity;
import com.tincent.demo.adapter.AdBannerAdapter;
import com.tincent.demo.service.DownloadService;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author hui.wang
 * 
 */
public class HomeFragment extends BaseFragment {
	private AdBannerAdapter adapter;
	private ViewPager adViewPager;
	private TXPageIndicator indicator;
	private HomeActivity homeActivity;
	private Dialog alertDialog;

	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void initView(View rootView) {
		ImageButton menu = (ImageButton) rootView.findViewById(R.id.back);
		menu.setImageResource(R.drawable.icon_menu);
		menu.setOnClickListener(this);

		rootView.findViewById(R.id.more).setVisibility(View.GONE);

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText("我的主页");

		adViewPager = (ViewPager) rootView.findViewById(R.id.image_pager);
		adapter = new AdBannerAdapter(getActivity());
		adViewPager.setAdapter(adapter);
		indicator = (TXPageIndicator) rootView.findViewById(R.id.indicator);
		indicator.setViewPager(adViewPager);

		rootView.findViewById(R.id.txtFuction1).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction2).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction3).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction4).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction5).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction6).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction7).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction8).setOnClickListener(this);
		rootView.findViewById(R.id.txtFuction9).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			homeActivity.toggleLeftMenu();
			break;
		case R.id.txtFuction1:
			startActivity(new Intent(getActivity(), IPCheckActivity.class));
			break;
		case R.id.txtFuction2:
			startActivity(new Intent(getActivity(), FoodAndDrinkActivity.class));
			break;
		case R.id.txtFuction3:
			startActivity(new Intent(getActivity(), NotesActivity.class));
			break;
		case R.id.txtFuction4:
			if (TXNetworkUtil.isMobileConnected(getActivity())) {
				alertDialog = TXDialogUtil.showTwoBtnDialog(getActivity(), "", "您当前不是WIFI网络，是否下载？", new OnClickListener() {
					@Override
					public void onClick(View v) {
						switch (v.getId()) {
						case R.id.yes:
							alertDialog.dismiss();
							downloadApk("http://27.17.0.142:5200/apk/dev/patient.apk", false);
							break;
						case R.id.no:
							alertDialog.dismiss();
							break;
						default:
							break;
						}
					}
				});
			} else {
				downloadApk("http://27.17.0.142:5200/apk/dev/patient.apk", false);
			}

			break;
		case R.id.txtFuction5:
			startActivity(new Intent(getActivity(), LocationActivity.class));
			break;
		case R.id.txtFuction6:
			startActivity(new Intent(getActivity(), ListMenuActivity.class));
			break;
		case R.id.txtFuction7:
			startActivity(new Intent(getActivity(), AppListMenuActivity.class));
			break;
		case R.id.txtFuction8:
			startActivity(new Intent(getActivity(), NumListActivity.class));
			break;
		case R.id.txtFuction9:
			startActivity(new Intent(getActivity(), TestActivity.class));
			break;
		}

	}

	/**
	 * 下载安装包
	 * 
	 * @param apkUrl
	 * 
	 * @param auto
	 *            是否自动下载，是：状态栏无进度显示；否：状态栏有下载进度
	 */
	private void downloadApk(String apkUrl, boolean auto) {
		Intent service = new Intent(getActivity(), DownloadService.class);
		service.putExtra(Constants.KEY_APK_URL, apkUrl);
		service.putExtra(Constants.KEY_FILE_DIR, Constants.APK_DIR);
		service.putExtra(Constants.KEY_FILE_SUBFIX, Constants.APK_SUBFIX);
		service.putExtra(Constants.KEY_AUTO_DOWNLOAD, auto); //自动下载，状态栏没有进度显示
		getActivity().startService(service);
		TXToastUtil.getInstatnce().showMessage("开始下载，请检查状态栏！");
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initData() {
		homeActivity = (HomeActivity) getActivity();

	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		// TODO Auto-generated method stub

	}

}
