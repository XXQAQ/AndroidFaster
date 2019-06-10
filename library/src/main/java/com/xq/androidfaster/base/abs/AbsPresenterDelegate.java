package com.xq.androidfaster.base.abs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.xq.androidfaster.base.life.PresenterLife;

public class AbsPresenterDelegate<T extends IAbsView> implements IAbsPresenter<T>,PresenterLife {

    private IAbsPresenter<T> presenter;

    public AbsPresenterDelegate(IAbsPresenter presenter) {
        this.presenter = presenter;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public T getBindView() {
        return getBeDelegatedPresenter().getBindView();
    }

    @Override
    public Context getContext() {
        return getBeDelegatedPresenter().getContext();
    }

    @Override
    public void inject(AbsPresenterDelegate delegate) {
        getBeDelegatedPresenter().inject(delegate);
    }

    @Override
    public Fragment getAreFragment() {
        return getBeDelegatedPresenter().getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return getBeDelegatedPresenter().getAreActivity();
    }

    @Override
    public boolean isFirstVisible() {
        return getBeDelegatedPresenter().isFirstVisible();
    }

    @Override
    public boolean isRestoreState() {
        return getBeDelegatedPresenter().isRestoreState();
    }

    protected IAbsPresenter<T> getBeDelegatedPresenter(){
        return presenter;
    }

}
