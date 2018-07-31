package com.xq.projectdefine.base.abs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xq.projectdefine.base.life.ViewLife;
import com.xq.projectdefine.util.tools.ImageUtils;

import java.util.List;

public interface AbsView<T extends AbsPresenter> {

    public List<AbsViewDelegate> getDelegates();

    //获取该View层对应P层
    public T getPresenter();

    //获取上下文
    public Context getContext();

    //获取根布局View
    public View getRootView();

    //如果当前View层服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前View层服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //结束自身，如果是Activity则等同于调用finish,如果是Fragment则将从FragmentManager中移除
    public void finishSelf();

    //关闭当前页面
    public void finish();

    //回退(兼容Activity与Fragment的使用情形)
    public void back();

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
