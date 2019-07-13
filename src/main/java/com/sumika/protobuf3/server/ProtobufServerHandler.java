package com.sumika.protobuf3.server;

import com.sumika.protobuf3.DataInfo;
import com.sumika.protobuf3.DataInfo.Animal;
import com.sumika.protobuf3.DataInfo.Data;
import com.sumika.protobuf3.DataInfo.Data.DataType;
import com.sumika.protobuf3.DataInfo.Person;
import com.sumika.protobuf3.DataInfo.Plant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufServerHandler  extends SimpleChannelInboundHandler<DataInfo.Data> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Data msg) throws Exception {
		DataType dataType = msg.getDataType();
		switch (dataType) {
		case PersonType:
			Person person = msg.getPerson();
			System.out.println(person.getName()+"--"+person.getAge()+"--"+person.getAddress());
			break;
		case AnimalType:
			Animal animal = msg.getAnimal();
			System.out.println(animal.getName()+"--"+animal.getColor());
			break;
		case PlantType:
			Plant plant = msg.getPlant();
			System.out.println(plant.getName()+"--"+plant.getAddress());
			break;
		}
		
		System.out.println(msg);
		/**
		旺财--黄色
		data_type: AnimalType
		animal {
		  name: "\346\227\272\350\264\242"
		  color: "\351\273\204\350\211\262"
		}
		 */
	}

}
