package com.xq.androidfaster.base.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    //返回值决定下面所有的方法管理哪个FragmentManager,默认管理自己的子集
    default FragmentManager whichFragmentManager(){
        return getCPFragmentManager();
    }

    //添加Fragment
    default void addFragment(Fragment fragment){
        whichFragmentManager().beginTransaction().add(fragment,fragment.getClass().getName()).commitAllowingStateLoss();
    }

    default void addFragment(Fragment fragment,int containerId){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId);
    }

    default void addFragment(Fragment fragment,int containerId,boolean isHide){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId,isHide);
    }

    default void addFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.add(whichFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);;
    }

    //替换Fragment
    default void replaceFragment(Fragment fragment,int containerId){
        FragmentUtils.replace(whichFragmentManager(),fragment,containerId);
    }

    default void replaceFragment(Fragment fragment,int containerId,boolean isAddStack,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim){
        FragmentUtils.replace(whichFragmentManager(),fragment,containerId,isAddStack,enterAnim,exitAnim,popEnterAnim,popExitAnim);
    }

    //隐藏Fragment
    default void hideFragment(String fragmentName){
        hideFragment(whichFragmentManager().findFragmentByTag(fragmentName));
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
        showFragment(whichFragmentManager().findFragmentByTag(fragmentName));
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
        removeFragment(whichFragmentManager().findFragmentByTag(fragmentName));
    }

    default void removeFragment(Fragment fragment){
        FragmentUtils.remove(fragment);
    }

    //如果当前页面是Fragment，则移除自身
    default void removeMe(){
        if (getAreFragment() != null)
            FragmentUtils.remove(getAreFragment());
    }

    //弹出Fragment
    default void popFragment(){
        FragmentUtils.pop(whichFragmentManager());
    }

    default void popFragmentImmediate(){
        FragmentUtils.pop(whichFragmentManager(),true);
    }

    //显示所有Fragment
    default void showAllFragment(){
        FragmentUtils.show(whichFragmentManager());
    }

    //隐藏所有Fragment
    default void hideAllFragment(){
        FragmentUtils.hide(whichFragmentManager());
    }

    //移除所有Fragment
    default void removeAllFragment(){
        FragmentUtils.removeAll(whichFragmentManager());
    }

}
