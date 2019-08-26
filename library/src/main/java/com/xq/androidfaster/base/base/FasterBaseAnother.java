package com.xq.androidfaster.base.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

public abstract class FasterBaseAnother<T extends IFasterBaseBehavior> implements IFasterBaseBehavior<T> {

    private T another;

    public FasterBaseAnother(T another) {
        this.another = another;
       getLifecycle().addObserver(this);
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void visible() {

    }

    @Override
    public void invisible() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public View getRootView() {
        return getBindAnother().getRootView();
    }

    @Override
    public Lifecycle getLifecycle() {
        return getBindAnother().getLifecycle();
    }

    @Override
    public T getBindAnother() {
        return another;
    }

    @Override
    public void init() {

    }

    @Override
    public Context getContext() {
        return getBindAnother().getContext();
    }

    @Override
    public Fragment getAreFragment() {
        return getBindAnother().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getBindAnother().getAreActivity();
    }

    @Override
    public void resolveBundle(Bundle bundle) {

    }

    @Override
    public boolean isFirstVisible() {
        return getBindAnother().isFirstVisible();
    }

    @Override
    public boolean isRestoreState() {
        return getBindAnother().isRestoreState();
    }

    @Override
    public void initFragment(Fragment fragment) {

    }

    public String getString(int resId){
        return getResources().getString(resId);
    }

    public int getColor(int resId){
        return ContextCompat.getColor(getContext(),resId);
    }

}
