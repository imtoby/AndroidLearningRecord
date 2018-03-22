package com.syberos.learnandroid;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaoDongshuang on 17-12-27.
 */

public class ActivitiesList {
    private static List<Activity> activitiesList = new ArrayList<>();

    public static void add(Activity activity) {
        activitiesList.add(activity);
    }

    public static void remove(Activity activity) {
        activitiesList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activitiesList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
