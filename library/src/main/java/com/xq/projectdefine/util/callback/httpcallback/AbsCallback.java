package com.xq.projectdefine.util.callback.httpcallback;


import android.text.TextUtils;

import java.util.List;

public interface AbsCallback<T> {

    default void requestStart(Object... objects) {

    }

    default void requestError(Object... objects) {

    }

    default void requestSuccess(T t, Object... objects) {
        if (operating(t,objects))
        {
            getCallbackBuilder().isOperateSuccess = true;
            operateSuccess(t);
        }
    }
    default void requestFinish(T t, Object... objects) {

    }
    public boolean operating(T t, Object... objects);

    public void operateSuccess(T t);

    default boolean isEmpty(Object object){
        if (object  == null)
            return true;

        if (object instanceof List && ((List)object).size() <= 0)
            return true;

        if (object instanceof CharSequence && TextUtils.isEmpty((CharSequence) object))
            return true;

        return false;
    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder{
        //下变量在requestSuccess方法完成后才具备意义
        public boolean isOperateSuccess = false;
    }

}
