package com.xq.androidfaster.util;

import java.util.Map;

public class HttpRequester {

    private static Requester requester;

    public static void get(String url, Map<Object, Object> map, Callback callback, Object... objects) {
        requester.get(url,map,callback,objects);
    }

    public static void post(String url, Map<Object, Object> map, Callback callback, Object... objects) {
        requester.post(url,map,callback,objects);
    }

    public static void postJson(String url, String json, Callback callback, Object... objects) {
        requester.postJson(url,json,callback,objects);
    }

    public static void setRequester(Requester requester) throws RuntimeException {
        if (HttpRequester.requester != null)
            throw new RuntimeException("Can't set Requester again");
        HttpRequester.requester = requester;
    }

    public static abstract class Callback<T>{

        private Class<T> dataClass;

        public Callback(Class<T> dataClass) {
            this.dataClass = dataClass;
        }

        public Class<T> getDataClass() {
            return dataClass;
        }

        public abstract void onCallback(boolean isSuccess,T data);
    }

    public static abstract class Requester {

        public abstract void get(String url, Map<Object,Object> map, Callback callback,Object... objects);

        public abstract void post(String url, Map<Object,Object> map, Callback callback,Object... objects);

        public abstract void postJson(String url, String json, Callback callback,Object... objects);

    }

}
