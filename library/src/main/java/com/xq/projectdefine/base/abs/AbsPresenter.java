package com.xq.projectdefine.base.abs;

import java.util.List;

public interface AbsPresenter<T extends AbsView> extends AbsCommon {

    //获取当前P层所有delegate
    public List<AbsPresenterDelegate> getDelegates();

    //获取当前P层对应View层
    public T getBindView();

}
