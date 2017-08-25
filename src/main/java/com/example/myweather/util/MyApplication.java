package com.example.myweather.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by 10206932 on 2017/8/22.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
