/**
 * 
 */
package com.tincent.demo.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Message;

import com.tincent.android.service.TXAbsService;
import com.tincent.demo.receiver.SmsReceiver;

/**
 * 短息接收服务
 * 
 * @author hui.wang
 * 
 */
public class SmsService extends TXAbsService {
	private SmsReceiver receiver;
	
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void registerListener() {
		IntentFilter filter = new IntentFilter();  
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");  
		filter.setPriority(Integer.MAX_VALUE);
		receiver = new SmsReceiver();
		registerReceiver(receiver, filter);
	}

	@Override
	public void unregisterListener() {
		unregisterReceiver(receiver);
	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onEventMainThread(Object evt) {

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
