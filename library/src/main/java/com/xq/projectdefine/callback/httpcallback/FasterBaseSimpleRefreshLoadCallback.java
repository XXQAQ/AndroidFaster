package com.xq.projectdefine.callback.httpcallback;


import android.text.TextUtils;

import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadPresenter;
import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadView;

import java.util.List;


public interface FasterBaseSimpleRefreshLoadCallback<T> extends FasterBaseCallback<T> {

    default void requestStart(Object... objects) {
        FasterBaseCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        FasterBaseCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        FasterBaseCallback.super.requestError(objects);
        if (getCallbackBuilder().simpleRefreshLoadView != null)
            getCallbackBuilder().simpleRefreshLoadView.afterRefreshLoadErro();
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        FasterBaseCallback.super.requestFinish(t,objects);
        if (getCallbackBuilder().simpleRefreshLoadView != null)
        {
            if (getCallbackBuilder().simpleRefreshLoadBuilder.isRefresh)
                afterRefresh(t);
            else
                afterLoadmore(t);

            if (isEmpty(t))
                getCallbackBuilder().simpleRefreshLoadView.afterEmpty();
        }
    }

    default void operateSuccess(T t){
        if (getCallbackBuilder().simpleRefreshLoadView != null)
        {
            if (!isEmpty(t))
                getCallbackBuilder().simpleRefreshLoadBuilder.page++;
        }
    }

    default void afterRefresh(T t){
        getCallbackBuilder().simpleRefreshLoadView.afterRefresh(t);
    }

    default void afterLoadmore(T t){
        getCallbackBuilder().simpleRefreshLoadView.afterLoadmore(t);
    }

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

    public static class CallbackBuilder {
        public IFasterBaseSimpleRefreshLoadView simpleRefreshLoadView;
        public IFasterBaseSimpleRefreshLoadPresenter.RefreshLoadBuilder simpleRefreshLoadBuilder;
    }

}

