package com.xq.androidfaster.base.abs;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public interface IAbsCommon {

    //获取上下文
    public Context getContext();

    //如果当前P/V层服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前P/V层服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //判断是否第一次可见
    public boolean isFirstVisible();

    //是否销毁并重建
    public boolean isRestoreState();

    //关闭当前页面
    default void finish() {
        ((Activity)getContext()).finish();
    }

    //回退(兼容Activity与Fragment的使用情形)
    default void back() {
        ((Activity)getContext()).onBackPressed();
    }

}
