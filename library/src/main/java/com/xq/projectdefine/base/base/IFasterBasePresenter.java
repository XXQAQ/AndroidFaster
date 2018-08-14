package com.xq.projectdefine.base.base;


import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsPresenterDelegate;
import com.xq.projectdefine.base.life.PresenterLife;

import java.util.List;


public interface IFasterBasePresenter<T extends IFasterBaseView> extends AbsPresenter<T>, PresenterLife{

    //获取当前P层所有delegate
    public List<AbsPresenterDelegate> getDelegates();

    //指定Class跳转页面
    public void startActivity(Class mClass);

}
