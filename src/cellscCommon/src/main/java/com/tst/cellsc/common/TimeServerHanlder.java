package com.tst.cellsc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TimeServerHanlder extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 try {
			 ByteBuf buff = (ByteBuf) msg;
			 byte[] data = new byte[buff.readableBytes()];
			 buff.readBytes(data);
			 System.out.println(new String(data,"UTF-8"));
		    } finally {
		        ReferenceCountUtil.release(msg);
		    }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}



}
