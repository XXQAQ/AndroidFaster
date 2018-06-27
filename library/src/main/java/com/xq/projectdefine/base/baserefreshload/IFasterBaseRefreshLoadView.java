package com.xq.projectdefine.base.baserefreshload;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.xq.projectdefine.base.basesimplerefreshload.IFasterSimpleBaseRefreshLoadView;

import java.util.List;

public interface IFasterBaseRefreshLoadView<T extends IFasterBaseRefreshLoadPresenter> extends IFasterSimpleBaseRefreshLoadView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterSimpleBaseRefreshLoadView.super.afterOnCreate(savedInstanceState);

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
        IFasterSimpleBaseRefreshLoadView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterSimpleBaseRefreshLoadView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterSimpleBaseRefreshLoadView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterSimpleBaseRefreshLoadView.super.onSaveInstanceState(outState);
    }

    //刷新完成后调用
    default void afterRefresh() {
        IFasterSimpleBaseRefreshLoadView.super.afterRefresh();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    //加载完成后调用
    default void afterLoad() {
        IFasterSimpleBaseRefreshLoadView.super.afterLoad();
        getRefreshLoadBuilder().rv.getAdapter().notifyDataSetChanged();
    }

    //集合类型数据加载完成后调用
    default void afterLoad(int position) {
        IFasterSimpleBaseRefreshLoadView.super.afterLoad();
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
    public int getHeadViewCount();

    //返回尾布局数量，防止adapter item总数异常
    public int getFoodViewCount();

    //在您的View定义RefreshLoadBuilder成员变量，并重写本方法返回该变量
    public RefreshLoadBuilder getRefreshLoadBuilder();

    @Override
    default SimpleRefreshLoadBuilder getSimpleRefreshLoadBuilder() {
        return getRefreshLoadBuilder();
    }

    public static class RefreshLoadBuilder extends SimpleRefreshLoadBuilder{
        public RecyclerView rv;
    }

}
