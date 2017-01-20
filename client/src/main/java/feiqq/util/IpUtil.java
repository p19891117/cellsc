package feiqq.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

	/** 获取本地IP信息 */
	public static InetAddress getLocalHost() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			return address;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/** 获取本地IP信息 */
	public static String getLocalHostStr() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
