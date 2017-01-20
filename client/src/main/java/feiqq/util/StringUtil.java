package feiqq.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isEqual(String str1, String str2) {
		if (str1 == str2 || str1.equals(str2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建发送人信息
	 * @param senderName 发送人姓名
	 * @return
	 */
	public static String createSenderInfo(String senderName) {
		return senderName + Constants.SPACE + TimeUtil.getCurrentTime() + Constants.NEWLINE;
	}
	
	/**
	 * 创建消息
	 * @param text 内容
	 * @param enterKey 是否回车发送
	 * @return
	 */
	public static String createMsgInfo(String text, boolean enterKey) {
		// 鼠标点击
		if (!enterKey) {
			text += Constants.NEWLINE;
		}
		return text;
	}
	
}
