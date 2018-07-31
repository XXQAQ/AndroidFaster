package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xq.projectdefine.base.abs.AbsViewDelegate;
import com.xq.projectdefine.base.life.ViewLife;
import com.xq.projectdefine.util.tools.ImageUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    protected T presenter;

    protected View rootView;

    private List<AbsViewDelegate> list_delegate = new LinkedList<>();

    public FasterBaseView(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

        if (getPresenter().getAreActivity() != null)
        {
            rootView = getPresenter().getAreActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }
        else    if (getPresenter().getAreFragment() != null)
        {
            rootView = getPresenter().getAreFragment().getView();
        }

        if (isAutoFindView())
            autoFindView();

        for (ViewLife life : list_delegate)     life.afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        for (ViewLife life : list_delegate)     life.onResume();
    }

    @Override
    public void onPause() {
        for (ViewLife life : list_delegate)     life.onPause();
    }

    @Override
    public void onDestroy() {
        for (ViewLife life : list_delegate)     life.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (ViewLife life : list_delegate)     life.onSaveInstanceState(outState);
    }

    @Override
    public List<AbsViewDelegate> getDelegates() {
        return list_delegate;
    }

    @Override
    public T getPresenter() {
        return presenter;
    }

    @Override
    public Context getContext() {
        return getPresenter().getContext();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public Fragment getAreFragment() {
        return getPresenter().getAreFragment();
    }

    public Activity getAreActivity() {
        return getPresenter().getAreActivity();
    }

    public void finishSelf() {
        getPresenter().finishSelf();
    }

    public void finish() {
        getPresenter().finish();
    }

    public void back() {
        getPresenter().back();
    }

    public Window getWindow() {
        return ((Activity)getContext()).getWindow();
    }

    public WindowManager getWindowManager(){
        return getWindow().getWindowManager();
    }

    public FragmentManager getCPFragmentManager() {
        if (getAreActivity() != null)
            return ((FragmentActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getChildFragmentManager();
        return null;
    }

    public LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Bitmap getRootViewBitmap(){
        return ImageUtils.view2Bitmap(getRootView());
    }

    public View findViewById(int id){
        return getRootView().findViewById(id);
    }

    //判断是否顶部容器
    protected boolean isTopContainer(){
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations)
        {
            if (annotation instanceof TopContainer)
                return true;
        }
        return false;
    }

    //重定义此方法返回false可以取消默认findViewById
    protected boolean isAutoFindView(){
        return true;
    }

    //自动findViewById(必须保证布局文件中的id与变量名一致)
    private void autoFindView() {
        Class mClass = this.getClass();
        while (true)
        {
            Field[] fields = mClass.getDeclaredFields();
            for (Field field : fields)
            {
                if (View.class.isAssignableFrom(field.getType()))
                {
                    String fileName = field.getName();
                    View view = findViewById(getContext().getResources().getIdentifier(fileName, "id", getContext().getPackageName()));
                    if (view != null)
                    {
                        field.setAccessible(true);
                        try {
                            field.set(this,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            mClass = mClass.getSuperclass();
            if (mClass.getName().equals(Object.class.getName()))
                break;
        }
    }

}
