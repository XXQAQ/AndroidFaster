package com.xq.projectdefine.base.baserefreshload;


import com.xq.projectdefine.base.abs.AbsPresenter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IFasterBaseRefreshLoadPresenter<T extends IFasterBaseRefreshLoadView> extends AbsPresenter<T> {

    default void refresh(Object... objects) {
        getRefreshLoadBuilder().page = 1;
        refreshLoad(true, getRefreshLoadBuilder().page,objects);
    }

    default void loadMore(Object... objects) {
        refreshLoad(false, getRefreshLoadBuilder().page+1,objects);
    }

    public void cancleRefresh();

    public void cancleLoadmore();

    default void initAdapter(){
        getBindView().initAdapter(getRefreshLoadBuilder().list_data);
    }

    //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载功能的方法
    public abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder{
        public List list_data = new LinkedList<>();
        public Integer page = 1;
    }
}
