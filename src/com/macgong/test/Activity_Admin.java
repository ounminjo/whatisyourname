package com.macgong.test;



import java.util.ArrayList;
import java.util.Map;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;

public class Activity_Admin extends Activity {
	
	
	int mCurrentFragmentIndex;
	public final static int FRAGMENT_MEMBER = 0;
	public final static int FRAGMENT_GROUP = 1;
	
	EditText remove_name;
	EditText remove_phonenumber;
	EditText remove_id;
	
	DataHandler_Admin mDataHandler = new DataHandler_Admin();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		TabHost mTab = (TabHost)findViewById(R.id.tabHost);
		
		mTab.setup();
		
		TabHost.TabSpec spec;
		
		spec = mTab.newTabSpec("Tab 0");
		spec.setIndicator("add");
		spec.setContent(R.id.layout_add);
		mTab.addTab(spec);
		
		spec = mTab.newTabSpec("Tab 1");
		spec.setIndicator("remove");
		spec.setContent(R.id.layout_remove);
		mTab.addTab(spec);
		
		mTab.setCurrentTab(0);
		

		
		remove_name = (EditText)findViewById(R.id.remove_name);
		remove_phonenumber = (EditText)findViewById(R.id.remove_phonenumber);
		remove_id = (EditText)findViewById(R.id.remove_id);
		
		
		RadioButton radio_add_member = (RadioButton)findViewById(R.id.addMember);
		radio_add_member.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_MEMBER;
				fragmentReplace(mCurrentFragmentIndex);	
			}
		});
		
		RadioButton radio_add_group = (RadioButton)findViewById(R.id.addGroup);
		radio_add_group.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_GROUP;
				fragmentReplace(mCurrentFragmentIndex);	
			}
		});
		
		
	
		Button btn_remove_submit = (Button)findViewById(R.id.btn_remove_submit);
		btn_remove_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String input_name = remove_name.getText().toString();
				String input_phonenumber = remove_phonenumber.getText().toString();
				String input_id = remove_id.getText().toString();
				
				new CommunicationWithServer(mDataHandler, new SocketData(Socket_StaticClass.DELETE_DATA,input_name,input_phonenumber,input_id)).execute();
			}
		});
	}
	
	
	
	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.fragment1, newFragment);

		// Commit the transaction
		transaction.commit();

	}
	
	private Fragment getFragment(int idx) {
		Fragment newFragment = null;

		switch (idx) {
		
		case FRAGMENT_MEMBER:
			newFragment = new Frag_AddMember();
			break;
			
		case FRAGMENT_GROUP:
			newFragment = new Frag_AddGroup();
			break;
			
		default:
			break;
		}

		return newFragment;
	}
	
	private class DataHandler_Admin extends Handler	{
		public void handleMessage(Message msg)		{
			switch (msg.what)			{
			case Socket_StaticClass.SUCCESS_RESPOND:
				SocketData socketData = (SocketData)msg.obj;

				if(Socket_StaticClass.DELETE_DATA.equals(socketData.getCommend())){
					AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Admin.this);
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    dialog.dismiss();     //닫기
					    }
					});
					alert.setMessage("삭제되었습니다.");
					alert.show();
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
