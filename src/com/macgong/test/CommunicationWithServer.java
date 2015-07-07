package com.macgong.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;




public class CommunicationWithServer extends AsyncTask<URL, Integer, SocketData> {
	SocketData  mSocketData;
	SocketData output;
	ObjectOutputStream  mObjectOutputStream  = null;
	ObjectInputStream  mObjectInputStream  = null;
	Socket socket = null;
	Handler mhandler=null;

	public CommunicationWithServer(Handler handler, SocketData socketData) {
		this.mSocketData = socketData;
		this.mhandler = handler;
	}

	@Override
	protected SocketData doInBackground(URL... urls) {			
		output = sendMessageServerSocket(mSocketData);
		return output;
	}

	@Override
	protected void onPreExecute() {

	}
	@Override
	protected void onPostExecute(SocketData returnValue) {
		if(output == null){
			return;
		}
		
		// Hnadler 를 호출한 부분으로 결과값을 보내준다.
		Message msg = mhandler.obtainMessage(Socket_StaticClass.SUCCESS_RESPOND, output);
		mhandler.sendMessage(msg);
	}

	private SocketData sendMessageServerSocket(SocketData mSocketData) {
		SocketData returnValue = null;
		if (mObjectOutputStream == null) {
			try {				
				socket = new Socket(Socket_StaticClass.SERVER_IP, Integer.parseInt(Socket_StaticClass.PORT));
				// 소켓을 여는 순서가 중요함. 서버와 쌍이 맞아야함
				mObjectInputStream = new ObjectInputStream(socket.getInputStream());
				mObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		
		if(mObjectOutputStream == null){
			// Hnadler 를 호출한 부분으로 결과값을 보내준다.
			Message msg = mhandler.obtainMessage(Socket_StaticClass.ERROR_RESPOND, "서버 연결에 실패하였습니다.");
			mhandler.sendMessage(msg);
			return null;
		}

		try {
			
			mObjectOutputStream.reset();			
			mObjectOutputStream.writeObject(mSocketData);
			mObjectOutputStream.flush();		

			returnValue = (SocketData)mObjectInputStream.readObject();			

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();

		} catch (IOException e) {	
			e.printStackTrace();
		}
		finally { 
			try { 
				if(mObjectOutputStream != null){
					mObjectOutputStream.close();
					mObjectOutputStream = null;	
				}
				if(mObjectInputStream != null){
					mObjectInputStream.close();				
					mObjectInputStream = null;	
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}
}
