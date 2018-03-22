package com.syberos.learnandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class TimerRunningService extends Service {

    private static final String TAG = "TimerRunningService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onStartCommand entered " + new Date().toString());
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final long anHour = 5 * 60 * 1000;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0,  new Intent(MyReceiver.ACTION_START_SERVICE), 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + anHour,
                pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
