package com.xq.projectdefine.base.abs;


public interface IAbsPresenter<T extends IAbsView> extends IAbsCommon {

    //注入代理
    public void inject(AbsPresenterDelegate delegate);

    //获取当前P层对应View层
    public T getBindView();

}
