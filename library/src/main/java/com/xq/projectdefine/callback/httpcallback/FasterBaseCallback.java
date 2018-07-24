package com.xq.projectdefine.callback.httpcallback;


public interface FasterBaseCallback<T> {


    default void requestStart(Object... objects) {

    }

    default void requestError(Object... objects) {

    }

    default void requestSuccess(T t, Object... objects) {
        if (operating(t,objects))
            operateSuccess(t);
    }

    default void requestFinish(T t,Object... objects) {

    }

    public boolean operating(T t, Object... objects);

    public void operateSuccess(T t);

}
