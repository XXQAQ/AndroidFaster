package com.xq.androidfaster.base.abs;

import android.content.Intent;

public interface IAbsPresenter<T extends IAbsView> extends IAbsCommon {

    //注入代理
    public void inject(AbsPresenterDelegate delegate);

    //获取当前P层对应View层
    public T getBindView();

    //指定Activity Class 跳转页面
    default void startActivity(Class mClass) {
        getContext().startActivity(new Intent(getContext(),mClass));
    }

}
