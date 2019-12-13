package com.xq.androidfaster.base.core;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
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
    public Fragment areFragment();

    //如果当前服务于Activity，则返回对应Activity，否则返回null
    public Activity areActivity();



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
    default FragmentManager getMatchFragmentManager() {
        if (areActivity() != null)
            return ((FragmentActivity) areActivity()).getSupportFragmentManager();
        else     if (areFragment() != null)
            return (areFragment()).getChildFragmentManager();
        return null;
    }

    //获取上级FragmentManager
    default FragmentManager getTopFragmentManager() {
        return ((FragmentActivity)getContext()).getSupportFragmentManager();
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
