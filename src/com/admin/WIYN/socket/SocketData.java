package com.admin.WIYN.socket;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;



public class SocketData implements Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<Map<String, String>> mArrayListMap=null;
	Map<String, String> mMap=null;
	String mCommend;
	public String name;
	public String id;
	public String phone;
	
	public SocketData(String commend,String name,String phonenumber,String id){
		mCommend = commend;
		this.id = id;
		this.name = name;
		this.phone = phonenumber;
	}
	
	public SocketData(String commend, Map<String, String> arrayListMap){
		mCommend = commend;
		mMap = arrayListMap;
	}
	public SocketData(String commend, ArrayList<Map<String, String>>  arrayListMap){
		mCommend = commend;
		mArrayListMap = arrayListMap;
	}
	
	public ArrayList<Map<String, String>> getArrayListMap(){
		return mArrayListMap;
	}

	public Map<String, String> getMap(){
		return mMap;
	}

	public String getCommend(){
		return mCommend;
	}

	
}
