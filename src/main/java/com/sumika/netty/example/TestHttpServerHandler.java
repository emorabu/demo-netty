package com.sumika.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	/**
	 * 接收客户端请求并完成响应
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		/**
		 * 没有这个判断就会抛出异常:
		 * 	 * 异常: 警告: An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		 */
		if(!(msg instanceof HttpRequest)) {
			return;
		}
		
		ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content); // 参数为HTTP协议的版本,响应码和响应内容
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
			.set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes()); // 设置请求头, 这里包括类型和长度
		ctx.writeAndFlush(response); // write() 只会将内存放到缓冲区, 并没有响应给客户端
	}
}
