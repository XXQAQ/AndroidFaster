package com.xq.projectdefine.base.base;


import com.xq.projectdefine.base.abs.AbsView;


public interface IFasterBaseView<T extends IFasterBasePresenter> extends AbsView<T> {

    public int getLayoutId();

}
