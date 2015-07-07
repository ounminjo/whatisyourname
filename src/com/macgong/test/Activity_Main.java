package com.macgong.test;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;


public class Activity_Main extends Activity {
	ListView m_ListView;
	ArrayList<Group> m_group = Group.getRootTreeOfGroup().getChildren();
	ArrayList<String> m_ArrayListMember = new ArrayList<String>();
	ListViewAdapter m_adapter;
	PopupWindow pwindo;

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String name = (String) parent.getItemAtPosition(position);

			if (name.equals(m_group.get(position).getGroupName())) {
				
				if (m_group.get(position).getChildren() == null) {
					m_adapter.removeAll();
					if(m_group.get(position).getData()!=null){
						for(int i=0; i < m_group.get(position).getData().size(); i++) {
							m_adapter.add(m_group.get(position).getData().get(i).get(StaticClass.USER_NAME)+" "+ m_group.get(position).getData().get(i).get(StaticClass.USER_PHONE));
						}
					}
					
				} else {
					m_group = m_group.get(position).getChildren();
					
					m_adapter.removeAll();
					if(m_group != null){
						for (int j = 0; j < m_group.size(); j++) {
							Group temp = m_group.get(j);
							m_adapter.add(m_group.get(j).getGroupName());
						}						
					}
					
				}
				m_ListView.setAdapter(m_adapter);	
			}
			
		}
	};

	// PhoneStateCheckListener phoneCheckListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		m_adapter = new ListViewAdapter();
		m_ListView = (ListView) findViewById(R.id.list_view_root);
		
		m_ListView.setAdapter(m_adapter);
		for (int i = 0; i < m_group.size(); i++) {
			m_adapter.add(m_group.get(i).getGroupName());
		}
		m_ListView.setOnItemClickListener(mItemClickListener);
		
		
		ImageButton btn_reset = (ImageButton)findViewById(R.id.btn_reset);
		btn_reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				m_adapter.removeAll();
				m_group = Group.getRootTreeOfGroup().getChildren();
				m_ListView.setAdapter(m_adapter);
				for (int i = 0; i < m_group.size(); i++) {
					m_adapter.add(m_group.get(i).getGroupName());
				}
				m_ListView.setOnItemClickListener(mItemClickListener);
				
			}
		});
		ImageButton btn_admin = (ImageButton)findViewById(R.id.btn_admin);
		btn_admin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Activity_AdminLogin.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.cycle_7);			
			}
		});
		
		ImageButton btn_search = (ImageButton)findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Activity_Search.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade, R.anim.cycle_7);	
				
			}
		});
		
		
		ImageButton btn_setting = (ImageButton)findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) Activity_Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 
				View layout = inflater.inflate(R.layout.setting_layout,(ViewGroup) findViewById(R.id.layout_setting));
				
				pwindo = new PopupWindow(layout, 500,300, true);
				pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
				
				Button btn_play = (Button) layout.findViewById(R.id.ServiceStart); 
				
				btn_play.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) { 
						startService(new Intent("com.macgong.test"));
					}
				});
				
				Button btn_stop = (Button) layout.findViewById(R.id.ServiceEnd); 
																			
				btn_stop.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						stopService(new Intent("com.macgong.test"));
					}
				});
				
				Button btnClosePopup = (Button) layout.findViewById(R.id.btn_close);
				btnClosePopup.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						pwindo.dismiss();
					}
				});
				
			}
		});
		

	}
	public void onBackPressed() {
		m_group = Group.getParentGroups(m_group.get(0).getDapth());
		m_adapter.removeAll();
		
		for (int j = 0; j < m_group.size(); j++) {
			m_adapter.add(m_group.get(j).getGroupName());
		}	
		
		m_ListView.setAdapter(m_adapter);
	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
