package com.xq.projectdefine.base.base;


import com.xq.projectdefine.base.abs.IAbsPresenter;
import com.xq.projectdefine.base.abs.AbsPresenterDelegate;
import com.xq.projectdefine.base.life.PresenterLife;
import com.xq.projectdefine.util.tools.FragmentUtils;
import java.util.List;


public interface IFasterBasePresenter<T extends IFasterBaseView> extends IAbsPresenter<T>, PresenterLife,FragmentUtils.OnBackClickListener{

    //获取当前P层所有delegate
    public List<AbsPresenterDelegate> getDelegates();

    //指定Class跳转页面
    public void startActivity(Class mClass);

}
