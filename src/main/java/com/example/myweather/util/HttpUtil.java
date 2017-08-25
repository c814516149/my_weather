package com.example.myweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 10206932 on 2017/8/22.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoInput(true);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader  = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder reponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        reponse.append(line);
                    }
                    if(listener != null) {
                        listener.onFinish(reponse.toString());
                    }
                } catch (MalformedURLException e) {
                    LogUtil.i("csc","1");
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    LogUtil.i("csc","2");
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    LogUtil.i("csc","3");
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
}
