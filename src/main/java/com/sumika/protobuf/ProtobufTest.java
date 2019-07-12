package com.sumika.protobuf;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sumika.protobuf.StudentInfo.Student;


public class ProtobufTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// 消息本身是不可变的, 要通过 build() 来构建
		StudentInfo.Student student = StudentInfo.Student.newBuilder().setName("张三").setAge(20).setAddress("西安").build();
		byte[] bytes = student.toByteArray();
		Student student2 = Student.parseFrom(bytes);
		System.out.println(student2.toString());
		/*
		name: "\345\274\240\344\270\211"
		age: 20
		address: "\350\245\277\345\256\211" 
		 * 
		 */
		System.out.println(student2.getName()); // 张三
		//CodedOutputStream output = CodedOutputStream.newInstance(new FileOutputStream("f:/temp/6/1.protobuf"));
		//		student.writeTo(output );
	}
}
