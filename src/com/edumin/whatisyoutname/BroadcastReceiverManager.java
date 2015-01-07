package com.edumin.whatisyoutname;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverManager extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("android.intent.action.BOOT_COMPLETED")) {			
			context.startService(new Intent(context, IncomingCallService.class));
			

		}
	}
}
