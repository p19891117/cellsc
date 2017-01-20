package feiqq.socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import feiqq.socket.server.ServerHandler;
import feiqq.util.Constants;

/**
 * Description: 服务器 <br/>
 * Date: 2014年11月22日 下午8:23:05 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class Server {

	private Map<SocketAddress, String> map = new HashMap<SocketAddress, String>();
	private Map<String, Channel> clientMap = new HashMap<String, Channel>();

	public void startServer() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
					ch.pipeline().addLast(new LengthFieldPrepender(2, false));
					ch.pipeline().addLast(new ServerHandler(map, clientMap));
				}
			});
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = bootstrap.bind(Constants.SERVER_PORT).sync();
			System.err.println("IP地址为："+Constants.SERVER_IP+"端口为："+Constants.SERVER_PORT);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void getInstance() {
		Server server = new Server();
		server.startServer();
	}
	
//	public static void main(String[] args) {
//		Server server = new Server();
//		server.startServer();
//	}

}
