package com.xq.androidfaster;

import android.app.Application;

import com.xq.androidfaster.util.callback.UniverseCallback;
import com.xq.androidfaster.util.tools.AppUtils;
import java.util.LinkedList;
import java.util.List;

public class AndroidFaster {

    private static Application app;

    private static String fileProvider;

    public static void init(Application app,String fileProvider){

        AndroidFaster.app = app;
        AndroidFaster.fileProvider = fileProvider;

        AppUtils.registerActivityLifecycleCallbacks();
    }

    public static Application getApp() {
        return app;
    }

    public static String getFileProvider() {
        return fileProvider;
    }

}
