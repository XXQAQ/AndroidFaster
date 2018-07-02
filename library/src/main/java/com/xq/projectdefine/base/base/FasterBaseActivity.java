package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.xq.projectdefine.base.abs.AbsPresenter;

import java.lang.reflect.Type;

public abstract class FasterBaseActivity<T extends IFasterBaseView> extends AppCompatActivity implements IFasterBasePresenter<T> {

    protected T view;

    //构造方案
    {
        view = createBindView();

        initData();
    }

    //重写该方法，返回对应View层
    protected abstract T createBindView();

    //该方法用于解析从其他页面传来的数据，注意如果传递数据不存在则不会执行该方法
    protected void resolveBundle(Bundle bundle) {

    }

    //初始化数据
    protected void initData(){

    }

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getBindView() != null)
            setContentView(getBindView().getLayoutId());

        Intent intent = getIntent();
        if (intent != null)
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                resolveBundle(bundle);
        }

        afterOnCreate(savedInstanceState);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

        if (getBindView() != null) getBindView().afterOnCreate(savedInstanceState);

        getImplementsPresenter().afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getBindView() != null) getBindView().onResume();

        getImplementsPresenter().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getBindView() != null) getBindView().onPause();

        getImplementsPresenter().onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getBindView() != null) getBindView().onDestroy();

        getImplementsPresenter().onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getBindView() != null) getBindView().onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        getImplementsPresenter().onActivityResult(requestCode,resultCode,data);
    }

    private AbsPresenter getImplementsPresenter(){
        Type[] types = getClass().getGenericInterfaces();
        for (Type type : types)
        {
            if (type instanceof AbsPresenter)
            {
                return (AbsPresenter) type;
            }
        }
        return null;
    }

    @Override
    public Fragment getAreFragment() {
        return null;
    }

    @Override
    public Activity getAreActivity() {
        return this;
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public T getBindView() {
        return view;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
