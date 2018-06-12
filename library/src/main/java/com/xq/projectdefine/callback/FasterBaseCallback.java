package com.xq.projectdefine.callback;

/**
 * Created by xq on 2017/4/7 0007.
 */

public interface FasterBaseCallback<T> {


    default void requestStart(Object... objects) {

    }

    default void requestCacheSuccess(T t, Object... objects) {

    }

    default void requestSuccess(T t, Object... objects) {

    }

    default void requestError(Object... objects) {

    }

    default void requestFinish(Object... objects) {

    }

    default T convertJson(String data) throws Throwable {

        return null;
    }
}
