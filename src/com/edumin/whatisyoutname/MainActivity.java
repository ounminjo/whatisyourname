package com.edumin.whatisyoutname;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button mButtonPlay;
	Button mButtonStop; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButtonPlay = (Button) findViewById(R.id.ServiceStart);
		mButtonStop = (Button) findViewById(R.id.ServiceEnd);
		mButtonPlay.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startService(new Intent(MainActivity.this, IncomingCallService.class));
			}
		});

		mButtonStop.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				stopService(new Intent(MainActivity.this, IncomingCallService.class));
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
