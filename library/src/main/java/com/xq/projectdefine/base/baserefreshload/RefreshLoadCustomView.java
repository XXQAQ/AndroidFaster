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

        public void onFinishRefresh(RefreshLoadCustomView view);

        public void onRefresh(RefreshLoadCustomView view);

        public void onCancleRefresh(RefreshLoadCustomView view);

        public void onFinishLoadmore(RefreshLoadCustomView view);

        public void onLoadmore(RefreshLoadCustomView view);

        public void onCancleLoadmore(RefreshLoadCustomView view);

    }

}
