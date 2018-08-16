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
import com.xq.projectdefine.util.tools.FragmentUtils;
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
        for (ViewLife life : list_delegate)     life.onDestroy();   list_delegate.clear();
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
    public void inject(AbsViewDelegate delegate) {
        getDelegates().add(delegate);
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

    @Override
    public Fragment getAreFragment() {
        return getPresenter().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getPresenter().getAreActivity();
    }

    @Override
    public void finishSelf() {
        getPresenter().finishSelf();
    }

    @Override
    public void finish() {
        getPresenter().finish();
    }

    @Override
    public void back() {
        getPresenter().back();
    }

    @Override
    public String getString(int id) {
        return getContext().getResources().getString(id);
    }

    @Override
    public int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    public Window getWindow() {
        return ((Activity)getContext()).getWindow();
    }

    @Override
    public WindowManager getWindowManager(){
        return getWindow().getWindowManager();
    }

    @Override
    public FragmentManager getCPFragmentManager() {
        if (getAreActivity() != null)
            return ((FragmentActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getChildFragmentManager();
        return null;
    }

    @Override
    public LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Bitmap getRootViewBitmap(){
        return ImageUtils.view2Bitmap(getRootView());
    }

    @Override
    public View findViewById(int id){
        return getRootView().findViewById(id);
    }

    @Override
    public void initFragment(Fragment fragment) {

    }

    //添加Fragment
    protected void addFragment(Fragment fragment,int containerId){
        addFragment(fragment,containerId,false);
    }

    protected void addFragment(Fragment fragment,int containerId,boolean isHide){
        addFragment(fragment,containerId,false,false);
    }

    protected void addFragment(Fragment fragment,int containerId,boolean isHide,boolean isAddStack){
        FragmentUtils.add(getCPFragmentManager(),fragment,containerId,isHide,isAddStack);;
    }

    //替换Fragment
    protected void replaceFragment(Fragment fragment,int containerId){
        replaceFragment(fragment,containerId,false);
    }

    protected void replaceFragment(Fragment fragment,int containerId,boolean isAddStack){
        FragmentUtils.replace(getCPFragmentManager(),fragment,containerId,isAddStack);
    }

    //移除Fragment
    protected void removeFragment(String fragmentName){
        FragmentUtils.remove(getCPFragmentManager().findFragmentByTag(fragmentName));
    }

    //移除所有Fragment
    protected void removeAllFragment(){
        FragmentUtils.removeAll(getCPFragmentManager());
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

    //返回false可以取消默认findViewById
    protected boolean isAutoFindView(){
        return true;
    }

    //自动findViewById
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
