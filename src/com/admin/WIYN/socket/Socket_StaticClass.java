package com.admin.WIYN.socket;


public class Socket_StaticClass {

	public final static String SERVER_IP = "175.126.167.40";
	public final static String PORT = "4567";	
	public final static int SOCKET_OPEN_COUNT = 100;
	
	public final static String GET_ALL_GROUP = "GET_ALL_GROUP";
	public final static String GET_ALL_MEMBER = "GET_ALL_MEMBER";
	public final static String SET_DATA = "SET_DATA";
	public final static String GET_DATA = "GET_DATA";
	public final static String DELETE_DATA = "DELETE_DATA";
	public final static String GET_ADMIN = "GET_ADMIN";

	// Handler definition
	public final static int SUCCESS_RESPOND = 0;
	public final static int ERROR_RESPOND = 1;

}
