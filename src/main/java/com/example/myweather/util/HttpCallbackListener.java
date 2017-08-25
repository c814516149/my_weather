package com.example.myweather.util;

/**
 * Created by 10206932 on 2017/8/22.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
