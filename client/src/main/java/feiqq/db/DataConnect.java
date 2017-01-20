package feiqq.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

	private static Connection conn;

	private DataConnect() {

	}

	public static Connection getConnect() {
		try {
			if (null == conn) {
				String driver = "oracle.jdbc.driver.OracleDriver";
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";
				Class.forName(driver);
				conn = DriverManager.getConnection(url, "scott", "tiger");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
