package com.tst.qq;

import com.tst.qq.server.NettyServer;

public class ServerApp {

	public static void main(String[] args) {
		NettyServer server = new NettyServer();
		server.startServer();
	}
	
}
