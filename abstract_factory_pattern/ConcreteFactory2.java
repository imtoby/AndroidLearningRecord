package com.syberos.learnjava.abstract_factory_pattern;

/**
 * Created by toby on 18-3-20.
 */

// 具体工厂类 2
public class ConcreteFactory2 extends AbstractFactory {
    @Override
    public AbstractProductA createProductA() {
        return new ConcreteProductA2();
    }

    @Override
    public AbstractProductB createProductB() {
        return new ConcreteProductB2();
    }
}
