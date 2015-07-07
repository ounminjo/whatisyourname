package com.macgong.test;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class Activity_Search extends Activity {
	EditText edit_input;
	Button btn_search;
	RadioButton radio_name;
	RadioButton radio_number;
	ListView m_ListView;
	ListViewAdapter m_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_acrivity);
		
		radio_name = (RadioButton)findViewById(R.id.radio_name);
		radio_number = (RadioButton)findViewById(R.id.radio_number);
		m_adapter = new ListViewAdapter();
		m_ListView = (ListView) findViewById(R.id.list_result);
		btn_search = (Button)findViewById(R.id.btn_search);
		edit_input = (EditText)findViewById(R.id.edit_input);
		
		btn_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				m_adapter.removeAll();
				String input = edit_input.getText().toString();
				ArrayList<Map<String,String>> result;
				if(radio_name.isChecked()){
					result = Group.getMemberByName(input);
				}
				else{
					result = Group.getMemberByNumber(input);
				}
				
				m_ListView.setAdapter(m_adapter);
				for (int i = 0; i<result.size(); i++) {
					m_adapter.add(result.get(i).get(StaticClass.USER_NAME)+" "+result.get(i).get(StaticClass.USER_PHONE));
				}
				
				
				
			}
		});
		
		
	}
}
