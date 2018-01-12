package com.syberos.coolweather.util;

/**
 * Created by toby on 18-1-10.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
