package com.xq.projectdefine;

import android.app.Application;

public class FasterInterface {

    private static Application app;

    private static String FILEPROVIDER;

    private static boolean isAutoPermission;



    public static void init(Application app,String FILEPROVIDER){
        init(app,FILEPROVIDER,true);
    }

    public static void init(Application app,String FILEPROVIDER,boolean isAutoPermission){

        FasterInterface.app = app;
        FasterInterface.FILEPROVIDER = FILEPROVIDER;
        FasterInterface.isAutoPermission = isAutoPermission;

    }



    public static Application getApp() {
        return app;
    }

    public static String getFileProvider() {
        return FILEPROVIDER;
    }

    public static boolean isIsAutoPermission() {
        return isAutoPermission;
    }
}
