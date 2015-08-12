/**
 * 
 */
package com.tincent.demo.activity;

import com.tincent.demo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * IP查询页面
 * 
 * @author hui.wang
 * 
 */
public class IPCheckActivity extends FragmentActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);

		setContentView(R.layout.activity_ip_check);

		findViewById(R.id.check).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check:
			startActivity(new Intent(this, TestActivity.class));
			break;
		}
	}
}
