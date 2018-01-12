package com.syberos.coolweather.util;

/**
 * Created by toby on 18-1-10.
 */

public class Config {
    public static final String TABLE_PROVINCE = "Province";
    public static final String TABLE_CITY = "City";
    public static final String TABLE_COUNTY = "County";

    public static final String KEY_ID           = "id";
    public static final String PROVINCE_NAME    = "province_name";
    public static final String PROVINCE_CODE    = "province_code";
    public static final String CITY_NAME        = "city_name";
    public static final String CITY_CODE        = "city_code";
    public static final String PROVINCE_ID      = "province_id";
    public static final String COUNTY_NAME      = "county_name";
    public static final String COUNTY_CODE      = "county_code";
    public static final String CITY_ID          = "city_id";

    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;

    public static final String CREATE_PROVINCE = "create table " + TABLE_PROVINCE +
            " (id integer primary key autoincrement, " + PROVINCE_NAME + " text, " +
            PROVINCE_CODE + " text)";

    public static final String CREATE_CITY = "create table " + TABLE_CITY +
            " (id integer primary key autoincrement, " + CITY_NAME + " text, " +
            CITY_CODE + " text, " + PROVINCE_ID + " integer)";

    public static final String CREATE_COUNTY = "create table " + TABLE_COUNTY +
            " (id integer primary key autoincrement, " + COUNTY_NAME + " text, " +
            COUNTY_CODE + " text, " + CITY_ID + " integer)";

    public static final int LEVEL_PROVINCE  = 0;
    public static final int LEVEL_CITY      = 1;
    public static final int LEVEL_COUNTY    = 2;

    public static final String QUERY_TYPE_COUNTY_CODE   = "countyCode";
    public static final String QUERY_TYPE_WEATHER_CODE  = "weatherCode";

    public static final String SP_KEY_CITY_SELECTED     = "city_selected";
    public static final String SP_KEY_CITY_NAME         = "city_name";
    public static final String SP_KEY_WEATHER_CODE      = "weather_code";
    public static final String SP_KEY_TEMP1             = "temp1";
    public static final String SP_KEY_TEMP2             = "temp2";
    public static final String SP_KEY_WEATHER_DESP      = "weather_desp";
    public static final String SP_KEY_PUBLISH_TIME      = "publish_time";
    public static final String SP_KEY_CURRENT_DATE      = "current_date";

    public static final String KEY_FROM_WEATHER_ACTIVITY = "from_weather_activity";
}
