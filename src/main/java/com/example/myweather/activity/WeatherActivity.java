package com.example.myweather.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.example.myweather.R;
import com.example.myweather.model.CityWeatherDefault;
import com.example.myweather.model.CityWeatherInfo;
import com.example.myweather.util.HttpCallbackListener;
import com.example.myweather.util.HttpUtil;
import com.example.myweather.util.LogUtil;
import com.example.myweather.util.Utility;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherActivity extends BaseActivity {

    private final static String TAG = "WeatherActivity";

    private TextView tvWeatherNumber;
    private TextView tvWeatherDescribe;
    private CityWeatherDefault cityWeatherDefault;
    private CityWeatherInfo cityWeatherInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        tvWeatherNumber = (TextView) findViewById(R.id.tv_weather_number);
        tvWeatherDescribe = (TextView) findViewById(R.id.tv_weather_describe);
        queryFromServer("http://www.weather.com.cn/data/sk/" + 101020100 + ".html","weathercodedefault");
        queryFromServer("http://www.weather.com.cn/data/cityinfo/" + 101020100 + ".html","weathercodeinfo");
    }

    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address,"weatherCode");
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address,"weatherCode");
    }

    private void queryFromServer(final String address,final String type) {

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if("countyCode".equals(type)) {
                    if(!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if(array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if("weathercodedefault".equals(type)) {
                    LogUtil.i(TAG,"222");
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String weatherDefault = jsonObject.getString("weatherinfo");
                        cityWeatherDefault = gson.fromJson(weatherDefault,CityWeatherDefault.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Utility.handleWeatherResponse(WeatherActivity.this,response);
                    LogUtil.i(TAG,"333");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.i(TAG,"444");
                            showWeather();
                        }
                    });
                }else if("weathercodeinfo".equals(type)) {
                    LogUtil.i(TAG,"222");
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String weatherinfo = jsonObject.getString("weatherinfo");
                        cityWeatherInfo = gson.fromJson(weatherinfo,CityWeatherInfo.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Utility.handleWeatherResponse(WeatherActivity.this,response);
                    LogUtil.i(TAG,"333");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.i(TAG,"444");
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                LogUtil.i(TAG,"failed");
            }
        });
    }

    private void showWeather() {
        LogUtil.i(TAG,"111");
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        tvWeatherNumber.setText(cityWeatherDefault.getTemp() + "℃");
        tvWeatherDescribe.setText(cityWeatherInfo.getWeather());
    }

}
