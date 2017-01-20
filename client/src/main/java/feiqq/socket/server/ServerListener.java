package feiqq.socket.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class ServerListener implements ChannelFutureListener {

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if (future.isSuccess()) {
			System.out.println("服务器操作完成！");
		} else {
			System.out.println("服务器操作失败！");
		}
	}

}
