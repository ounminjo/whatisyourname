package com.macgong.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("android.intent.action.BOOT_COMPLETED")) {
			Intent i = new Intent(context, ServiceClass.class);
			context.startService(new Intent("com.macgong.test"));
		}
	}
}
