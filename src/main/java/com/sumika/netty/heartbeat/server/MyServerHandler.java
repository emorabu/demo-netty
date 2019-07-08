package com.sumika.netty.heartbeat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/***
 * 更改了继承的类: ChannelInboundHandlerAdapter
 * 
 * @author emora
 *
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 第二个参数为事件对象
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (!(evt instanceof IdleStateEvent)) {
			return;
		}
		IdleStateEvent event = (IdleStateEvent) evt;
		String eventType = null;

		switch (event.state()) {
		case READER_IDLE:
			eventType = "读空闲"; // 客户端未发送任何信息
			break;
		case WRITER_IDLE:
			eventType = "写空闲"; // 服务端收到了客户端请求的数据, 但不向客户端响应数据
			break;
		case ALL_IDLE:
			eventType = "读写空闲"; // 读操作或写操作任意一个都没执行
			break;
		}
		
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress()+" 超时事件: "+ eventType);
		channel.close();
	}

}
