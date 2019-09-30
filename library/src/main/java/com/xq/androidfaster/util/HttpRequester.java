package com.xq.androidfaster.util;

import com.xq.androidfaster.util.callback.FasterHttpCallback;
import java.util.Map;

public class HttpRequester {

    private static Requester requester;

    public void get(String url, Map<Object, Object> map, FasterHttpCallback callback, Object... objects) {
        requester.get(url,map,callback,objects);
    }

    public void post(String url, Map<Object, Object> map, FasterHttpCallback callback, Object... objects) {
        requester.post(url,map,callback,objects);
    }

    public void postJson(String url, String json, FasterHttpCallback callback, Object... objects) {
        requester.postJson(url,json,callback,objects);
    }

    public static void setRequester(Requester requester) {
        HttpRequester.requester = requester;
    }

    public static abstract class Requester {

        public abstract void get(String url, Map<Object,Object> map, FasterHttpCallback callback,Object... objects);

        public abstract void post(String url, Map<Object,Object> map, FasterHttpCallback callback,Object... objects);

        public abstract void postJson(String url, String json, FasterHttpCallback callback,Object... objects);

    }

}
