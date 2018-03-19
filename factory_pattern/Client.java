package com.syberos.learnjava.factory_pattern;

/**
 * Created by toby on 18-3-19.
 */

public class Client {
    public static void main() {
        Factory factory = new ConcreteFactory();
        Product product = factory.createProduct(ConcreteProductA.class);
        product.method();
        product = factory.createProduct(ConcreteProductB.class);
        product.method();
    }
}
