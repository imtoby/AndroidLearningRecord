package com.syberos.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.syberos.coolweather.model.City;
import com.syberos.coolweather.model.County;
import com.syberos.coolweather.model.Province;
import com.syberos.coolweather.util.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toby on 18-1-10.
 */

public class CoolWeatherDB {
    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    // 私有化构造函数
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper
                = new CoolWeatherOpenHelper(context, Config.DB_NAME, null, Config.VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (null == coolWeatherDB) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveProvince(Province province) {
        if (null != province) {
            ContentValues values = new ContentValues();
            values.put(Config.PROVINCE_NAME, province.getName());
            values.put(Config.PROVINCE_CODE, province.getCode());
            db.insert(Config.TABLE_PROVINCE, null, values);
        }
    }

    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>();

        Cursor cursor = db.query(Config.TABLE_PROVINCE, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex(Config.KEY_ID)));
                province.setName(cursor.getString(cursor.getColumnIndex(Config.PROVINCE_NAME)));
                province.setCode(cursor.getString(cursor.getColumnIndex(Config.PROVINCE_CODE)));
                list.add(province);
            } while (cursor.moveToNext());
        }

        if (null != cursor) {
            cursor.close();
        }

        return list;
    }

    public void saveCity(City city) {
        if (null != city) {
            ContentValues values = new ContentValues();
            values.put(Config.CITY_NAME, city.getName());
            values.put(Config.CITY_CODE, city.getCode());
            values.put(Config.PROVINCE_ID, city.getProvinceId());
            db.insert(Config.TABLE_CITY, null, values);
        }
    }

    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();

        Cursor cursor = db.query(Config.TABLE_CITY, null,
                Config.PROVINCE_ID + " = ?", new String[] {String.valueOf(provinceId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex(Config.KEY_ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(Config.CITY_NAME)));
                city.setCode(cursor.getString(cursor.getColumnIndex(Config.CITY_CODE)));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }

        if (null != cursor) {
            cursor.close();
        }

        return list;
    }

    public void saveCounty(County county) {
        if (null != county) {
            ContentValues values = new ContentValues();
            values.put(Config.COUNTY_NAME, county.getName());
            values.put(Config.COUNTY_CODE, county.getCode());
            values.put(Config.CITY_ID, county.getCityId());
            db.insert(Config.TABLE_COUNTY, null, values);
        }
    }

    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<>();

        Cursor cursor = db.query(Config.TABLE_COUNTY, null,
                Config.CITY_ID + " = ?", new String[] {String.valueOf(cityId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex(Config.KEY_ID)));
                county.setName(cursor.getString(cursor.getColumnIndex(Config.COUNTY_NAME)));
                county.setCode(cursor.getString(cursor.getColumnIndex(Config.COUNTY_CODE)));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }

        if (null != cursor) {
            cursor.close();
        }

        return list;
    }

}
