package com.syberos.learnandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

/**
 * Created by toby on 18-1-4.
 */

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    public static final String ACTION_START_SERVICE = "com.syberos.learnandroid.startService";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ACTION_START_SERVICE)) {
            Intent i = new Intent(context, TimerRunningService.class);
            context.startService(i);
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_SHUTDOWN)) {
            Log.d(TAG, Intent.ACTION_SHUTDOWN);
        }
    }
}
