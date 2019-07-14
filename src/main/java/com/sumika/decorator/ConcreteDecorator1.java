package com.sumika.decorator;

public class ConcreteDecorator1 extends Decorator {

	public ConcreteDecorator1(Component component) {
		super(component);
	}
	
	@Override
	public void doSomething() {
		super.doSomething();
		this.doAnothingThing();
	}
	private void doAnothingThing() {
		System.out.println("功能b");
	}

}
