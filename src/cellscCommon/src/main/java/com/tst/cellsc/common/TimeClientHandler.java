package com.tst.cellsc.common;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 ByteBuf m = (ByteBuf) msg; // (1)
	        try {
	            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
	            System.out.println(new Date(currentTimeMillis));
	            ctx.close();
	        } finally {
	            m.release();
	        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] data = ("客户端时间："+System.currentTimeMillis()).getBytes("UTF-8");
		ByteBuf buf = Unpooled.buffer(data.length);
		buf.writeBytes(data);
		ctx.writeAndFlush(buf);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}


}
