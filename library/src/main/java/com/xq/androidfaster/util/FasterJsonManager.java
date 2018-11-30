package com.xq.androidfaster.util;

public class FasterJsonManager {

    private static Manager manager;

    protected static <T> T jsonToObject(String json, Class<T> mClass, Object... objects) {
        return manager.jsonToObject(json,mClass,objects);
    }

    protected static String objectToJson(Object object, Object... objects) {
        return manager.objectToJson(object,objects);
    }

    protected static abstract class Manager {

        public abstract <T>T jsonToObject(String json,Class<T> mClass,Object... objects);

        public abstract String objectToJson(Object object,Object... objects);

    }

}
