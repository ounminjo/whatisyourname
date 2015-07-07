package com.macgong.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_AdminLogin extends Activity {
	EditText Edit_ID;
	EditText Edit_PW;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		
		
		Edit_ID = (EditText)findViewById(R.id.Input_ID);
		Edit_PW = (EditText)findViewById(R.id.Input_PW);
		
		Button btn_login = (Button)findViewById(R.id.btn_login);
		
		btn_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String Input_ID = Edit_ID.getText().toString(); 
				String Input_PW = Edit_PW.getText().toString();
				
				for(int i=0;i<Group.mAdmin.size();i++){
					
					if(Input_ID.equals(Group.mAdmin.get(i).get("ID"))){
						if(Input_PW.equals(Group.mAdmin.get(i).get("PASSWORD"))){
							Intent intent = new Intent(getApplicationContext(), Activity_Admin.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade, R.anim.cycle_7);	
						}
						else
							System.out.println("정보불일치");
					}
					else
						System.out.println("정보불일치");
				}
				
				
				
			}
		});
	}
}
