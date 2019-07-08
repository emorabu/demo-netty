package com.sumika.netty.heartbeat.server;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * 客户端一旦和服务端成功建立连接, 就会创建该类的实例, 并调用该方法
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				// 空闲检测, 一段时间内没有读写操作就触发IdleStateEvent事件, 参数分别为: 读时间, 写时间, 读+写的时间
				.addLast("IdleStateHandler", new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS))
				.addLast("MyServerHandler", new MyServerHandler());
	}

}
