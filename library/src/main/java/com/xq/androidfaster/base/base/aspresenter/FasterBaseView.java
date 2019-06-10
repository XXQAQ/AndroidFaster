package com.xq.androidfaster.base.base.aspresenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.base.IFasterBasePresenter;
import com.xq.androidfaster.base.base.IFasterBaseView;
import com.xq.androidfaster.base.base.TopContainer;
import com.xq.androidfaster.base.life.ViewLife;
import com.xq.androidfaster.util.tools.FragmentUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    private T presenter;

    private View rootView;

    private List<AbsViewDelegate> list_delegate = new LinkedList<>();

    public FasterBaseView(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void create(Bundle savedInstanceState) {

        if (getBindPresenter().getAreActivity() != null)
        {
            rootView = getBindPresenter().getAreActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }
        else    if (getBindPresenter().getAreFragment() != null)
        {
            rootView = getBindPresenter().getAreFragment().getView();
        }

        autoFindView();

        for (ViewLife life : list_delegate)     life.create(savedInstanceState);
    }

    @Override
    public void visible() {
        for (ViewLife life : list_delegate)     life.visible();
    }

    @Override
    public void invisible() {
        for (ViewLife life : list_delegate)     life.invisible();
    }

    @Override
    public void destroy() {
        for (ViewLife life : list_delegate)     life.destroy();   list_delegate.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (ViewLife life : list_delegate)     life.onSaveInstanceState(outState);
    }

    @Override
    public void inject(AbsViewDelegate delegate) {
        getDelegates().add(delegate);
    }

    protected List<AbsViewDelegate> getDelegates() {
        return list_delegate;
    }

    @Override
    public T getBindPresenter() {
        return presenter;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Context getContext() {
        return getBindPresenter().getContext();
    }

    @Override
    public Fragment getAreFragment() {
        return getBindPresenter().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getBindPresenter().getAreActivity();
    }

    @Override
    public boolean isFirstVisible() {
        return getBindPresenter().isFirstVisible();
    }

    @Override
    public boolean isRestoreState() {
        return getBindPresenter().isRestoreState();
    }

    @Override
    public void initFragment(Fragment fragment) {

    }



    //以下为Fragment快捷管理方法
    //返回值决定下面所有的方法管理哪个FragmentManager,默认管理自己的子集
    protected FragmentManager whichFragmentManager(){
        return getCPFragmentManager();
    }

    //添加Fragment
    protected void addFragment(Fragment fragment){
        whichFragmentManager().beginTransaction().add(fragment,fragment.getClass().getName()).commitAllowingStateLoss();
    }

    protected void addFragment(Fragment fragment,int containerId){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId);
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

    //自动findViewById
    protected void autoFindView() {
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
