package com.xq.projectdefine.base.baserefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.basesimplerefreshload.IFasterSimpleBaseRefreshLoadPresenter;
import com.xq.projectdefine.base.basesimplerefreshload.IFasterSimpleBaseRefreshLoadView;

import java.util.LinkedList;
import java.util.List;


public interface IFasterBaseRefreshLoadPresenter<T extends IFasterBaseRefreshLoadView> extends IFasterSimpleBaseRefreshLoadPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterSimpleBaseRefreshLoadPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterSimpleBaseRefreshLoadPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    default void initAdapter(){
        getBindView().initAdapter(getRefreshLoadBuilder().list_data);
    }

    //在您的P层定义RefreshLoadBuilder成员变量，并重写本方法返回该变量
    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder extends IFasterSimpleBaseRefreshLoadPresenter.RefreshLoadBuilder{
        public List list_data = new LinkedList<>();
    }

}
