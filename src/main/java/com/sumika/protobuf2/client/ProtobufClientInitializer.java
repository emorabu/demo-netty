package com.sumika.protobuf2.client;

import com.sumika.protobuf2.PersonInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtobufClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast(new ProtobufVarint32FrameDecoder())
				.addLast(new ProtobufDecoder(PersonInfo.Person.getDefaultInstance())) // 注意这里的参数是生成的 protobuf 类的默认实例
				.addLast(new ProtobufVarint32LengthFieldPrepender())
				.addLast(new ProtobufEncoder())
				.addLast(new ProtobufClientHandler());
	}
}