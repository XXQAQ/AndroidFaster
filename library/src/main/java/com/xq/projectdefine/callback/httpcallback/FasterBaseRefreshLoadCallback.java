package com.xq.projectdefine.callback.httpcallback;


import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadPresenter;
import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.ListBehavior;

import java.util.LinkedList;
import java.util.List;


public interface FasterBaseRefreshLoadCallback<T> extends FasterBaseCallback<T> {

    default void requestSuccess(T t, Object... objects) {

        List list = null;

        if (t instanceof List)
            list = (List) t;
        else    if (t instanceof ListBehavior)
            list = ((ListBehavior)t).getList();
        else
            return;

        if (getCallbackBuilder().baseRefreshLoadview != null)
        {
            if (list == null)
                list = new LinkedList();

            if (list.size() <1)
                getCallbackBuilder().baseRefreshLoadview.afterRefreshLoadEnd();

            if (getCallbackBuilder().refreshLoadBuilder.isRefresh)
            {
                getCallbackBuilder().refreshLoadBuilder.list_data.clear();
                getCallbackBuilder().refreshLoadBuilder.list_data.addAll(list);
            }
            else
            {
                getCallbackBuilder().refreshLoadBuilder.list_data.addAll(list);
                if (list.size() > 0)
                    getCallbackBuilder().refreshLoadBuilder.page++;
            }
        }
    }

    default void requestError(Object... objects) {
        if (getCallbackBuilder().baseRefreshLoadview != null)
            getCallbackBuilder().baseRefreshLoadview.afterRefreshLoadErro();
    }

    @Override
    default void requestFinish(Object... objects) {
        if (getCallbackBuilder().baseRefreshLoadview != null)
        {
            if (getCallbackBuilder().refreshLoadBuilder.isRefresh)
                getCallbackBuilder().baseRefreshLoadview.afterRefresh();
            else
                getCallbackBuilder().baseRefreshLoadview.afterLoadmore(getCallbackBuilder().refreshLoadBuilder.list_data.size());

            if (getCallbackBuilder().refreshLoadBuilder.list_data == null || getCallbackBuilder().refreshLoadBuilder.list_data.size()<=0)
                getCallbackBuilder().baseRefreshLoadview.afterEmpty();
        }
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder {
        public IFasterBaseRefreshLoadView baseRefreshLoadview;
        public IFasterBaseRefreshLoadPresenter.RefreshLoadBuilder refreshLoadBuilder;
    }

}

