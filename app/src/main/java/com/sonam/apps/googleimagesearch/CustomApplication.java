package com.sonam.apps.googleimagesearch;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by sonam on 2/12/2018.
 */

public class CustomApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        CustomApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CustomApplication.context;
    }
}
