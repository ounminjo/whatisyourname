package com.macgong.test;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.admin.WIYN.socket.SocketData;
import com.admin.WIYN.socket.Socket_StaticClass;

public class DataHandler extends Handler	{
	public void handleMessage(Message msg)		{
		switch (msg.what)			{
		case Socket_StaticClass.SUCCESS_RESPOND:
			SocketData socketData = (SocketData)msg.obj;

			if(Socket_StaticClass.SET_DATA.equals(socketData.getCommend())){
				System.out.println("Set_DATA 성공");
			}
			else if(Socket_StaticClass.GET_DATA.equals(socketData.getCommend())){
									
			}
			
			//처음에 Loading액티비티에서 한번만 사용하므로 그룹과 멤버를 모두 불러와 그룹트리 만들때 사용
			else if(Socket_StaticClass.GET_ALL_GROUP.equals(socketData.getCommend())){
				Group.setRootTreeOfGroup(Group.makeGroup(socketData.getArrayListMap()));
			}
			else if(Socket_StaticClass.GET_ALL_MEMBER.equals(socketData.getCommend())){
				ArrayList<Map<String, String>> dataList = socketData.getArrayListMap();
				for(int i=0;i<dataList.size();i++){
					Group.setGroupData(dataList.get(i));	//해당 그룹에 데이터 넣기
				}
			}
			
			else if(Socket_StaticClass.GET_ADMIN.equals(socketData.getCommend())){
				Group.InitAdmin(socketData.getArrayListMap());
			}
			
			
			else if(Socket_StaticClass.DELETE_DATA.equals(socketData.getCommend())){
				System.out.println("삭제 성공");
			}
			
			else if(Socket_StaticClass.SET_DATA.equals(socketData.getCommend())){
				System.out.println("삽입 성공");
			}
			break;
		case Socket_StaticClass.ERROR_RESPOND:
			break;
		default:				
			break;
		}
	}
}