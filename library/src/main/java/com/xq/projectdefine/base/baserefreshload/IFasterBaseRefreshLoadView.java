package com.xq.projectdefine.base.baserefreshload;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadView;

import java.util.List;


public interface IFasterBaseRefreshLoadView<T extends IFasterBaseRefreshLoadPresenter> extends IFasterBaseSimpleRefreshLoadView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseSimpleRefreshLoadView.super.afterOnCreate(savedInstanceState);

        if (getRootView() instanceof RecyclerView)   //如果根布局是RecyclerView，则直接将根布局转化为rv
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView();
        else
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView().findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));

        //初始化RecyclerView
        getRefreshLoadBuilder().rv.setLayoutManager(getLayoutManager());

        //通知P层初始化Adapter
        getPresenter().initAdapter();
    }

    @Override
    default void onResume() {
        IFasterBaseSimpleRefreshLoadView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseSimpleRefreshLoadView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseSimpleRefreshLoadView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterBaseSimpleRefreshLoadView.super.onSaveInstanceState(outState);
    }

    //刷新完成后调用
    default void afterRefresh() {
        IFasterBaseSimpleRefreshLoadView.super.afterRefresh();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    //加载完成后调用
    default void afterLoadmore() {
        IFasterBaseSimpleRefreshLoadView.super.afterLoadmore();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    //集合类型数据加载完成后调用
    default void afterLoadmore(int position) {
        IFasterBaseSimpleRefreshLoadView.super.afterLoadmore();
        getRefreshLoadBuilder().rv.getAdapter().notifyItemRangeChanged(0,position);
    }

    //初始化适配器，主要写给P层调用
    default void initAdapter(List list, Object... objects) {
        getRefreshLoadBuilder().rv.setAdapter(getAdapter(list,objects));
    }

    //返回适配器，可以选择重写该方法，为Adater设置更多参数
    public RecyclerView.Adapter getAdapter(List list, Object... objects);

    //返回布局管理器，可以选择重写该方法以指定RecyclerView的布局方案
    public RecyclerView.LayoutManager getLayoutManager();

    //返回头布局数量，防止adapter item总数异常
    public int getAdapterHeadCount();

    //返回尾布局数量，防止adapter item总数异常
    public int getAdapterFootCount();

    //在您的View定义RefreshLoadBuilder成员变量，并重写本方法返回该变量
    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder extends IFasterBaseSimpleRefreshLoadView.RefreshLoadBuilder{
        public RecyclerView rv;
    }

}
