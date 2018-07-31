package com.xq.projectdefine.base.abs;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.life.PresenterLife;

import java.util.List;

public abstract class AbsPresenterDelegate<T extends AbsView> implements AbsPresenter<T>,PresenterLife {

    protected AbsPresenter<T> presenter;

    public AbsPresenterDelegate(AbsPresenter presenter) {
        this.presenter = presenter;
        getDelegates().add(this);
    }

    @Override
    public T getBindView() {
        return presenter.getBindView();
    }

    @Override
    public Context getContext() {
        return presenter.getContext();
    }

    @Override
    public List<AbsPresenterDelegate> getDelegates() {
        return presenter.getDelegates();
    }

    @Override
    public Fragment getAreFragment() {
        return presenter.getAreFragment();
    }

    @Override
    public Activity getAreActivity() {
        return presenter.getAreActivity();
    }

    @Override
    public void finishSelf() {
        presenter.finishSelf();
    }

    @Override
    public void finish() {
        presenter.finish();
    }

    @Override
    public void back() {
        presenter.back();
    }

    @Override
    public void startActivity(Class mClass) {
        presenter.startActivity(mClass);
    }
}
