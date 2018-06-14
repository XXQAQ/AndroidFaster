package com.xq.projectdefine;

import android.app.Application;

public class FasterInterface {

    private static Application app;

    private static String FILEPROVIDER;

    public static void init(Application app,String FILEPROVIDER){

        FasterInterface.app = app;
        FasterInterface.FILEPROVIDER = FILEPROVIDER;

    }

    public static Application getApp() {
        return app;
    }

    public static String getFILEPROVIDER() {
        return FILEPROVIDER;
    }

}
