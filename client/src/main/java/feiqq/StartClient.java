package feiqq;

import feiqq.socket.client.Client;
import feiqq.ui.frame.LoginWindow;

public class StartClient {

	public static void main(String[] args) {
		try {
			// 这里本来应该是在登陆的时候才应该链接服务器的
			// 这里是为了注册才需要放到这里
			// 像QQ他是直接跳转到web里面去了
			Client client = new Client();
			LoginWindow inst = LoginWindow.getInstance(client);
			client.setLogin(inst);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
