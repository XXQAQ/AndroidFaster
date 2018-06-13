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

        if (getDataBean().baseRefreshLoadview != null)
        {
            if (list == null)
            {
                list = new LinkedList();
            }

            if (list.size() <1)
            {
                getDataBean().baseRefreshLoadview.showRefreshLoadEnd();
            }

            if (getDataBean().isRefresh)
            {
                getDataBean().list_data.clear();
                getDataBean().list_data.addAll(list);
            }
            else
            {
                getDataBean().list_data.addAll(list);
                if (list.size() > 0)
                {
                    getDataBean().page++;
                }
            }
        }
    }

    default void requestError(Object... objects) {
        if (getDataBean().baseRefreshLoadview != null)
        {
            getDataBean().baseRefreshLoadview.showRefreshLoadErro();
        }
    }

    @Override
    default void requestFinish(Object... objects) {
        if (getDataBean().baseRefreshLoadview != null)
        {
            if (getDataBean().isRefresh)
            {
                getDataBean().baseRefreshLoadview.afterRefresh();
            }
            else
            {
                getDataBean().baseRefreshLoadview.afterLoad(getDataBean().list_data.size());
            }
            if (getDataBean().list_data == null || getDataBean().list_data.size()<=0)
                getDataBean().baseRefreshLoadview.afterEmpty();
        }
    }

    public DataBean getDataBean();
    public static class DataBean{
        public IFasterBaseRefreshLoadView baseRefreshLoadview;
        public List list_data;
        public Integer page;
        public boolean isRefresh;
    }

}

