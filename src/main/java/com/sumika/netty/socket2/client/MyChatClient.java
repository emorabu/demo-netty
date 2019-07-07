package com.sumika.netty.socket2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class MyChatClient {
	public static void main(String[] args) throws InterruptedException, IOException {
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8899).sync();
			Channel channel = channelFuture.channel();

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, CharsetUtil.UTF_8));
			for (;;) {
				channel.writeAndFlush(br.readLine() + "\r\n"); // 必须有 \r\n 否则回车没有反应
			}
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
