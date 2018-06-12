package com.xq.projectdefine.base.baserefreshload;

import android.view.View;

public interface RefreshLoadCustomView {

    public void startLoadmore();

    public void startRefresh();

    public void finishLoadmore();

    public void finishRefreshing();

    public void setEmptyView(Object object);

    public void setHeaderView(Object object);

    public void setFootView(Object object);

    public void setRefreshLoadListener(OnRefreshLoadListener listeneri);

    public static interface OnRefreshLoadListener {

        public void startRefresh(RefreshLoadCustomView view);

        public void finishRefresh(RefreshLoadCustomView view);

        public void onRefresh(RefreshLoadCustomView view);

        public void startLoadmore(RefreshLoadCustomView view);

        public void finishLoadmore(RefreshLoadCustomView view);

        public void onLoadmore(RefreshLoadCustomView view);
    }

}
