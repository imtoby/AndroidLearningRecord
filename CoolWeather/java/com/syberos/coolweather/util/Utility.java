package com.syberos.coolweather.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.syberos.coolweather.db.CoolWeatherDB;
import com.syberos.coolweather.model.City;
import com.syberos.coolweather.model.County;
import com.syberos.coolweather.model.Province;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by toby on 18-1-10.
 */

public class Utility {
    private static final String TAG = "Utility";

    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,
                                                               String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (null != allProvinces && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setCode(array[0]);
                    province.setName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
                                                            String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (null != allCities && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCode(array[0]);
                    city.setName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
                                                            String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (null != allCounties && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCode(array[0]);
                    county.setName(array[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            Log.d(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");

            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context, String cityName, String weatherCode,
                                       String temp1, String temp2, String weatherDesp,
                                       String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);

        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor
                = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(Config.SP_KEY_CITY_SELECTED, true);
        editor.putString(Config.SP_KEY_CITY_NAME, cityName);
        editor.putString(Config.SP_KEY_WEATHER_CODE, weatherCode);
        editor.putString(Config.SP_KEY_TEMP1, temp1);
        editor.putString(Config.SP_KEY_TEMP2, temp2);
        editor.putString(Config.SP_KEY_WEATHER_DESP, weatherDesp);
        editor.putString(Config.SP_KEY_PUBLISH_TIME, publishTime);
        editor.putString(Config.SP_KEY_CURRENT_DATE, sdf.format(new Date()));
        editor.apply();
    }
}
