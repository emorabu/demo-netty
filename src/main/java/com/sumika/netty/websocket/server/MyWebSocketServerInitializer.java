package com.sumika.netty.websocket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 只用于处理文本
 * @author emora
 *
 */
public class MyWebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast(new HttpServerCodec())
				.addLast(new ChunkedWriteHandler()) // 以块的方式来写的处理器
				.addLast(new HttpObjectAggregator(8192)) // netty 对请求和响应分块, 聚合形成完整的请求和响应
				.addLast(new WebSocketServerProtocolHandler("/ws")) // 处理 WebSocket, 握手, 控制, 数据以帧(6种)的形式传递. 参数为ws url 地址.
				.addLast(new TextWebSocketFrameHandler());
	}

}
