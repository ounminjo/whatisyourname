package com.macgong.test;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Frag_AddGroup extends Fragment{
	EditText add_group_key;
	EditText add_group_name;
	EditText add_group_depth;
	EditText add_group_parent_key;

	DataHandler mDataHandler = new DataHandler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.add_group, container, false);

		add_group_key = (EditText) v.findViewById(R.id.add_group_key);
		add_group_name = (EditText) v.findViewById(R.id.add_group_name);
		add_group_depth = (EditText) v.findViewById(R.id.add_group_depth);
		add_group_parent_key = (EditText) v.findViewById(R.id.add_group_parent_key);

		Button btn_add_group_submit = (Button) v.findViewById(R.id.add_group_submit);
		btn_add_group_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String input_key = add_group_key.getText().toString();
				String input_name = add_group_name.getText().toString();
				String input_depth = add_group_depth.getText().toString();
				String input_parent_key = add_group_parent_key.getText().toString();

				/*new CommunicationWithServer(mDataHandler,
						new SocketData(Socket_StaticClass.SET_DATA, input_name, input_phonenumber, input_id)).execute();*/

			}
		});

		return v;
	}
}
