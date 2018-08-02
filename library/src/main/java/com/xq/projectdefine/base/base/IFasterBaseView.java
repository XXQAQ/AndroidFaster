package com.xq.projectdefine.base.base;


import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.base.abs.AbsViewDelegate;
import com.xq.projectdefine.base.life.ViewLife;

import java.util.List;

public interface IFasterBaseView<T extends IFasterBasePresenter> extends AbsView<T>, ViewLife {

    //获取当前View层所有delegate
    public List<AbsViewDelegate> getDelegates();

    //返回布局ID
    public int getLayoutId();

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
