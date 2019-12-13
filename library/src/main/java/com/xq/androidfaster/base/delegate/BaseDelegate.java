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

public class BaseDelegate implements Controler {

    private WeakReference<Controler> reference;

    public BaseDelegate(Controler controler) {
        this.reference = new WeakReference<Controler>(controler);
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
    public Fragment areFragment() {
        return getControler().areFragment();
    }

    @Override
    public Activity areActivity() {
        return getControler().areActivity();
    }

    @Override
    public View getRootView() {
        return getControler().getRootView();
    }

    protected Controler getControler() {
        return reference.get();
    }
}
