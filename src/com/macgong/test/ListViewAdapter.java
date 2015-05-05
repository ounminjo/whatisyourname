package com.macgong.test;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{
	
	private ArrayList<String> m_List;
	
	
	
	public  ListViewAdapter() {
		m_List = new ArrayList<String>();
	}
	@Override
	public int getCount() {
		return m_List.size();
	}

	@Override
	public Object getItem(int position) {
		return m_List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		final Context context = parent.getContext();
		
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.text,parent,false);
			
			TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(m_List.get(position));
			
		}
		return convertView;
	}
	
	public void add(String _msg) {
        m_List.add(_msg);
    }

	public void remove(int _position) {
        m_List.remove(_position);
    }
	public void removeAll(){
		m_List.clear();
	}
}
