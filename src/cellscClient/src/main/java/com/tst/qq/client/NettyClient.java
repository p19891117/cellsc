package com.tst.qq.client;

import java.net.InetSocketAddress;

import com.tst.cellsc.common.utils.NettyServerCfg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NettyClient {
	public void init(){
		final ClientHandler clientHandler = new ClientHandler(null);//日了狗，竟然传swing对象
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(new NioEventLoopGroup());
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
					ch.pipeline().addLast(new LengthFieldPrepender(2, false));
					ch.pipeline().addLast(clientHandler);
				}
			});
			ChannelFuture future = bootstrap.connect(new InetSocketAddress(NettyServerCfg.SERVER_IP, NettyServerCfg.SERVER_PORT)).sync();
			// TODO 这里为什么加上了sync()方法之后，启动client类的时候会被阻塞住，导致后面的发消息都不行
			// future.channel().closeFuture().sync();
			future.channel().closeFuture();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	
	}
}
