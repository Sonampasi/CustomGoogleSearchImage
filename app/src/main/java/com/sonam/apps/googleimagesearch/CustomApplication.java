package com.sonam.apps.googleimagesearch;

import android.app.Application;
import android.content.Context;

/**
 * Created by sonam on 2/12/2018.
 */

public class CustomApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        CustomApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CustomApplication.context;
    }
}
