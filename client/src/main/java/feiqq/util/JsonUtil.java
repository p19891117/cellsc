package feiqq.util;

import com.google.gson.Gson;

import feiqq.bean.Message;

/**
 * Description: Json工具类 <br/>
 * Date: 2014年11月21日 下午10:09:23 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class JsonUtil {

	/** 将对象转化为Json字符 */
	public static String transToJson(Message message) {
		Gson gson = new Gson();
		String text = gson.toJson(message);
		return text;
	}

	/** 将Json字符转化为对象 */
	public static Message transToBean(String text) {
		Gson gson = new Gson();
		Message message = gson.fromJson(text, Message.class);
		return message;
	}

}
