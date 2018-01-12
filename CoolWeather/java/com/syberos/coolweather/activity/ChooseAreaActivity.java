package com.syberos.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.syberos.coolweather.R;
import com.syberos.coolweather.db.CoolWeatherDB;
import com.syberos.coolweather.model.City;
import com.syberos.coolweather.model.County;
import com.syberos.coolweather.model.Province;
import com.syberos.coolweather.util.Config;
import com.syberos.coolweather.util.HttpCallbackListener;
import com.syberos.coolweather.util.HttpUtil;
import com.syberos.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends Activity {

    private static final String TAG = "ChooseAreaActivity";

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;

    private int currentLevel;

    private boolean isFromWeatherActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromWeatherActivity
                = getIntent().getBooleanExtra(Config.KEY_FROM_WEATHER_ACTIVITY, false);

        if (!isFromWeatherActivity) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if (prefs.getBoolean(Config.SP_KEY_CITY_SELECTED, false)) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_area);

        listView = findViewById(R.id.list_view);
        titleText = findViewById(R.id.title_text);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        coolWeatherDB = CoolWeatherDB.getInstance(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Config.LEVEL_PROVINCE == currentLevel) {
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (Config.LEVEL_CITY == currentLevel) {
                    selectedCity = cityList.get(i);
                    queryCounties();
                } else if (Config.LEVEL_COUNTY == currentLevel) {
                    String countyCode = countyList.get(i).getCode();
                    Intent intent = new Intent(ChooseAreaActivity.this,
                            WeatherActivity.class);
                    intent.putExtra(Config.COUNTY_CODE, countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });

        queryProvinces();
    }

    private void queryProvinces() {
        provinceList = coolWeatherDB.loadProvinces();

        if (provinceList.size() > 0) {
            dataList.clear();

            for (Province province : provinceList) {
                dataList.add(province.getName());
            }

            adapter.notifyDataSetChanged();

            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = Config.LEVEL_PROVINCE;
        } else {
            queryFromServer(null, Config.TABLE_PROVINCE);
        }
    }

    private void queryCities() {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();

            for (City city : cityList) {
                dataList.add(city.getName());
            }

            adapter.notifyDataSetChanged();

            listView.setSelection(0);

            titleText.setText(selectedProvince.getName());

            currentLevel = Config.LEVEL_CITY;
        } else {
            queryFromServer(selectedProvince.getCode(), Config.TABLE_CITY);
        }
    }

    private void queryCounties() {
        countyList = coolWeatherDB.loadCounties(selectedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();

            for (County county : countyList) {
                dataList.add(county.getName());
            }

            adapter.notifyDataSetChanged();

            listView.setSelection(0);

            titleText.setText(selectedCity.getName());
            currentLevel = Config.LEVEL_COUNTY;
        } else {
            queryFromServer(selectedCity.getCode(), Config.TABLE_COUNTY);
        }
    }

    private void queryFromServer(final String code, final String type) {
        String address;

        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }

        showProgressDialog();

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;

                if (Config.TABLE_PROVINCE.equals(type)) {
                    result = Utility.handleProvincesResponse(coolWeatherDB, response);
                } else if (Config.TABLE_CITY.equals(type)) {
                    result = Utility.handleCitiesResponse(coolWeatherDB, response,
                            selectedProvince.getId());
                } else if (Config.TABLE_COUNTY.equals(type)) {
                    result = Utility.handleCountiesResponse(coolWeatherDB, response,
                            selectedCity.getId());
                }

                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();

                            if (Config.TABLE_PROVINCE.equals(type)) {
                                queryProvinces();
                            } else if (Config.TABLE_CITY.equals(type)) {
                                queryCities();
                            } else if (Config.TABLE_COUNTY.equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();

                        Toast.makeText(ChooseAreaActivity.this, "加载失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (Config.LEVEL_COUNTY == currentLevel) {
            queryCities();
        } else if (Config.LEVEL_CITY == currentLevel) {
            queryProvinces();
        } else {
            if (isFromWeatherActivity) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
