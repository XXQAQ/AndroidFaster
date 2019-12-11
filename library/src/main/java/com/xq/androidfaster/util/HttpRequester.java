package com.xq.androidfaster.util;

import java.util.Map;

public class HttpRequester {

    private static Requester requester;

    public static void get(String url, Map<String, Object> map, HttpCallback callback) {
        requester.get(url,map,callback);
    }

    public static void post(String url, Map<String, Object> map, HttpCallback callback) {
        requester.post(url,map,callback);
    }

    public static void postJson(String url, String json, HttpCallback callback) {
        requester.postJson(url,json,callback);
    }

    public static void setRequester(Requester requester) throws RuntimeException {
        HttpRequester.requester = requester;
    }

    public static abstract class Requester {

        public abstract void get(String url, Map<String,Object> map, HttpCallback callback);

        public abstract void post(String url, Map<String,Object> map, HttpCallback callback);

        public abstract void postJson(String url, String json, HttpCallback callback);

    }

}
