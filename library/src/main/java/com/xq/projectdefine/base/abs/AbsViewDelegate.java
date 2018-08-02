package com.xq.projectdefine.base.abs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xq.projectdefine.base.life.ViewLife;

import java.util.List;

public abstract class AbsViewDelegate<T extends AbsPresenter> implements AbsView<T>,ViewLife {

    protected AbsView<T> view;

    public AbsViewDelegate(AbsView view) {
        this.view = view;
        inject(this);
    }

    @Override
    public T getPresenter() {
        return view.getPresenter();
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void inject(AbsViewDelegate delegate) {
        view.inject(delegate);
    }

    @Override
    public View getRootView() {
        return view.getRootView();
    }

    @Override
    public Fragment getAreFragment() {
        return view.getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return view.getAreActivity();
    }

    @Override
    public void finishSelf() {
        view.finishSelf();
    }

    @Override
    public void finish() {
        view.finish();
    }

    @Override
    public void back() {
        view.back();
    }

    @Override
    public Window getWindow() {
        return view.getWindow();
    }

    @Override
    public WindowManager getWindowManager() {
        return view.getWindowManager();
    }

    @Override
    public FragmentManager getCPFragmentManager() {
        return view.getCPFragmentManager();
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return view.getLayoutInflater();
    }

    @Override
    public Bitmap getRootViewBitmap() {
        return view.getRootViewBitmap();
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

}
