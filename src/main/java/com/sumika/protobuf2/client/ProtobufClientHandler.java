package com.sumika.protobuf2.client;

import com.sumika.protobuf2.PersonInfo;
import com.sumika.protobuf2.PersonInfo.Person;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufClientHandler  extends SimpleChannelInboundHandler<PersonInfo.Person> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
		System.out.println(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		PersonInfo.Person person = PersonInfo.Person.newBuilder().setName("李四").setAge(30).setAddress("北京").build();
		ctx.channel().writeAndFlush(person);
	}
}