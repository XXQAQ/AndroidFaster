package com.xq.projectdefine.base.basesimplerefreshload;

public interface RefreshLoadView {

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

        public void onFinishRefresh(RefreshLoadView view);

        public void onRefresh(RefreshLoadView view);

        public void onCancleRefresh(RefreshLoadView view);

        public void onFinishLoadmore(RefreshLoadView view);

        public void onLoadmore(RefreshLoadView view);

        public void onCancleLoadmore(RefreshLoadView view);

    }

}
