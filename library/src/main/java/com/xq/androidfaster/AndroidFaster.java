package com.xq.androidfaster;

import android.app.Application;
import com.xq.androidfaster.util.tools.Utils;

public class AndroidFaster {

    private static Application app;

    public static void init(Application app,String fileProvider){
        AndroidFaster.app = app;
        Utils.init(app,fileProvider);
    }

    public static Application getApp() {
        return app;
    }

}
