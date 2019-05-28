package com.xq.androidfaster.base.base;

import android.support.v4.app.Fragment;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.life.ViewLife;
import java.util.List;

public interface IFasterBaseView<T extends IFasterBasePresenter> extends IAbsView<T>, ViewLife {

    //获取当前View层所有delegate
    public List<AbsViewDelegate> getDelegates();

    //返回布局ID
    public int getLayoutId();

    //在当前页面是否重建并保存
    public boolean hasRestoreState();

    //为P层提供快速初始化Fragment的方法(可以传入多种Fragment,并在实现类判断具体的Fragment类型)
    public void initFragment(Fragment fragment);

}
