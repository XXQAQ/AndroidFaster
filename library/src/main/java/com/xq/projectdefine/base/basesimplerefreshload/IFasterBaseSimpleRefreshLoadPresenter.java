package com.xq.projectdefine.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;
import com.xq.projectdefine.base.abs.AbsPresenter;


public interface IFasterBaseSimpleRefreshLoadPresenter<T extends IFasterBaseSimpleRefreshLoadView> extends AbsPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

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
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //开始刷新
    default void refresh(Object... objects) {
        getRefreshLoadBuilder().page = 1;
        getRefreshLoadBuilder().isRefresh = true;
        refreshLoad(true, getRefreshLoadBuilder().page,objects);
    }

    //开始加载
    default void loadmore(Object... objects) {
        getRefreshLoadBuilder().isRefresh = false;
        refreshLoad(false, getRefreshLoadBuilder().page+1,objects);
    }

    //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
    public abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    //取消刷新
    public void cancleRefresh();

    //取消加载
    public void cancleLoadmore();

    //在您的P层定义RefreshLoadBuilder成员变量，并重写本方法返回该变量
    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder {
        public int page = 1;
        public boolean isRefresh;
    }
}
