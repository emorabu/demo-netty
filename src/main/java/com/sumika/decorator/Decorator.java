package com.sumika.decorator;

/**
 * 实现接口, 并持有该接口的引用
 * @author emora
 *
 */
public class Decorator implements Component {
	private Component component;

	@Override
	public void doSomething() {
		component.doSomething();
	}

	public Decorator(Component component) {
		this.component = component;
	}

}
