package com.syberos.learnandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by toby on 18-1-9.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context globalContext() {
        return context;
    }
}
