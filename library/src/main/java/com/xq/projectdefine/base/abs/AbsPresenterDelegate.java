package com.xq.projectdefine.base.abs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.xq.projectdefine.base.life.PresenterLife;
import java.util.List;

public abstract class AbsPresenterDelegate<T extends AbsView> implements AbsPresenter<T>,PresenterLife {

    protected AbsPresenter<T> presenter;

    public AbsPresenterDelegate(AbsPresenter presenter) {
        this.presenter = presenter;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
    public void inject(AbsPresenterDelegate delegate) {
        presenter.inject(delegate);
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
    public String getString(int id) {
        return presenter.getString(id);
    }

    @Override
    public int getColor(int id) {
        return presenter.getColor(id);
    }
}
