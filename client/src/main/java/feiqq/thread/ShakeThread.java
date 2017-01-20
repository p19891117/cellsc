package feiqq.thread;

import feiqq.ui.frame.ChatRoom;

public class ShakeThread extends Thread {

	private ChatRoom room;
	
	public ShakeThread(ChatRoom room) {
		this.room = room;
	}

	@Override
	public void run() {
		try {
			int x = room.getX();
			int y = room.getY();
			for (int i = 0; i < 20; i++) {
				if ((i & 1) == 0) {
					x += 3;
					y += 3;
				} else {
					x -= 3;
					y -= 3;
				}
				room.setLocation(x, y);
				// 睡一会儿
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
