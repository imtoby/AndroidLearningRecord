package com.syberos.learnjava.factory_pattern;

/**
 * Created by toby on 18-3-19.
 */

public abstract class IOHandler {
    public abstract void add(String id, String name);
    public abstract void remove(String id);
    public abstract void update(String id, String name);
    public abstract String query(String id);
}
