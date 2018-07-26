package com.xq.projectdefine.base.abs;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    default Fragment getAreFragment() {
        return getPresenter().getAreFragment();
    }

    //如果当前View层服务于Activity，则返回对应Activity，否则返回null
    default Activity getAreActivity() {
        return getPresenter().getAreActivity();
    }

    //获取Window
    default Window getWindow() {
        return ((Activity)getContext()).getWindow();
    }

    //获取WindowManager
    default WindowManager getWindowManager(){
        return getWindow().getWindowManager();
    }

    //获取对应FragmentManager，无需判断Activity或者Fragment的使用情景
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

//    //以下封装 添加Fragment的便捷方法
//    default void addFragment(Fragment fragment){
//        addFragment(fragment,false);
//    }
//
//    default void addFragment(Fragment fragment,boolean isOverride){
//        FragmentTransaction transaction = getCPFragmentManager().beginTransaction();
//        if (getCPFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null)
//            if (isOverride)
//                transaction.remove(fragment);
//            else
//                return;
//        transaction.add(fragment,fragment.getClass().getName());
//        transaction.commitAllowingStateLoss();
//    }
//
//    default void addFragment(int id,Fragment fragment){
//        addFragment(id,fragment,false);
//    }
//
//    default void addFragment(int id,Fragment fragment,boolean isOverride){
//        FragmentTransaction transaction = getCPFragmentManager().beginTransaction();
//        if (getCPFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null)
//            if (isOverride)
//                transaction.remove(fragment);
//            else
//                return;
//        transaction.add(id,fragment,fragment.getClass().getName());
//        transaction.commitAllowingStateLoss();
//    }
//
//    default void replaceFragment(int id,Fragment fragment){
//        replaceFragment(id,fragment,false);
//    }
//
//    default void replaceFragment(int id, Fragment fragment,boolean isOverride) {
//        FragmentTransaction transaction = getCPFragmentManager().beginTransaction();
//        if (getCPFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null)
//            if (!isOverride)
//                return;
//        transaction.replace(id,fragment,fragment.getClass().getName());
//        transaction.commitAllowingStateLoss();
//    }
//
//    default void removeFragment(String fragmentName){
//        removeFragment(getCPFragmentManager().findFragmentByTag(fragmentName.getClass().getName()));
//    }
//
//    default void removeFragment(Fragment fragment){
//        FragmentTransaction transaction = getCPFragmentManager().beginTransaction();
//        transaction.remove(fragment);
//        transaction.commitAllowingStateLoss();
//    }
//
//    default void clearFragments(){
//        FragmentTransaction transaction = getCPFragmentManager().beginTransaction();
//        for (Fragment fragment : getCPFragmentManager().getFragments())
//        {
//            transaction.remove(fragment);
//        }
//        transaction.commitAllowingStateLoss();
//    }


}
