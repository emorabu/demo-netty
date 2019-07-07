package com.sumika.netty.http;

import java.net.URI;

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

/**
 *  in bound: 进来, out bound: 出去
 * @author emora
 *
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	/**
	 * 接收客户端请求并完成响应
	 * curl 默认使用 get 方式请求, 指定 post 请求: curl -X POST  'http://localhost:8899/'
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		System.out.println(msg.getClass()); // class io.netty.handler.codec.http.DefaultHttpRequest, class io.netty.handler.codec.http.LastHttpContent$1
		System.out.println(ctx.channel().remoteAddress()); // curl: /0:0:0:0:0:0:0:1:7131, 浏览器: /0:0:0:0:0:0:0:1:7132
		/**
		 * 没有这个判断就会抛出异常:
		 * 	 * 异常: 警告: An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		 */
		if(!(msg instanceof HttpRequest)) {
			return;
		}
		/*
		DefaultHttpRequest(decodeResult: success, version: HTTP/1.1)
		GET / HTTP/1.1
		Host: localhost:8899
		User-Agent: curl/7.50.3
		Accept: 

		 */
	//	System.out.println(msg);
		HttpRequest request = (HttpRequest) msg;
	//	System.out.println(request.method()+":"+request.method().name()); //GET:GET
		URI uri = new URI(request.uri());
	//	System.out.println(uri.getPath()); // uri 路径, 根路径为 / , 其他类推
		if("/favicon.ico".equals(uri.getPath())) {// 以此可以防止浏览器对favicon.ico图标的请求
			return;
		}
		
		ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content); // 参数为HTTP协议的版本,响应码和响应内容
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, content); 
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
			.set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes()); // 设置请求头, 这里包括类型和长度
		ctx.writeAndFlush(response); // write() 只会将内存放到缓冲区, 并没有响应给客户端
		System.out.println("TestHttpServerHandler.channelRead0()");
//		ctx.channel().close(); // 关闭连接
	}
	
	/**
	 * 执行顺序:
	curl:
	TestHttpServerHandler.handlerAdded()
	TestHttpServerHandler.channelRegistered()
	TestHttpServerHandler.channelActive()
	TestHttpServerHandler.channelRead0()
	TestHttpServerHandler.channelInactive()
	TestHttpServerHandler.channelUnregistered()
	浏览器:
		(不一定)
		TestHttpServerHandler.channelRead0()
		TestHttpServerHandler.channelInactive()
		TestHttpServerHandler.channelUnregistered()
	关闭标签页后:
	TestHttpServerHandler.channelInactive()
	TestHttpServerHandler.channelUnregistered()
	
	当获取到客户端请求后, 如果请求基于 HTTP1.1 协议, 会根据 keep-alive 的时间, 如超过3秒客户端没有发出新的请求, 服务端会主动关闭该连接; 
	如果基于 HTTP1.0 协议, 请求发送完服务器端就关闭连接
	tomcat, jetty 等 Servlet 容器会决定如何关闭连接, 而 netty 可以由开发者决定如何关闭
	 */
	
	/**
	 * 通道变为活动状态时会执行该方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("TestHttpServerHandler.channelActive()");
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("TestHttpServerHandler.channelRegistered()");
		super.channelRegistered(ctx);
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("TestHttpServerHandler.handlerAdded()");
		super.handlerAdded(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("TestHttpServerHandler.channelInactive()");
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("TestHttpServerHandler.channelUnregistered()");
		super.channelUnregistered(ctx);
	}
}
