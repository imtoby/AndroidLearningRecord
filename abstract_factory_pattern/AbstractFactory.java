package com.syberos.learnjava.abstract_factory_pattern;

/**
 * Created by toby on 18-3-20.
 */

// 抽象工厂类
public abstract class AbstractFactory {

    public abstract AbstractProductA createProductA();

    public abstract AbstractProductB createProductB();
}
