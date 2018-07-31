package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.life.PresenterLife;
import com.xq.projectdefine.util.callback.ActivityResultCallback;

import java.util.List;


public interface IFasterBasePresenter<T extends IFasterBaseView> extends PresenterLife{

    //获取该P层对应View层
    public T getBindView();

    //获取上下文
    public Context getContext();

    public List<PresenterLife> getLifes();

    //如果当前P层服务于Fragment，则返回对应Fragment，否则返回null
    public Fragment getAreFragment();

    //如果当前P层服务于Activity，则返回对应Activity，否则返回null
    public Activity getAreActivity();

    //结束自身，如果是Activity则等同于调用finish,如果是Fragment则将从FragmentManager中移除
    public void finishSelf();

    //关闭当前页面
    public void finish();

    //回退(兼容Activity与Fragment的使用情形)
    public void back();

    //指定Class快捷跳转页面
    public void startActivity(Class mClass);

}
