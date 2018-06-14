package com.xq.projectdefine.callback;


import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.GetListBehavior;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xq on 2017/4/12.
 */
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
                getRefreshLoadData().baseRefreshLoadview.showRefreshLoadEnd();
            }

            if (getRefreshLoadData().isRefresh)
            {
                getRefreshLoadData().list_data.clear();
                getRefreshLoadData().list_data.addAll(list);
            }
            else
            {
                getRefreshLoadData().list_data.addAll(list);
                if (list.size() > 0)
                {
                    getRefreshLoadData().page++;
                }
            }
        }
    }

    default void requestError(Object... objects) {
        if (getRefreshLoadData().baseRefreshLoadview != null)
        {
            getRefreshLoadData().baseRefreshLoadview.showRefreshLoadErro();
        }
    }

    @Override
    default void requestFinish(Object... objects) {
        if (getRefreshLoadData().baseRefreshLoadview != null)
        {
            if (getRefreshLoadData().isRefresh)
            {
                getRefreshLoadData().baseRefreshLoadview.afterRefresh();
            }
            else
            {
                getRefreshLoadData().baseRefreshLoadview.afterLoad(getRefreshLoadData().list_data.size());
            }
            if (getRefreshLoadData().list_data == null || getRefreshLoadData().list_data.size()<=0)
                getRefreshLoadData().baseRefreshLoadview.afterEmpty();
        }
    }

    public RefreshLoadData getRefreshLoadData();
    public static class RefreshLoadData {
        public IFasterBaseRefreshLoadView baseRefreshLoadview;
        public List list_data;
        public Integer page;
        public boolean isRefresh;
    }

}

