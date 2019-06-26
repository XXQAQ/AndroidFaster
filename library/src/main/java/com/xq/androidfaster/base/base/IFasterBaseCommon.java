package com.xq.androidfaster.base.base;

import com.xq.androidfaster.base.abs.IAbsCommon;

public interface IFasterBaseCommon extends IAbsCommon {

    //判断是否第一次可见
    public boolean isFirstVisible();

    //是否销毁并重建
    public boolean isRestoreState();

}
