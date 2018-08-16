package com.xq.projectdefine.util.callback.httpcallback;


import android.text.TextUtils;

import java.util.List;

public interface AbsCallback<T> {

    //请求开始
    default void requestStart(Object... objects) {

    }

    //请求错误
    default void requestError(Object... objects) {

    }

    //请求成功
    default void requestSuccess(T t, Object... objects) {
        if (operating(t,objects))
        {
            getCallbackBuilder().isOperateSuccess = true;
            operateSuccess(t);
        }
    }

    //请求结束
    default void requestFinish(T t, Object... objects) {

    }

    //判断业务操作是否成功
    public boolean operating(T t, Object... objects);

    //业务操作成功之后调用的方法
    public void operateSuccess(T t);

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder{
        //下变量在requestSuccess方法完成后才具备意义
        public boolean isOperateSuccess = false;
    }

}
