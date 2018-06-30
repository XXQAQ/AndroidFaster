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


public interface AbsView<T extends AbsPresenter> extends ViewLife {

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

    //获取Window
    default Window getWindow() {
        return ((Activity)getContext()).getWindow();
    }

    //获取WindowManager
    default WindowManager getWindowManager(){
        return getWindow().getWindowManager();
    }

    //获取对应FragmentManager，无需判断Activity或者Fragment下的不同使用情景
    default FragmentManager getCPFragmentManager() {
        if (getAreActivity() != null)
            return ((FragmentActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getChildFragmentManager();
        return null;
    }

    //获取布局构造器
    default LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //根布局截图
    default Bitmap getRootViewBitmap(){
        return ImageUtils.view2Bitmap(getRootView());
    }

    //findViewById
    default View findViewById(int id){
        return getRootView().findViewById(id);
    }

}
