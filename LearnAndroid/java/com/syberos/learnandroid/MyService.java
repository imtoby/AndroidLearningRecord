package com.syberos.learnandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private InfoBinder mBinder = new InfoBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class InfoBinder extends Binder {
        public void start() {
            Log.d(TAG, "start entered");
        }

        public int getCurrentInfo() {
            Log.d(TAG, "getCurrentInfo entered");
            return 0;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate entered");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("This is title")
                .setContentText("This is content")
                .setAutoCancel(false)
                .setOngoing(true)
                .setTicker("This is content")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand entered");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy entered");
    }
}
