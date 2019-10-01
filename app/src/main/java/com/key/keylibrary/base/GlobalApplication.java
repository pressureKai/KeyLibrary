package com.key.keylibrary.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import org.litepal.LitePalApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * created by key  on 2019/1/6
 */
public class GlobalApplication extends LitePalApplication {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
    private List<Activity> aList;

    @Override
    public void onCreate() {
        super.onCreate();
        aList = new ArrayList<>();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();
    }


    public static Context getContext() {
        return context;
    }
    public static Handler getHandler() {
        return handler;
    }
    public static int getMainThreadId() {
        return mainThreadId;
    }

    public void addActivity(Activity activity) {
        if (!aList.contains(activity)) {
            aList.add(activity);
        }
    }


    public void removeActivity(Activity activity) {
        if (aList.contains(activity)) {
            aList.remove(activity);
            activity.finish();
        }
    }


    public void removeAllActivity() {
        for (Activity activity : aList) {
            activity.finish();
        }
    }

}
