/**
 * 
 */
package com.tincent.demo.receiver;

import com.tincent.demo.service.SmsService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 自启动接收器
 * 
 * @author wanghui
 * 
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, SmsService.class));
	}
}
