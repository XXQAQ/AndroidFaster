package com.xq.projectdefine.base.abs;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public interface AbsView<T extends AbsPresenter> extends AbsCommon {

    //注入代理
    public void inject(AbsViewDelegate delegate);

    //获取当前View层对应P层
    public T getPresenter();

    //获取根布局View
    public View getRootView();

    //获取Window
    public Window getWindow();

    //获取WindowManager
    public WindowManager getWindowManager();

    //获取子FragmentManager，无需判断Activity或者Fragment的使用情景
    public FragmentManager getCPFragmentManager();

    //获取管理当前页面的FragmentManager
    public FragmentManager getFragmentManager();

    //获取布局构造器
    public LayoutInflater getLayoutInflater();

    //根布局截图
    public Bitmap getRootViewBitmap();

    //findViewById
    public View findViewById(int id);

}
