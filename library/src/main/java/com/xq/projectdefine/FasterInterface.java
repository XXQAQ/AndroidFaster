package com.xq.projectdefine;

import android.app.Application;

public class FasterInterface {

    private static Application app;

    private static String fileProvider;

    private static boolean isAutoPermission;

    public static void init(Application app,String fileProvider){
        init(app,fileProvider,true);
    }

    public static void init(Application app,String fileProvider,boolean isAutoPermission){

        FasterInterface.app = app;
        FasterInterface.fileProvider = fileProvider;
        FasterInterface.isAutoPermission = isAutoPermission;

    }

    public static Application getApp() {
        return app;
    }

    public static String getFileProvider() {
        return fileProvider;
    }

    public static boolean isIsAutoPermission() {
        return isAutoPermission;
    }
}
