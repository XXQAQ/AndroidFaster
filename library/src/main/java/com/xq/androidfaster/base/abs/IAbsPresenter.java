package com.xq.androidfaster.base.abs;


public interface IAbsPresenter<T extends IAbsView> extends IAbsCommon {

    //注入代理
    public void inject(AbsPresenterDelegate delegate);

    //获取当前P层对应View层
    public T getBindView();

    //指定Activity Class 跳转页面
    public void startActivity(Class mClass);

}
