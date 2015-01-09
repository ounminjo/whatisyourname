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
	private LinearLayout mLinearLayoutPopupView;							//�׻� ���̰� �� ��
	private WindowManager.LayoutParams mParams;		//layout params ��ü. ���� ��ġ �� ũ�⸦ �����ϴ� ��ü
	private WindowManager mWindowManager;			//������ �Ŵ���

	private float START_X, START_Y;							//�����̱� ���� ��ġ�� ���� ��
	private int PREV_X, PREV_Y;								//�����̱� ������ �䰡 ��ġ�� ��
	private int MAX_X = -1, MAX_Y = -1;					//���� ��ġ �ִ� ��

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

		// ���񽺰� �����Ǹ� �绡�� �� ���� ��Ų��.
		return START_STICKY ;

	}

	private OnTouchListener mViewTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:				//����� ��ġ �ٿ��̸�
				if(MAX_X == -1)
					setMaxPosition();
				START_X = event.getRawX();					//��ġ ���� ��
				START_Y = event.getRawY();					//��ġ ���� ��
				PREV_X = mParams.x;							//���� ���� ��
				PREV_Y = mParams.y;							//���� ���� ��
				break;
			case MotionEvent.ACTION_MOVE:
				int x = (int)(event.getRawX() - START_X);	//�̵��� �Ÿ�
				int y = (int)(event.getRawY() - START_Y);	//�̵��� �Ÿ�

				//��ġ�ؼ� �̵��� ��ŭ �̵� ��Ų��
				mParams.x = PREV_X - x;
				mParams.y = PREV_Y + y;

				optimizePosition();		//���� ��ġ ����ȭ
				mWindowManager.updateViewLayout(mLinearLayoutPopupView, mParams);	//�� ������Ʈ
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
				mLinearLayoutPopupView.setOnTouchListener(mViewTouchListener);										//�˾��信 ��ġ ������ ���
				mLinearLayoutPopupView.setBackgroundColor(getResources().getColor(R.color.lightblue));
				
				LinearLayout.LayoutParams mLayoutParamsPopUpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				mLinearLayoutPopupView.setLayoutParams(mLayoutParamsPopUpView);
				
				
				
				

				//�� ����
				TextView textViewCallNumber = new TextView(mIncomingCallService);	
				
				textViewCallNumber.setText("��ȭ��ȣ : " + incomingNumber);	//�ؽ�Ʈ ����
				textViewCallNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);								//�ؽ�Ʈ ũ�� 18sp
				textViewCallNumber.setTextColor(Color.BLUE);															//���� ����
				textViewCallNumber.setBackgroundColor(Color.argb(127, 0, 255, 255));								//�ؽ�Ʈ�� ��� ��				
				mLinearLayoutPopupView.addView(textViewCallNumber);
				
				
				Button buttonClose = new Button(mIncomingCallService);
				buttonClose.setText("�ݱ�");
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
				
				

				//�ֻ��� �����쿡 �ֱ� ���� ����
				mParams = new WindowManager.LayoutParams(
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_PHONE,					//�׻� �� ������ �ְ�. status bar �ؿ� ����. ��ġ �̺�Ʈ ���� �� ����.
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,		//�� �Ӽ��� ���ָ� ��ġ & Ű �̺�Ʈ�� �԰� �ȴ�. 
						//��Ŀ���� ���༭ �ڱ� ���� ����ġ�� �ν� ���ϰ� Ű�̺�Ʈ�� ������� �ʰ� ����
						PixelFormat.TRANSLUCENT);										//����
				mParams.gravity = Gravity.RIGHT | Gravity.TOP;						//���� ��ܿ� ��ġ�ϰ� ��.

				mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);	//������ �Ŵ��� �ҷ���.
				mWindowManager.addView(mLinearLayoutPopupView, mParams);		//�ֻ��� �����쿡 �� �ֱ�. *�߿� : ���⿡ permission�� �̸� ������ �ξ�� �Ѵ�. �Ŵ��佺Ʈ��

				
			} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
			
			}
		}
	}

	private void setMaxPosition() {
		DisplayMetrics matrix = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(matrix);		//ȭ�� ������ �����ͼ�

		MAX_X = matrix.widthPixels; // - mLinearLayoutPopupView.getWidth();			//x �ִ밪 ����
		MAX_Y = matrix.heightPixels; // - mLinearLayoutPopupView.getHeight();			//y �ִ밪 ����
	}

	private void optimizePosition() {
		//�ִ밪 �Ѿ�� �ʰ� ����
		if(mParams.x > MAX_X) mParams.x = MAX_X;
		if(mParams.y > MAX_Y) mParams.y = MAX_Y;
		if(mParams.x < 0) mParams.x = 0;
		if(mParams.y < 0) mParams.y = 0;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setMaxPosition();		//�ִ밪 �ٽ� ����
		optimizePosition();		//�� ��ġ ����ȭ
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
