/**
 * 
 */
package com.tincent.demo.activity;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.util.Debug;

/**
 * 定位界面
 * 
 * @author hui.wang
 * 
 */
public class LocationActivity extends BaseActivity implements BDLocationListener {
	private LocationClient locationClient;
	private TextView txtTime;
	private TextView txtCountry;
	private TextView txtProvince;
	private TextView txtCity;
	private TextView txtDistrict;
	private TextView txtStreet;
	private TextView txtNumber;
	private TextView txtLocation;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btnLocate:
			locationClient.requestLocation();
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_location);

	}

	@Override
	public void initData() {
		locationClient = new LocationClient(this);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(false);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

		locationClient.setLocOption(option);

		locationClient.registerLocationListener(this);
		locationClient.start();
	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btnLocate).setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("百度定位");

		txtTime = (TextView) findViewById(R.id.txtTime);
		txtCountry = (TextView) findViewById(R.id.txtCountry);
		txtProvince = (TextView) findViewById(R.id.txtProvince);
		txtCity = (TextView) findViewById(R.id.txtCity);
		txtDistrict = (TextView) findViewById(R.id.txtDistrict);
		txtStreet = (TextView) findViewById(R.id.txtStreet);
		txtNumber = (TextView) findViewById(R.id.txtNumber);
		txtLocation = (TextView) findViewById(R.id.txtLocation);

	}

	@Override
	public void updateView() {
		locationClient.requestLocation();
	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		Debug.o(new Exception(), "locationType : " + location.getLocType() + ", networkType = " + location.getNetworkLocationType());
		txtTime.setText("时间：" + location.getTime());
		txtCountry.setText("国家：" + location.getCountry());
		txtProvince.setText("省份：" + location.getProvince());
		txtCity.setText("城市：" + location.getCity());
		txtDistrict.setText("区域：" + location.getDistrict());
		txtStreet.setText("街道：" + location.getStreet());
		txtNumber.setText("号码：" + location.getStreetNumber());
		txtLocation.setText(location.getAddrStr());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationClient.stop();
		locationClient.unRegisterLocationListener(this);
	}
}
