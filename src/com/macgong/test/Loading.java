package com.macgong.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class Loading extends Activity {
	phpDown task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		task = new phpDown();
		task.execute("http://168.188.129.121");
		
	}
	
	private class phpDown extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				URL url = new URL(urls[0]); // ���� url ����				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Ŀ�ؼ� ��ü ����

				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// ����Ǿ��� �ڵ尡 ���ϵǸ�.
					// if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					for (;;) {
						String line = br.readLine(); // ���� �������� �ؽ�Ʈ�� ���δ����� �о� ����.
						if (line == null)
							break;	// ����� �ؽ�Ʈ ������ jsonHtml�� �ٿ�����
						jsonHtml.append(line + "\n");
						br.close();
					}
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			return jsonHtml.toString();
		}

		protected void onPostExecute(String str) {

			ArrayList<Map<String, String>> groupList= new ArrayList<Map<String, String>>();
			ArrayList<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			
			try {

				JSONObject root = new JSONObject(str);
				JSONArray ja = root.getJSONArray("results");
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					if(jo.getString("tablename").equals("GROUP_TABLE")){
						Map<String, String> group = new HashMap<String, String>();
						group.put(StaticClass.GROUP_KEY,jo.getString("group_key"));
						group.put(StaticClass.GROUP_NAME, jo.getString("group_name"));
						group.put(StaticClass.GROUP_DEPTH, jo.getString("group_depth"));
						group.put(StaticClass.GROUP_PARENT, jo.getString("group_parent_key"));
						groupList.add(group);
					}
					else if(jo.getString("tablename").equals("MEMBER")){
						Map<String, String> contact = new HashMap<String, String>();
						contact.put(StaticClass.USER_PHONE, jo.getString("user_phone"));
						contact.put(StaticClass.USER_NAME, jo.getString("user_name"));
						contact.put(StaticClass.USER_STUDENT_NUMBER, jo.getString("user_id"));
						contact.put(StaticClass.USER_GROUP_KEY, jo.getString("user_group_key"));
						dataList.add(contact);
					}	
				}
				
				Group.setRootTreeOfGroup(Group.makeGroup(groupList));	//�׷�Ʈ������ ��Ʈ����
				
				for(int i=0;i<dataList.size();i++){
					Group.setGroupData(dataList.get(i));	//�ش� �׷쿡 ������ �ֱ�
				}
				
				/*ArrayList<Map<String, String>> dataAll = Group.getDataAll(Group.getRootTreeOfGroup());
				for(int i=0; i<dataAll.size(); i++){
					Log.e("HyunSik", i + " : " + dataAll.get(i));
				}
				Group temp = Group.getRootTreeOfGroup();*/
				
				
				Handler handler = new Handler(){
					public void handleMessage(Message msg){
						Intent intent = new Intent(getApplicationContext(),MainActivity.class);
						startActivity(intent);
						finish();
					}
				};
				handler.sendEmptyMessageDelayed(0, 3000);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}
