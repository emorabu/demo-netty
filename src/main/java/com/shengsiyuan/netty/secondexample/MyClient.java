package com.shengsiyuan.netty.secondexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * socket编程的客户端
 * @Author: zhouwen
 * @Date: 2017/6/10 13:49
 */
public class MyClient {

    public static void main(String[] args) throws Exception {
        // 客户端只有一个事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            // 客户端启动器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}