package com.xq.androidfaster.base.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.life.ViewLife;
import com.xq.androidfaster.util.tools.FragmentUtils;
import com.xq.androidfaster.util.tools.ImageUtils;
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
    public Bitmap getRootViewBitmap(){
        return ImageUtils.view2Bitmap(getRootView());
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
    public FragmentManager getParentFragmentManager() {
        if (getAreActivity() != null)
            return ((AppCompatActivity)getAreActivity()).getSupportFragmentManager();
        else     if (getAreFragment() != null)
            return (getAreFragment()).getFragmentManager();
        return null;
    }

    @Override
    public LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View findViewById(int id){
        return getRootView().findViewById(id);
    }

    @Override
    public void initFragment(Fragment fragment) {

    }



    //以下为Fragment快捷管理
    //返回值决定下面所有的方法管理哪个FragmentManager,默认管理自己的子集
    protected FragmentManager whichFragmentManager(){
        return getCPFragmentManager();
    }

    //添加Fragment
    protected void addFragment(Fragment fragment){
        whichFragmentManager().beginTransaction().add(fragment,fragment.getClass().getName()).commitAllowingStateLoss();
    }
    protected void addFragment(Fragment fragment,int containerId){
        addFragment(fragment,containerId,false);
    }
    protected void addFragment(Fragment fragment,int containerId,boolean isHide){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId,isHide);
    }
    protected void addFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);;
    }

    //替换Fragment
    protected void replaceFragment(Fragment fragment,int containerId){
        FragmentUtils.replace(whichFragmentManager(),fragment,containerId);
    }
    protected void replaceFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.replace(whichFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);
    }

    //隐藏Fragment
    protected void hideFragment(String fragmentName){
        hideFragment(whichFragmentManager().findFragmentByTag(fragmentName));
    }
    protected void hideFragment(Fragment fragment){
        FragmentUtils.hide(fragment);
    }

    //如果当前页面是Fragment，则隐藏自身
    protected void hideMe(){
        if (getAreFragment() != null)
            FragmentUtils.hide(getAreFragment());
    }

    //显示Fragment
    protected void showFragment(String fragmentName){
        showFragment(whichFragmentManager().findFragmentByTag(fragmentName));
    }
    protected void showFragment(Fragment fragment){
        FragmentUtils.show(fragment);
    }

    //如果当前页面是Fragment，则显示自身
    protected void showMe(){
        if (getAreFragment() != null)
            FragmentUtils.show(getAreFragment());
    }

    //移除Fragment
    protected void removeFragment(String fragmentName){
        removeFragment(whichFragmentManager().findFragmentByTag(fragmentName));
    }
    protected void removeFragment(Fragment fragment){
        FragmentUtils.remove(fragment);
    }

    //如果当前页面是Fragment，则移除自身
    protected void removeMe(){
        if (getAreFragment() != null)
            FragmentUtils.remove(getAreFragment());
    }

    //弹出Fragment
    protected void popFragment(){
        FragmentUtils.pop(whichFragmentManager());
    }
    protected void popFragmentImmediate(){
        FragmentUtils.pop(whichFragmentManager(),true);
    }

    //显示所有Fragment
    protected void showAllFragment(){
        FragmentUtils.show(whichFragmentManager());
    }

    //隐藏所有Fragment
    protected void hideAllFragment(){
        FragmentUtils.hide(whichFragmentManager());
    }

    //移除所有Fragment
    protected void removeAllFragment(){
        FragmentUtils.removeAll(whichFragmentManager());
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

    //返回false可以取消默认FindViewById
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
