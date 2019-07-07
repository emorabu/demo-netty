package com.sumika.netty.socket.server;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/***
 * 这里的泛型是实际传输的类型
 * 
 * @author emora
 *
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("server output: " + ctx.channel().remoteAddress() + " : " + msg);
		ctx.channel().writeAndFlush("from server: " + new Date()); // 返回内容较多时, 可以写多个 write(), 最后再写一个 flush()
	}

	/**
	 * 异常处理
	 * 这里打印异常并关闭连接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("from server...");
	}
}
