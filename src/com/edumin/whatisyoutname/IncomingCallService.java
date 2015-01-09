package com.edumin.whatisyoutname;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class IncomingCallService extends Service {
	PhoneStateCheckListener phoneCheckListener;
	private LinearLayout mLinearLayoutPopupView;							//항상 보이게 할 뷰
	private WindowManager.LayoutParams mParams;		//layout params 객체. 뷰의 위치 및 크기를 지정하는 객체
	private WindowManager mWindowManager;			//윈도우 매니저

	private float START_X, START_Y;							//움직이기 위해 터치한 시작 점
	private int PREV_X, PREV_Y;								//움직이기 이전에 뷰가 위치한 점
	private int MAX_X = -1, MAX_Y = -1;					//뷰의 위치 최대 값

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(phoneCheckListener == null){
			phoneCheckListener = new PhoneStateCheckListener(this);
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			telephonyManager.listen(phoneCheckListener,PhoneStateListener.LISTEN_CALL_STATE);
		}

		// 서비스가 중지되면 재빨리 재 시작 시킨다.
		return START_STICKY ;

	}

	private OnTouchListener mViewTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:				//사용자 터치 다운이면
				if(MAX_X == -1)
					setMaxPosition();
				START_X = event.getRawX();					//터치 시작 점
				START_Y = event.getRawY();					//터치 시작 점
				PREV_X = mParams.x;							//뷰의 시작 점
				PREV_Y = mParams.y;							//뷰의 시작 점
				break;
			case MotionEvent.ACTION_MOVE:
				int x = (int)(event.getRawX() - START_X);	//이동한 거리
				int y = (int)(event.getRawY() - START_Y);	//이동한 거리

				//터치해서 이동한 만큼 이동 시킨다
				mParams.x = PREV_X - x;
				mParams.y = PREV_Y + y;

				optimizePosition();		//뷰의 위치 최적화
				mWindowManager.updateViewLayout(mLinearLayoutPopupView, mParams);	//뷰 업데이트
				break;
			}
			return true;
		}
	};
	
	private class PhoneStateCheckListener extends PhoneStateListener {
		IncomingCallService mIncomingCallService;

		PhoneStateCheckListener(IncomingCallService incomingCallService) {
			mIncomingCallService = incomingCallService;
		}



		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_IDLE) {			
				if(mWindowManager != null){
					if(mLinearLayoutPopupView != null){
						mWindowManager.removeView(mLinearLayoutPopupView);
						mLinearLayoutPopupView = null;
						mWindowManager = null;
					}
				}
			} else if (state == TelephonyManager.CALL_STATE_RINGING) {				
				mLinearLayoutPopupView = new LinearLayout(mIncomingCallService);	
				mLinearLayoutPopupView.setOnTouchListener(mViewTouchListener);										//팝업뷰에 터치 리스너 등록
				mLinearLayoutPopupView.setBackgroundColor(getResources().getColor(R.color.lightblue));
				
				LinearLayout.LayoutParams mLayoutParamsPopUpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				mLinearLayoutPopupView.setLayoutParams(mLayoutParamsPopUpView);
				
				
				
				

				//뷰 생성
				TextView textViewCallNumber = new TextView(mIncomingCallService);	
				
				textViewCallNumber.setText("전화번호 : " + incomingNumber);	//텍스트 설정
				textViewCallNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);								//텍스트 크기 18sp
				textViewCallNumber.setTextColor(Color.BLUE);															//글자 색상
				textViewCallNumber.setBackgroundColor(Color.argb(127, 0, 255, 255));								//텍스트뷰 배경 색				
				mLinearLayoutPopupView.addView(textViewCallNumber);
				
				
				Button buttonClose = new Button(mIncomingCallService);
				buttonClose.setText("닫기");
				buttonClose.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						if(mWindowManager != null){
							if(mLinearLayoutPopupView != null){
								mWindowManager.removeView(mLinearLayoutPopupView);
								mLinearLayoutPopupView = null;
								mWindowManager = null;
							}
						}
					}
				});
				
				mLinearLayoutPopupView.addView(buttonClose);
				
				

				//최상위 윈도우에 넣기 위한 설정
				mParams = new WindowManager.LayoutParams(
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_PHONE,					//항상 최 상위에 있게. status bar 밑에 있음. 터치 이벤트 받을 수 있음.
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,		//이 속성을 안주면 터치 & 키 이벤트도 먹게 된다. 
						//포커스를 안줘서 자기 영역 밖터치는 인식 안하고 키이벤트를 사용하지 않게 설정
						PixelFormat.TRANSLUCENT);										//투명
				mParams.gravity = Gravity.RIGHT | Gravity.TOP;						//왼쪽 상단에 위치하게 함.

				mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);	//윈도우 매니저 불러옴.
				mWindowManager.addView(mLinearLayoutPopupView, mParams);		//최상위 윈도우에 뷰 넣기. *중요 : 여기에 permission을 미리 설정해 두어야 한다. 매니페스트에

				
			} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
			
			}
		}
	}

	private void setMaxPosition() {
		DisplayMetrics matrix = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(matrix);		//화면 정보를 가져와서

		MAX_X = matrix.widthPixels; // - mLinearLayoutPopupView.getWidth();			//x 최대값 설정
		MAX_Y = matrix.heightPixels; // - mLinearLayoutPopupView.getHeight();			//y 최대값 설정
	}

	private void optimizePosition() {
		//최대값 넘어가지 않게 설정
		if(mParams.x > MAX_X) mParams.x = MAX_X;
		if(mParams.y > MAX_Y) mParams.y = MAX_Y;
		if(mParams.x < 0) mParams.x = 0;
		if(mParams.y < 0) mParams.y = 0;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setMaxPosition();		//최대값 다시 설정
		optimizePosition();		//뷰 위치 최적화
	}

	@Override
	public void onDestroy() {
		if(mWindowManager != null) {
			if(mLinearLayoutPopupView != null) {
				mWindowManager.removeView(mLinearLayoutPopupView);
				mLinearLayoutPopupView = null;
				mWindowManager = null;
			}

		}
	}


}
