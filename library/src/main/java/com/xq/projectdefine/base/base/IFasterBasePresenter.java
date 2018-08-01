package com.xq.projectdefine.base.base;



import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.life.PresenterLife;



public interface IFasterBasePresenter<T extends IFasterBaseView> extends AbsPresenter<T>, PresenterLife{

    //指定Class快捷跳转页面
    public void startActivity(Class mClass);

}
