package com.syberos.learnandroid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class AccelerometerActivity extends Activity {

    private static final int THRESHOLD_VALUE = 15;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalToast.show("进入加速监测...", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != sensorManager) {
            sensorManager.unregisterListener(sensorEventListener);
        }

        GlobalToast.show("退出加速监测...", Toast.LENGTH_SHORT);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x_value = Math.abs(sensorEvent.values[0]);
            float y_value = Math.abs(sensorEvent.values[1]);
            float z_value = Math.abs(sensorEvent.values[2]);

            if (x_value > THRESHOLD_VALUE || y_value > THRESHOLD_VALUE
                    || z_value > THRESHOLD_VALUE) {
                GlobalToast.show("设备正在晃动...", Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
}
