package com.xq.projectdefine.base.base;


import com.xq.projectdefine.base.abs.AbsView;

public interface IFasterBaseView<T extends IFasterBasePresenter> extends AbsView<T> {

    public int getLayoutId();

    public void initToolbar(String title);

    public void initToolbar(String title, boolean isback);

    public String getBehaviorDescription();

}
