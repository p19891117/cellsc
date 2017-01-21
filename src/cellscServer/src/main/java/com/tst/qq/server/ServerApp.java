package com.tst.qq.server;

public class ServerApp {

	public static void main(String[] args) {
		NettyServer server = new NettyServer();
		server.startServer();
	}
	
}
