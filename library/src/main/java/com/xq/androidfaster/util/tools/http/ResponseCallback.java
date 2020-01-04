package com.xq.androidfaster.util.tools.http;

import com.xq.androidfaster.util.HttpCallback;

public abstract class ResponseCallback implements HttpCallback<Response> {
    private CallbackBean bean = new CallbackBean();
    @Override
    public CallbackBean<Response> getCallbackBean() {
        return bean;
    }
}
