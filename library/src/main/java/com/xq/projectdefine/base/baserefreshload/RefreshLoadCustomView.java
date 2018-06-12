package com.xq.projectdefine.base.baserefreshload;

import android.view.View;

public interface RefreshLoadCustomView {

    public void startLoadmore();

    public void startRefresh();

    public void finishLoadmore();

    public void finishRefreshing();

    public void setEmptyView(View view);

    public void setHeaderView(View view);

    public void setFootView(View view);

    public void setRefreshLoadListener(OnRefreshLoadListener listeneri);

    public static interface OnRefreshLoadListener {

        public void afterRefresh(RefreshLoadCustomView view);

        public void afterLoadmore(RefreshLoadCustomView view);
    }

}
