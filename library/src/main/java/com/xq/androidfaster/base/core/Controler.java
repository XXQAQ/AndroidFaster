package com.xq.androidfaster.base.core;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public interface Controler extends Life {

    ///////////////////////////////////////////////////////////////////////////
    // C
    ///////////////////////////////////////////////////////////////////////////
    //获取生命周期控制器
    public Lifecycle getLifecycle();

    //获取上下文
    public Context getContext();

    //如果当前服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //判断是否第一次可见
    public boolean isFirstVisible();

    //是否销毁并重建
    public boolean isRestoreState();

    //关闭当前页面
    default void finish() {
        ((Activity)getContext()).finish();
    }

    //回退(兼容Activity与Fragment的使用情形)
    default void back() {
        ((Activity)getContext()).onBackPressed();
    }

    //指定Activity Class 跳转页面
    default void startActivity(Class mClass) {
        getContext().startActivity(new Intent(getContext(),mClass));
    }



    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    //获取根布局View
    public View getRootView();

    //获取Window
    default Window getWindow() {
        return ((Activity)getContext()).getWindow();
    }

    //获取WindowManager
    default WindowManager getWindowManager() {
        return getWindow().getWindowManager();
    }

    //获取子FragmentManager，无需判断Activity或者Fragment的使用情景
    default FragmentManager getCPFragmentManager() {
        if (getAreActivity() != null)
            return ((FragmentActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getChildFragmentManager();
        return null;
    }

    //获取管理当前页面的FragmentManager
    default FragmentManager getParentFragmentManager() {
        if (getAreActivity() != null)
            return ((FragmentActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getFragmentManager();
        return null;
    }

    //获取布局构造器
    default LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //findViewById
    default <T_View extends View> T_View findViewById(int id) {
        return getRootView().findViewById(id);
    }

}
