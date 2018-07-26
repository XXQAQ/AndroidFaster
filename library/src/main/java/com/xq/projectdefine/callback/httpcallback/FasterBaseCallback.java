package com.xq.projectdefine.callback.httpcallback;


public interface FasterBaseCallback<T> {


    default void requestStart(Object... objects) {

    }

    default void requestError(Object... objects) {

    }

    default void requestFinish(Object... objects) {

    }

    default void requestSuccess(T t, Object... objects) {
        if (operating(t,objects))
        {
            getCallbackBuilder().isOperateSuccess = true;
            operateSuccess(t);
        }
    }

    public boolean operating(T t, Object... objects);

    public void operateSuccess(T t);

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder{
        public boolean isOperateSuccess;
    }

}
