package feiqq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static String type = "yyyy/MM/dd HH:mm:ss";
	
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(new Date());
	}
	
}
