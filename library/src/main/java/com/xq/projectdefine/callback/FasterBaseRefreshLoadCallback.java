package com.xq.projectdefine.callback;


import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadPresenter;
import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.GetListBehavior;

import java.util.LinkedList;
import java.util.List;


public interface FasterBaseRefreshLoadCallback<T> extends FasterBaseCallback<T> {

    default void requestSuccess(T t, Object... objects) {

        List list = null;

        if (t instanceof List)
        {
            list = (List) t;
        }
        else    if (t instanceof GetListBehavior)
        {
            list = ((GetListBehavior)t).getList();
        }
        else
            return;

        if (getRefreshLoadData().baseRefreshLoadview != null)
        {
            if (list == null)
            {
                list = new LinkedList();
            }

            if (list.size() <1)
            {
                getRefreshLoadData().baseRefreshLoadview.afterRefreshLoadEnd();
            }

            if (getRefreshLoadData().refreshLoadBuilder.isRefresh)
            {
                getRefreshLoadData().refreshLoadBuilder.list_data.clear();
                getRefreshLoadData().refreshLoadBuilder.list_data.addAll(list);
            }
            else
            {
                getRefreshLoadData().refreshLoadBuilder.list_data.addAll(list);
                if (list.size() > 0)
                {
                    getRefreshLoadData().refreshLoadBuilder.page++;
                }
            }
        }
    }

    default void requestError(Object... objects) {
        if (getRefreshLoadData().baseRefreshLoadview != null)
        {
            getRefreshLoadData().baseRefreshLoadview.afterRefreshLoadErro();
        }
    }

    @Override
    default void requestFinish(Object... objects) {
        if (getRefreshLoadData().baseRefreshLoadview != null)
        {
            if (getRefreshLoadData().refreshLoadBuilder.isRefresh)
            {
                getRefreshLoadData().baseRefreshLoadview.afterRefresh();
            }
            else
            {
                getRefreshLoadData().baseRefreshLoadview.afterLoad(getRefreshLoadData().refreshLoadBuilder.list_data.size());
            }
            if (getRefreshLoadData().refreshLoadBuilder.list_data == null || getRefreshLoadData().refreshLoadBuilder.list_data.size()<=0)
                getRefreshLoadData().baseRefreshLoadview.afterEmpty();
        }
    }

    public RefreshLoadData getRefreshLoadData();

    public static class RefreshLoadData {
        public IFasterBaseRefreshLoadView baseRefreshLoadview;
        public IFasterBaseRefreshLoadPresenter.RefreshLoadBuilder refreshLoadBuilder;
    }

}

