package com.xq.androidfaster.base.abs;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public interface IAbsView<T extends IAbsPresenter> extends IAbsCommon {

    //注入代理
    public void inject(AbsViewDelegate delegate);

    //获取当前View层对应P层
    public T getBindPresenter();

    //获取根布局View
    public View getRootView();

    //根布局截图
    public Bitmap getRootViewBitmap();

    //获取Window
    public Window getWindow();

    //获取WindowManager
    public WindowManager getWindowManager();

    //获取子FragmentManager，无需判断Activity或者Fragment的使用情景
    public FragmentManager getCPFragmentManager();

    //获取管理当前页面的FragmentManager
    public FragmentManager getParentFragmentManager();

    //获取布局构造器
    public LayoutInflater getLayoutInflater();

    //findViewById
    public View findViewById(int id);

    //当父Activity|Fragment销毁后是否保存子Fragmnet的状态
    public boolean isSaveFragmentState();

}
