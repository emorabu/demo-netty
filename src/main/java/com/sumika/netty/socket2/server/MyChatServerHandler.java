package com.sumika.netty.socket2.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
	/**
	 * GlobalEventExecutor 是单线程, 单例的, 可以自动启动线程, 并在任务队列中超过1秒没有挂起的任务时停止线程
	 * channelGroup 保存所有与客户端建立连接的 channel
	 */
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 客户端有请求时进行响应
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.forEach(ch -> {
			if (channel != ch) {
				ch.writeAndFlush("[别人]" + channel.remoteAddress() + " 发送了: " + msg + "\r\n");
			} else {
				ch.writeAndFlush("[自己] 发送了: " + msg + "\r\n");
			}
		});
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

	/**
	 * 客户端与服务端建立连接时调用
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 加入" + "\r\n"); // 通知其他客户端有新的客户端连接了
		channelGroup.add(channel);
	}

	/**
	 * 客户端断开连接时调用
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 断开了" + "\r\n");
		// 理论上需要调用 channelGroup.remove(channel); 但实际 netty 会自动移除该客户端
	}

	/**
	 * 客户端处于活动状态时调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + " 上线了");
	}

	/**
	 * 客户端不活动时调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + " 下线了");
	}
}
