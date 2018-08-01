package com.xq.projectdefine.base.abs;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public interface AbsView<T extends AbsPresenter> extends AbsCommon {

    //获取当前View层所有delegate
    public List<AbsViewDelegate> getDelegates();

    //获取当前View层对应P层
    public T getPresenter();

    //获取根布局View
    public View getRootView();

    //获取Window
    public Window getWindow();

    //获取WindowManager
    public WindowManager getWindowManager();

    //获取对应FragmentManager，无需判断Activity或者Fragment的使用情景
    public FragmentManager getCPFragmentManager();

    //获取布局构造器
    public LayoutInflater getLayoutInflater();

    //根布局截图
    public Bitmap getRootViewBitmap();

    //findViewById
    public View findViewById(int id);

}
