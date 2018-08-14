package com.xq.projectdefine.base.abs;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public interface AbsCommon {

    //获取上下文
    public Context getContext();

    //如果当前P/V层服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前P/V层服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //结束自身，如果是Activity则等同于调用finish,如果是Fragment则移除Fragment自身而不会关闭Activity
    public void finishSelf();

    //关闭当前页面
    public void finish();

    //回退(兼容Activity与Fragment的使用情形)
    public void back();

    //获取String资源
    public String getString(int id);

    //获取color资源
    public int getColor(int id);

}
