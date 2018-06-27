package com.xq.projectdefine.base.basesimplerefreshload;

public interface RefreshLoadCustomView {

    //开始加载
    public void startLoadmore();

    //开始刷新
    public void startRefresh();

    //结束加载
    public void finishLoadmore();

    //结束刷新
    public void finishRefreshing();

    //设置空布局
    public void setEmptyView(Object object);

    //设置头布局
    public void setHeaderView(Object object);

    //设置尾布局
    public void setFootView(Object object);

    //设置状态监听
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
