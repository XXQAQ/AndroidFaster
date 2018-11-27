package com.xq.projectdefine.base.base;


import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.abs.IAbsView;
import com.xq.projectdefine.base.abs.AbsViewDelegate;
import com.xq.projectdefine.base.life.ViewLife;

import java.util.List;

public interface IFasterBaseView<T extends IFasterBasePresenter> extends IAbsView<T>, ViewLife {

    //获取当前View层所有delegate
    public List<AbsViewDelegate> getDelegates();

    //返回布局ID
    public int getLayoutId();

    //为P层提供快速初始化Fragment的方法(可以传入多种Fragment,并在实现类判断具体的Fragment类型)
    public void initFragment(Fragment fragment);

}
