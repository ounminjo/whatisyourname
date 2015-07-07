package com.macgong.test;

import java.util.HashMap;
import java.util.Map;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	DataHandler mDataHandler = new DataHandler();
	@Override
	public void onReceive(Context context, Intent intent) {
		BootReceiver mBootReceiver;
		String action = intent.getAction();
		if (action.equals("android.intent.action.BOOT_COMPLETED")) {
			
			Map<String,String> map = new HashMap<String,String>();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ALL_GROUP, map)).execute();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ALL_MEMBER, map)).execute();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ADMIN, map)).execute();
			
			
			Intent i = new Intent(context, ServiceClass.class);
			context.startService(new Intent("com.macgong.test"));
			Toast.makeText(context, "DB그룹 받아오기", Toast.LENGTH_SHORT).show();
		}
	}
}
