package com.sumika.decorator;

public class ConcreteDecorator2 extends Decorator {

	public ConcreteDecorator2(Component component) {
		super(component);
	}
	
	@Override
	public void doSomething() {
		super.doSomething();
		this.doAnothingThing();
	}
	private void doAnothingThing() {
		System.out.println("功能c");
	}

}
