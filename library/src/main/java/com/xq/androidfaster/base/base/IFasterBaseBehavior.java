package com.xq.androidfaster.base.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster.util.tools.FragmentUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface IFasterBaseBehavior<T extends IFasterBaseBehavior> extends Controler{

    ///////////////////////////////////////////////////////////////////////////
    // C
    ///////////////////////////////////////////////////////////////////////////
    //获取当前P/V层
    public T getBindAnother();

    //初始化
    public void init();

    //解析数据
    public void resolveBundle(Bundle bundle);

    //获取资源文件
    default Resources getResources(){
        return getContext().getResources();
    }

    //关闭当前页面(兼容Activity与Fragment的使用情形)
    default void finish() {
        if (getAreActivity() != null)
            getAreActivity().finish();
        else if (getAreFragment() != null)
            removeMe();
    }

    //回退(兼容Activity与Fragment的使用情形)
    default void back() {
        ((Activity)getContext()).onBackPressed();
    }

    //指定Activity Class跳转页面
    default void startActivity(Class mClass) {
        getContext().startActivity(new Intent(getContext(),mClass));
    }



    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    //获取当前对应V层
    default T getBindView() {
        return getBindAnother();
    }



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    //获取当前对应P层
    default T getBindPresenter() {
        return getBindAnother();
    }

    //返回布局ID
    public int getLayoutId();

    //判断是否顶部容器
    default boolean isTopContainer(Object container){
        Annotation[] annotations = container.getClass().getAnnotations();
        for (Annotation annotation : annotations)
        {
            if (annotation instanceof TopContainer)
                return true;
        }
        return false;
    }

    //全自动FindView
    default void autoFindView(Object container) {
        Class mClass = container.getClass();
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
                            field.set(container,view);
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



    //为P层提供快速初始化Fragment的方法(可以传入多种Fragment,并在实现类判断具体的Fragment类型)
    public void initFragment(Fragment fragment);

    //以下为Fragment快捷管理
    //添加Fragment
    default void addFragment(Fragment fragment){
        getCPFragmentManager().beginTransaction().add(fragment,fragment.getClass().getName()).commitAllowingStateLoss();
    }

    default void addFragment(Fragment fragment,int containerId){
        FragmentUtils.add(getCPFragmentManager(),fragment,containerId);
    }

    default void addFragment(Fragment fragment,int containerId,boolean isHide){
        FragmentUtils.add(getCPFragmentManager(),fragment,containerId,isHide);
    }

    default void addFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.add(getCPFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);;
    }

    //替换Fragment
    default void replaceFragment(Fragment fragment,int containerId){
        FragmentUtils.replace(getCPFragmentManager(),fragment,containerId);
    }

    default void replaceFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.replace(getCPFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);
    }

    //隐藏Fragment
    default void hideFragment(String fragmentName){
        hideFragment(getCPFragmentManager().findFragmentByTag(fragmentName));
    }

    default void hideFragment(Fragment fragment){
        FragmentUtils.hide(fragment);
    }

    //如果当前页面是Fragment，则隐藏自身
    default void hideMe(){
        if (getAreFragment() != null)
            FragmentUtils.hide(getAreFragment());
    }

    //显示Fragment
    default void showFragment(String fragmentName){
        showFragment(getCPFragmentManager().findFragmentByTag(fragmentName));
    }

    default void showFragment(Fragment fragment){
        FragmentUtils.show(fragment);
    }

    //如果当前页面是Fragment，则显示自身
    default void showMe(){
        if (getAreFragment() != null)
            FragmentUtils.show(getAreFragment());
    }

    //移除Fragment
    default void removeFragment(String fragmentName){
        removeFragment(getCPFragmentManager().findFragmentByTag(fragmentName));
    }

    default void removeFragment(Fragment fragment){
        FragmentUtils.remove(fragment);
    }

    //如果当前页面是Fragment，则移除自身
    default void removeMe(){
        if (getAreFragment() != null)
        {
            if (FragmentUtils.getTopShowInStack(getParentFragmentManager()) == getAreFragment())
                FragmentUtils.pop(getParentFragmentManager());
            FragmentUtils.remove(getAreFragment());
        }
    }

    //弹出Fragment
    default void popFragment(){
        FragmentUtils.pop(getCPFragmentManager());
    }

    default void popFragmentImmediate(){
        FragmentUtils.pop(getCPFragmentManager(),true);
    }

    //显示所有Fragment
    default void showAllFragment(){
        FragmentUtils.show(getCPFragmentManager());
    }

    //隐藏所有Fragment
    default void hideAllFragment(){
        FragmentUtils.hide(getCPFragmentManager());
    }

    //移除所有Fragment
    default void removeAllFragment(){
        FragmentUtils.removeAll(getCPFragmentManager());
    }

}
