package com.tst.qq.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

	private static Connection conn;

	private DataConnect() {

	}
	public static Connection getConnect() {
		try {
			if (null == conn) {
				String driver = "com.mysql.driver.Driver";
				String url = "jdbc:mysql://localhost:3306/cellsc";
				Class.forName(driver);
				conn = DriverManager.getConnection(url, "scott", "tiger");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
