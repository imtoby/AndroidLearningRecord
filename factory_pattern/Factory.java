package com.syberos.learnjava.factory_pattern;

/**
 * Created by toby on 18-3-19.
 */

public abstract class Factory {
    public abstract <T extends Product> T createProduct(Class<T> clz);
}
