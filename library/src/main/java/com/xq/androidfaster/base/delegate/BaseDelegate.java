package com.xq.androidfaster.base.delegate;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.xq.androidfaster.base.core.Controler;
import java.lang.ref.WeakReference;

public class BaseDelegate<T extends Controler> implements Controler<T> {

    private WeakReference<T> controlerReference;

    public BaseDelegate(T controler) {
        this.controlerReference = new WeakReference<T>(controler);
        getLifecycle().addObserver(this);
    }

    @Override
    public boolean isFirstVisible() {
        return getControler().isFirstVisible();
    }

    @Override
    public boolean isRestoreState() {
        return getControler().isRestoreState();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public Lifecycle getLifecycle() {
        return getControler().getLifecycle();
    }

    @Override
    public Context getContext() {
        return getControler().getContext();
    }

    @Override
    public Fragment getAreFragment() {
        return getControler().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getControler().getAreActivity();
    }

    @Override
    public View getRootView() {
        return getControler().getRootView();
    }

    protected T getControler() {
        return controlerReference.get();
    }
}
