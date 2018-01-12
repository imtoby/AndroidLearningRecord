package com.syberos.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syberos.coolweather.R;
import com.syberos.coolweather.service.AutoUpdateService;
import com.syberos.coolweather.util.Config;
import com.syberos.coolweather.util.HttpCallbackListener;
import com.syberos.coolweather.util.HttpUtil;
import com.syberos.coolweather.util.Utility;

public class WeatherActivity extends Activity {

    private static final String TAG = "ChooseAreaActivity";

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);

        weatherInfoLayout = findViewById(R.id.weather_info_layout);
        cityNameText = findViewById(R.id.city_name);
        publishText = findViewById(R.id.publish_text);
        weatherDespText = findViewById(R.id.weather_desp);
        temp1Text = findViewById(R.id.temp1);
        temp2Text = findViewById(R.id.temp2);
        currentDateText = findViewById(R.id.current_date);
        String countyCode = getIntent().getStringExtra(Config.COUNTY_CODE);
        if (!TextUtils.isEmpty(countyCode)) {
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            // 无县级代号时就直接显示本地天气
            showWeather();
        }
    }

    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, Config.QUERY_TYPE_COUNTY_CODE);
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, Config.QUERY_TYPE_WEATHER_CODE);
    }

    private void queryFromServer(final String address, final String type) {
        Log.d(TAG, "address: " + address);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (Config.QUERY_TYPE_COUNTY_CODE.equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (null != array && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if (Config.QUERY_TYPE_WEATHER_CODE.equals(type)) {
                    // 处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString(Config.SP_KEY_CITY_NAME, ""));
        temp1Text.setText(prefs.getString(Config.SP_KEY_TEMP1, ""));
        temp2Text.setText(prefs.getString(Config.SP_KEY_TEMP2, ""));
        weatherDespText.setText(prefs.getString(Config.SP_KEY_WEATHER_DESP, ""));
        publishText.setText(String.format("%s发布",
                prefs.getString(Config.SP_KEY_PUBLISH_TIME, "")));
        currentDateText.setText(prefs.getString(Config.SP_KEY_CURRENT_DATE, ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    public void switchCity(View view) {
        Intent intent = new Intent(this, ChooseAreaActivity.class);
        intent.putExtra(Config.KEY_FROM_WEATHER_ACTIVITY, true);
        startActivity(intent);
        finish();
    }

    public void refreshWeather(View view) {
        publishText.setText("同步中...");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString(Config.SP_KEY_WEATHER_CODE, "");
        if (!TextUtils.isEmpty(weatherCode)) {
            queryWeatherInfo(weatherCode);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
