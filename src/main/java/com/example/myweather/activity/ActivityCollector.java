package com.example.myweather.activity;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by 10206932 on 2017/8/8.
 */
public class ActivityCollector {
    private static ArrayList<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for(Activity activity : activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
