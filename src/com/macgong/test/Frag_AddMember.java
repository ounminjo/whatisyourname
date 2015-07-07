package com.macgong.test;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Frag_AddMember extends Fragment {
	EditText add_name;
	EditText add_phonenumber;
	EditText add_id;

	DataHandler_AddMember mDataHandler = new DataHandler_AddMember();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.add_member, container, false);
		
		add_name = (EditText)v.findViewById(R.id.add_name);
		add_phonenumber = (EditText)v.findViewById(R.id.add_phonenumber);
		add_id = (EditText)v.findViewById(R.id.add_id);
		
		Button btn_add_submit = (Button)v.findViewById(R.id.btn_add_submit);
		btn_add_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String input_name = add_name.getText().toString();
				String input_phonenumber = add_phonenumber.getText().toString();
				String input_id = add_id.getText().toString();
				
				new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.SET_DATA,input_name,input_phonenumber,input_id)).execute();
				
				
			}
		});


		return v;
	}
	
	private class DataHandler_AddMember extends Handler	{
		public void handleMessage(Message msg)		{
			switch (msg.what)			{
			case Socket_StaticClass.SUCCESS_RESPOND:
				SocketData socketData = (SocketData)msg.obj;

				if(Socket_StaticClass.SET_DATA.equals(socketData.getCommend())){
					
					AlertDialog.Builder alert = new AlertDialog.Builder(getView().getContext());
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    dialog.dismiss();     //닫기
					    }
					});
					alert.setMessage("추가되었습니다.");
					alert.show();
				}
				else if(Socket_StaticClass.GET_DATA.equals(socketData.getCommend())){
										
				}
				break;
			case Socket_StaticClass.ERROR_RESPOND:
				break;
			default:				
				break;
			}
		}
	}

}
