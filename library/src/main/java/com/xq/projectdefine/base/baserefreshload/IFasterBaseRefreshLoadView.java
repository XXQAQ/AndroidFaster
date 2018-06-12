package com.xq.projectdefine.base.baserefreshload;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xq.projectdefine.base.abs.AbsView;

import java.util.List;

public interface IFasterBaseRefreshLoadView<T extends IFasterBaseRefreshLoadPresenter> extends AbsView<T> {


    default void afterOnCreate(Bundle savedInstanceState) {

        if (getRootView() instanceof RefreshLoadCustomView)
            getRefreshLoadBuilder().refreshView = (RefreshLoadCustomView) getRootView();
        else
            getRefreshLoadBuilder().refreshView = (RefreshLoadCustomView) findViewById(getContext().getResources().getIdentifier("refreshView", "id", getContext().getPackageName()));

        if (getRootView() instanceof RecyclerView)   //如果根布局是RecyclerView，则直接将根布局转化为rv
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView();
        else
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView().findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));

        //以下初始化twi
        if (getRefreshLoadBuilder().refreshView != null)
        {
            getRefreshLoadBuilder().refreshView.setRefreshLoadListener(new RefreshLoadCustomView.OnRefreshLoadListener() {
                @Override
                public void afterRefresh(RefreshLoadCustomView view) {
                    refreshPresenter();
                }

                @Override
                public void afterLoadmore(RefreshLoadCustomView view) {
                    loadMorePresenter();
                }
            });
            getRefreshLoadBuilder().refreshView.setHeaderView(getHeadView());
            getRefreshLoadBuilder().refreshView.setFootView(getFootView());
        }

        //以下初始化RecyclerView
        getRefreshLoadBuilder().rv.setLayoutManager(getLayoutManager());

        getPresenter().initAdapter();
    }

    default void refreshPresenter() {
        getPresenter().refresh();
    }

    default void loadMorePresenter() {
        getPresenter().loadMore();
    }

    default void startRefresh(){
        getRefreshLoadBuilder().refreshView.startRefresh();
    }

    default void startLoadmore(){
        getRefreshLoadBuilder().refreshView.startLoadmore();
    }

    default void initAdapter(List list, Object... objects) {
        getRefreshLoadBuilder().rv.setAdapter(getAdapter(list,objects));
    }

    default void afterRefresh() {
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.finishRefreshing();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    default void afterLoad() {
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.finishLoadmore();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    default void afterLoad(int position) {
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.finishLoadmore();
        getRefreshLoadBuilder().rv.getAdapter().notifyItemRangeChanged(0,position);
    }

    public RecyclerView.Adapter getAdapter(List list, Object... objects);

    //重写该方法以指定RecyclerView的布局方案
    public RecyclerView.LayoutManager getLayoutManager();

    //根据您的需要重写该方法
    public View getEmptyView();

    public View getHeadView();

    public View getFootView();

    public void afterEmpty();

    public void showRefreshLoadEnd();

    public void showRefreshLoadErro();

    public int getHeadViewCount();

    public int getFoodViewCount();

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder{
        //刷新控件，在布局文件中对应的id为twi（可不需要）
        public RefreshLoadCustomView refreshView;
        //在布局文件中对应的id为rv，你可使用任意RecycleView或其子类，本项目中推荐使用FamiliarRecyclerView，对其有额外加成。
        //有一点需要注意：rv允许在Fragment中作为根布局使用，但不允许在Activity中作为根布局
        public RecyclerView rv;
}

}
