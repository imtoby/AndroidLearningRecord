package com.syberos.learnjava.factory_pattern;

/**
 * Created by toby on 18-3-19.
 */

public class FileHandler extends IOHandler {
    @Override
    public void add(String id, String name) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void update(String id, String name) {

    }

    @Override
    public String query(String id) {
        return "File Handler";
    }
}
