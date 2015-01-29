package com.macgong.test;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	//PhoneStateCheckListener phoneCheckListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		//phoneCheckListener = new PhoneStateCheckListener(this);

		/*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		telephonyManager.listen(phoneCheckListener,
				PhoneStateListener.LISTEN_CALL_STATE);*/
		 Button btn_play = (Button) findViewById(R.id.ServiceStart);		//수신화면 서비스 시작 버튼
		 btn_play.setOnClickListener(new View.OnClickListener(){
			 public void onClick(View v){
				 startService(new Intent("com.macgong.test"));
			 }
		 });
	     Button btn_stop = (Button) findViewById(R.id.ServiceEnd);		//수신화면 서비스 종료 버튼
	     btn_stop.setOnClickListener(new View.OnClickListener(){
	            public void onClick(View v) {
	                stopService(new Intent("com.macgong.test"));
	            }
	        });
	}
	

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
