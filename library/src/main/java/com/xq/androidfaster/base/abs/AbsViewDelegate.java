package com.xq.androidfaster.base.abs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.xq.androidfaster.base.life.ViewLife;

public abstract class AbsViewDelegate<T extends IAbsPresenter> implements IAbsView<T>,ViewLife {

    protected IAbsView<T> view;

    public AbsViewDelegate(IAbsView view) {
        this.view = view;
        inject(this);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

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
    public FragmentManager getParentFragmentManager() {
        return view.getParentFragmentManager();
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

    @Override
    public String getString(int id) {
        return view.getString(id);
    }

}
