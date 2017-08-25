package com.example.myweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 10206932 on 2017/8/23.
 */
public class Utility {
    public static void handleWeatherResponse (Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherObject = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherObject.getString("city");
            String weatherCode = weatherObject.getString("cityid");
            String temp = weatherObject.getString("temp");
            //String temp2 = weatherObject.getString("temp2");
            String windDirection = weatherObject.getString("WD");
            String windScale = weatherObject.getString("WS");
            String airHumidity = weatherObject.getString("SD");
            String weatherDesp = weatherObject.getString("weather");
            String publicTime = weatherObject.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp, windDirection, windScale, airHumidity, weatherDesp, publicTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context, String cityName, String weatherCode,
                                       String temp, String windDirection, String windScale,
                                       String airHumidity, String weatherDesp,String publicTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp", temp);
        editor.putString("wind_direction", windDirection);
        editor.putString("wind_scale", windScale);
        editor.putString("air_humidity", airHumidity);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publicTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();

    }

}
