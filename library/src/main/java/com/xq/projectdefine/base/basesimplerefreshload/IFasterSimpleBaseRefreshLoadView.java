package com.xq.projectdefine.base.basesimplerefreshload;


import android.os.Bundle;
import com.xq.projectdefine.base.abs.AbsView;

public interface IFasterSimpleBaseRefreshLoadView<T extends IFasterSimpleBaseRefreshLoadPresenter> extends AbsView<T> {


    default void afterOnCreate(Bundle savedInstanceState) {

        if (getRootView() instanceof RefreshLoadCustomView)
            getSimpleRefreshLoadBuilder().refreshView = (RefreshLoadCustomView) getRootView();
        else
            getSimpleRefreshLoadBuilder().refreshView = (RefreshLoadCustomView) findViewById(getContext().getResources().getIdentifier("refreshView", "id", getContext().getPackageName()));

        //以下初始化刷新控件
        if (getSimpleRefreshLoadBuilder().refreshView != null)
        {
            getSimpleRefreshLoadBuilder().refreshView.setRefreshLoadListener(new RefreshLoadCustomView.OnRefreshLoadListener() {
                @Override
                public void onFinishRefresh(RefreshLoadCustomView view) {

                }

                @Override
                public void onRefresh(RefreshLoadCustomView view) {
                    refreshPresenter();
                }

                @Override
                public void onCancleRefresh(RefreshLoadCustomView view) {
                    getPresenter().cancleRefresh();
                }

                @Override
                public void onFinishLoadmore(RefreshLoadCustomView view) {

                }

                @Override
                public void onLoadmore(RefreshLoadCustomView view) {
                    loadMorePresenter();
                }

                @Override
                public void onCancleLoadmore(RefreshLoadCustomView view) {
                    getPresenter().cancleLoadmore();
                }
            });
            getSimpleRefreshLoadBuilder().refreshView.setHeaderView(getHeadView());
            getSimpleRefreshLoadBuilder().refreshView.setFootView(getFootView());
        }
    }

    @Override
    default void onResume() {

    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onSaveInstanceState(Bundle outState) {

    }

    //开始刷新，主要写给P层调用
    default void startRefresh(){
        getSimpleRefreshLoadBuilder().refreshView.startRefresh();
    }

    //开始加载，主要写给P层调用
    default void startLoadmore(){
        getSimpleRefreshLoadBuilder().refreshView.startLoadmore();
    }

    //通知P层刷新，可以选择重写该方法，在刷新时传入更多参数
    default void refreshPresenter() {
        getPresenter().refresh();
    }

    //通知P层加载，可以选择重写该方法，在加载时传入更多参数
    default void loadMorePresenter() {
        getPresenter().loadMore();
    }

    //刷新完成后调用
    default void afterRefresh() {
        if (getSimpleRefreshLoadBuilder().refreshView != null)
            getSimpleRefreshLoadBuilder().refreshView.finishRefreshing();
    }

    //加载完成后调用
    default void afterLoad() {
        if (getSimpleRefreshLoadBuilder().refreshView != null)
            getSimpleRefreshLoadBuilder().refreshView.finishLoadmore();
    }

    //返回刷新加载的空布局方案
    public Object getEmptyView();

    //返回头布局
    public Object getHeadView();

    //返回尾布局
    public Object getFootView();

    //数据加载为空后处理
    public void afterEmpty();

    //刷新完成后处理
    public void showRefreshLoadEnd();

    //加载完成后处理
    public void showRefreshLoadErro();

    //在您的View定义SimpleRefreshLoadBuilder成员变量，并重写本方法返回该变量
    public SimpleRefreshLoadBuilder getSimpleRefreshLoadBuilder();

    public static class SimpleRefreshLoadBuilder {
        public RefreshLoadCustomView refreshView;
    }

}
