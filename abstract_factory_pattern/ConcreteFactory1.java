package com.syberos.learnjava.abstract_factory_pattern;

/**
 * Created by toby on 18-3-20.
 */

// 具体工厂类 1
public class ConcreteFactory1 extends AbstractFactory {
    @Override
    public AbstractProductA createProductA() {
        return new ConcreteProductA1();
    }

    @Override
    public AbstractProductB createProductB() {
        return new ConcreteProductB1();
    }
}
