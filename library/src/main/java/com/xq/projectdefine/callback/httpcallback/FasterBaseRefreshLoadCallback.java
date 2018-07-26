package com.xq.projectdefine.callback.httpcallback;


import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadPresenter;
import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.ListBehavior;

import java.util.LinkedList;
import java.util.List;


public interface FasterBaseRefreshLoadCallback<T> extends FasterBaseSimpleRefreshLoadCallback<T> {

    default void requestStart(Object... objects) {
        FasterBaseSimpleRefreshLoadCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        FasterBaseSimpleRefreshLoadCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        FasterBaseSimpleRefreshLoadCallback.super.requestError(objects);
    }

    @Override
    default void requestFinish(Object... objects) {
        FasterBaseSimpleRefreshLoadCallback.super.requestFinish(objects);
    }

    default void operateSuccess(T t){
        List list = null;
        if (t instanceof List)
            list = (List) t;
        else    if (t instanceof ListBehavior)
            list = ((ListBehavior)t).getList();
        else
            return;

        if (getCallbackBuilder().refreshLoadView != null)
        {
            if (list == null)
                list = new LinkedList();

            if (list.size() <= 0)
                getCallbackBuilder().refreshLoadView.afterRefreshLoadEnd();

            if (getCallbackBuilder().refreshLoadBuilder.isRefresh)
            {
                getCallbackBuilder().refreshLoadBuilder.list_data.clear();
                getCallbackBuilder().refreshLoadBuilder.list_data.addAll(list);
            }
            else
            {
                getCallbackBuilder().refreshLoadBuilder.list_data.addAll(list);
            }
        }
    }

    @Override
    default void afterRefresh() {
        getCallbackBuilder().refreshLoadView.afterRefresh();
    }

    @Override
    default void afterLoadmore() {
        getCallbackBuilder().refreshLoadView.afterLoadmore(getCallbackBuilder().refreshLoadBuilder.list_data.size());
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends FasterBaseSimpleRefreshLoadCallback.CallbackBuilder{
        public IFasterBaseRefreshLoadView refreshLoadView;
        public IFasterBaseRefreshLoadPresenter.RefreshLoadBuilder refreshLoadBuilder;
    }

}

