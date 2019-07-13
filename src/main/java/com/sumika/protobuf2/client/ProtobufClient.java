package com.sumika.protobuf2.client;

import java.io.IOException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProtobufClient {
	public static void main(String[] args) throws InterruptedException, IOException {
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ProtobufClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8899).sync();
			Channel channel = channelFuture.channel();
			channel.closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
