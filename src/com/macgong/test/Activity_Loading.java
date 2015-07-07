package com.macgong.test;

import java.util.HashMap;
import java.util.Map;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Activity_Loading extends Activity {
	DataHandler mDataHandler = new DataHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		if (Group.getRootTreeOfGroup() == null) {
			Map<String, String> map = new HashMap<String, String>();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ALL_GROUP, map)).execute();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ALL_MEMBER, map)).execute();
			new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.GET_ADMIN, map)).execute();
		}
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				Intent intent = new Intent(getApplicationContext(), Activity_Main.class);
				startActivity(intent);
				finish();
			}
		};
		handler.sendEmptyMessageDelayed(0, 3000);
	}
}
