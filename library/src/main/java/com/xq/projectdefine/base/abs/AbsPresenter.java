package com.xq.projectdefine.base.abs;


public interface AbsPresenter<T extends AbsView> extends AbsCommon {

    //注入代理
    public void inject(AbsPresenterDelegate delegate);

    //获取当前P层对应View层
    public T getBindView();

}
