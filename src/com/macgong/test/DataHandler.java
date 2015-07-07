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
				System.out.println("Set_DATA ����");
			}
			else if(Socket_StaticClass.GET_DATA.equals(socketData.getCommend())){
									
			}
			
			//ó���� Loading��Ƽ��Ƽ���� �ѹ��� ����ϹǷ� �׷�� ����� ��� �ҷ��� �׷�Ʈ�� ���鶧 ���
			else if(Socket_StaticClass.GET_ALL_GROUP.equals(socketData.getCommend())){
				Group.setRootTreeOfGroup(Group.makeGroup(socketData.getArrayListMap()));
			}
			else if(Socket_StaticClass.GET_ALL_MEMBER.equals(socketData.getCommend())){
				ArrayList<Map<String, String>> dataList = socketData.getArrayListMap();
				for(int i=0;i<dataList.size();i++){
					Group.setGroupData(dataList.get(i));	//�ش� �׷쿡 ������ �ֱ�
				}
			}
			
			else if(Socket_StaticClass.GET_ADMIN.equals(socketData.getCommend())){
				Group.InitAdmin(socketData.getArrayListMap());
			}
			
			
			else if(Socket_StaticClass.DELETE_DATA.equals(socketData.getCommend())){
				System.out.println("���� ����");
			}
			
			else if(Socket_StaticClass.SET_DATA.equals(socketData.getCommend())){
				System.out.println("���� ����");
			}
			break;
		case Socket_StaticClass.ERROR_RESPOND:
			break;
		default:				
			break;
		}
	}
}