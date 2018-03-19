package com.syberos.learnjava.factory_pattern;

/**
 * Created by toby on 18-3-19.
 */

public class IOFactory {
    public static <T extends IOHandler> T getIOHandler(Class<T> clz) {
        IOHandler handler = null;
        try {
            handler = (IOHandler) Class.forName(clz.getName()).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T) handler;
    }
}
