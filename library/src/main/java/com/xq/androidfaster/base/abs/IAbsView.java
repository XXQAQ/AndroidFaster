package com.xq.androidfaster.base.abs;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
    default View findViewById(int id) {
        return getRootView().findViewById(id);
    }

}
