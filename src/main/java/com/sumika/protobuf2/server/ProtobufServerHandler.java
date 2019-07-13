package com.sumika.protobuf2.server;

import com.sumika.protobuf2.PersonInfo;
import com.sumika.protobuf2.PersonInfo.Person;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufServerHandler  extends SimpleChannelInboundHandler<PersonInfo.Person> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
		System.out.println(msg);
		/*
		name: "\346\235\216\345\233\233"
		age: 30
		address: "\345\214\227\344\272\254"
		 * */
	}
}
