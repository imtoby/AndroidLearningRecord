package com.syberos.coolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.syberos.coolweather.util.Config;

/**
 * Created by toby on 18-1-10.
 */

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

    public CoolWeatherOpenHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Config.CREATE_PROVINCE);
        sqLiteDatabase.execSQL(Config.CREATE_CITY);
        sqLiteDatabase.execSQL(Config.CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
