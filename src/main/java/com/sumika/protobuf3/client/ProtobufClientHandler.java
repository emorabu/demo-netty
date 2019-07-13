package com.sumika.protobuf3.client;

import java.util.Random;

import com.sumika.protobuf3.DataInfo;
import com.sumika.protobuf3.DataInfo.Animal;
import com.sumika.protobuf3.DataInfo.Data;
import com.sumika.protobuf3.DataInfo.Data.DataType;
import com.sumika.protobuf3.DataInfo.Person;
import com.sumika.protobuf3.DataInfo.Plant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<DataInfo.Data> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Data msg) throws Exception {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int randomNumber = new Random().nextInt(3);
		DataType dataType = null;
		DataInfo.Data data = null;
		switch (randomNumber) {
		case 0:
			Person person = DataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("西安").build();
			dataType = DataType.PersonType;
			data = DataInfo.Data.newBuilder().setDataType(dataType).setPerson(person).build();
			break;
		case 1:
			Animal animal = DataInfo.Animal.newBuilder().setName("旺财").setColor("黄色").build();
			dataType = DataType.AnimalType;
			data = DataInfo.Data.newBuilder().setDataType(dataType).setAnimal(animal).build();
			break;
		case 2:
			Plant plant = DataInfo.Plant.newBuilder().setName("果树").setAddress("中国").build();
			dataType = DataType.PlantType;
			data = DataInfo.Data.newBuilder().setDataType(dataType).setPlant(plant).build();
			break;
		}
		
		ctx.channel().writeAndFlush(data);
	}

}