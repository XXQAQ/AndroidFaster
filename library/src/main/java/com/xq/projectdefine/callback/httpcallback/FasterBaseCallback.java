package com.xq.projectdefine.callback.httpcallback;


public interface FasterBaseCallback<T> {

    default void requestStart(Object... objects) {

    }

    default void requestSuccess(T t, Object... objects) {

    }

    default void requestError(Object... objects) {

    }

    default void requestFinish(Object... objects) {

    }

}
