package com.sumika.decorator;

public class Client {
	public static void main(String[] args) {
		Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
		component.doSomething();
		/**
		功能a
		功能b
		功能c
		 */
		
		component = new ConcreteDecorator1(new ConcreteComponent());
		component.doSomething();
		/**
		功能a
		功能b
		 */
	}
}
