/**
 * 
 */
package com.tincent.demo.receiver;

import com.tincent.android.util.TXToastUtil;
import com.tincent.demo.util.Debug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * 短信接收器
 * @author hui.wang
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SmsMessage[] messages = getMessagesFromIntent(intent);
		for (SmsMessage message : messages) {

			Debug.o(new Exception(), message.getOriginatingAddress()
					+ " : " + message.getDisplayOriginatingAddress()
					+ " : " + message.getDisplayMessageBody() + " : "
					+ message.getTimestampMillis());
			String from = message.getOriginatingAddress();
			String body = message.getDisplayMessageBody();
			TXToastUtil.getInstatnce().showMessage(from + ":" + body);
		}
	}

	public final SmsMessage[] getMessagesFromIntent(Intent intent) {

		Object[] messages = (Object[]) intent.getSerializableExtra("pdus");

		byte[][] pduObjs = new byte[messages.length][];

		for (int i = 0; i < messages.length; i++) {
			pduObjs[i] = (byte[]) messages[i];
		}

		byte[][] pdus = new byte[pduObjs.length][];

		int pduCount = pdus.length;

		SmsMessage[] msgs = new SmsMessage[pduCount];

		for (int i = 0; i < pduCount; i++) {
			pdus[i] = pduObjs[i];
			msgs[i] = SmsMessage.createFromPdu(pdus[i]);
		}

		return msgs;
	}

}
