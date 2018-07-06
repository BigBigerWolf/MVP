package com.example.rxretrofitdaggermvp.manager;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MrKong on 2017/4/1.
 */

public class AppManager {
    private List<Activity> mActivities = new ArrayList<>();

    @Inject
    public AppManager() {
    }

    public void addAcitivty(Activity activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    private void removeAllActivity() {

        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public void exitApp() {
        removeAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
