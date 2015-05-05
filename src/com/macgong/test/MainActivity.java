package com.macgong.test;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity {
	ListView m_ListView;
	ArrayList<Group> m_group = Group.getRootTreeOfGroup().getChildren();
	ArrayList<String> m_ArrayListMember = new ArrayList<String>();
	ListViewAdapter m_adapter;

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
							Group temp = m_group.get(position);
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
		
		

		Button btn_play = (Button) findViewById(R.id.ServiceStart); 
	
		btn_play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				startService(new Intent("com.macgong.test"));
			}
		});
		
		Button btn_stop = (Button) findViewById(R.id.ServiceEnd); 
																	
		btn_stop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopService(new Intent("com.macgong.test"));
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
