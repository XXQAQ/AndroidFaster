package com.xq.androidfaster.base.abs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.xq.androidfaster.base.life.ViewLife;

public class AbsViewDelegate<T extends IAbsPresenter> implements IAbsView<T>,ViewLife {

    private IAbsView<T> view;

    public AbsViewDelegate(IAbsView view) {
        this.view = view;
        inject(this);
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
    public T getBindPresenter() {
        return getBeDelegatedView().getBindPresenter();
    }

    @Override
    public Context getContext() {
        return getBeDelegatedView().getContext();
    }

    @Override
    public void inject(AbsViewDelegate delegate) {
        getBeDelegatedView().inject(delegate);
    }

    @Override
    public View getRootView() {
        return getBeDelegatedView().getRootView();
    }

    @Override
    public Fragment getAreFragment() {
        return getBeDelegatedView().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getBeDelegatedView().getAreActivity();
    }

    @Override
    public boolean isFirstVisible() {
        return getBeDelegatedView().isFirstVisible();
    }

    @Override
    public boolean isRestoreState() {
        return getBeDelegatedView().isRestoreState();
    }

    protected IAbsView<T> getBeDelegatedView(){
        return view;
    }

}
