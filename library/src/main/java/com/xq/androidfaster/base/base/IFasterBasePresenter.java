package com.xq.androidfaster.base.base;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.life.PresenterLife;
import com.xq.androidfaster.util.tools.FragmentUtils;

public interface IFasterBasePresenter<T extends IFasterBaseView> extends IAbsPresenter<T>,IFasterBaseCommon,PresenterLife,FragmentUtils.OnBackClickListener{

}
