package com.xq.androidfaster;

import android.annotation.SuppressLint;
import android.app.Application;
import com.xq.androidfaster.util.tools.Utils;
import java.lang.reflect.InvocationTargetException;

public class AndroidFaster {

    private static Application app;

    private static String fileProvider;

    public static void init(Application app,String fileProvider){

        AndroidFaster.app = app;
        AndroidFaster.fileProvider = fileProvider;

        Utils.init(app);
    }

    public static Application getApp() {
        if (app == null) return getApplicationByReflect();
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    public static String getFileProvider() {
        return fileProvider;
    }

}
