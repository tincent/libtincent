/**
 * 
 */
package com.tincent.demo.activity;

import org.json.JSONObject;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.R;
import com.tincent.demo.manager.DemoManager;
import com.tincent.demo.util.Debug;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * IP查询页面
 * 
 * @author hui.wang
 * 
 */
public class IPCheckActivity extends BaseActivity {

	private EditText edtDomain;

	private TextView txtArea;
	private TextView txtLocation;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.query:
			query();
			break;
		}

	}

	private void query() {
		String domain = edtDomain.getText().toString();
		if (!TextUtils.isEmpty(domain)) {
			showLoading();
			DemoManager.getInstance().quertyIP(domain);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {

		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		if (DemoManager.TAG_REQUEST_QUERY_ID.equals(evt.requestTag)) {
			if (evt.statusCode == TXResponseEvent.REQUET_OK_CODE) {
				if (evt.object != null && evt.object[0] instanceof JSONObject) {
					JSONObject jobj = (JSONObject) evt.object[0];
					String code = jobj.optString("resultcode");
					String reason = jobj.optString("reason");
					JSONObject jresult = jobj.optJSONObject("result");
					Debug.o(new Exception(), "code = " + code + ", reason = " + reason);
					if (jresult != null) {
						String area = jresult.optString("area");
						String location = jresult.optString("location");
						txtArea.setText(getString(R.string.ip_area, area));
						txtLocation.setText(getString(R.string.ip_location, location));
					}
				}
				hideLoading();
			}
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_ip_check);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.query).setOnClickListener(this);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("IP地址查询");

		edtDomain = (EditText) findViewById(R.id.edtDomain);
		edtDomain.setText("www.baidu.com");
		txtArea = (TextView) findViewById(R.id.txtArea);
		txtLocation = (TextView) findViewById(R.id.txtLocation);
	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

}
