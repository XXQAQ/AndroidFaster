package com.xq.androidfaster.base.base;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.life.PresenterLife;
import com.xq.androidfaster.util.tools.FragmentUtils;
import java.util.List;

public interface IFasterBasePresenter<T extends IFasterBaseView> extends IAbsPresenter<T>, PresenterLife,FragmentUtils.OnBackClickListener{

    //获取当前P层所有delegate
    public List<AbsPresenterDelegate> getDelegates();

}
