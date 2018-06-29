package com.xq.projectdefine.base.abs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.life.PresenterLife;


public interface AbsPresenter<T extends AbsView> extends PresenterLife {

    //获取该P层对应View层
    public T getBindView();

    //获取上下文
    public Context getContext();

    //如果当前P层服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前P层服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //结束自身，如果是Activity则等同于调用finish,如果是Fragment则将从FragmentManager中移除
    public void finishSelf();

    //关闭当前页面
    public void finish();

    //打开Activity
    public void startActivity(Intent intent);

    //打开一堆Activity
    public void startActivities(Intent[] intents);

    //打开代返回值的Activity
    public void startActivityForResult(Intent intent, int requestCode);
}
