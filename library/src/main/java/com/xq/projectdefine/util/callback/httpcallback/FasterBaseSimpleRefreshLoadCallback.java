package com.xq.projectdefine.util.callback.httpcallback;


import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadPresenter;
import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadView;


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
    default void requestFinish(Object... objects) {
        FasterBaseCallback.super.requestFinish(objects);
        if (getCallbackBuilder().simpleRefreshLoadView != null)
        {
            if (getCallbackBuilder().simpleRefreshLoadBuilder.isRefresh)
            {
                if (!getCallbackBuilder().isEmpty)
                    getCallbackBuilder().simpleRefreshLoadBuilder.page = 1;
                afterRefresh();
            }
            else
            {
                if (!getCallbackBuilder().isEmpty)
                    getCallbackBuilder().simpleRefreshLoadBuilder.page++;
                afterLoadmore();
            }

            if (getCallbackBuilder().isEmpty)
                getCallbackBuilder().simpleRefreshLoadView.afterEmpty();
        }
    }

    default void afterRefresh(){
        getCallbackBuilder().simpleRefreshLoadView.afterRefresh();
    }

    default void afterLoadmore(){
        getCallbackBuilder().simpleRefreshLoadView.afterLoadmore();
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends FasterBaseCallback.CallbackBuilder{
        public IFasterBaseSimpleRefreshLoadView simpleRefreshLoadView;
        public IFasterBaseSimpleRefreshLoadPresenter.RefreshLoadBuilder simpleRefreshLoadBuilder;
    }

}

