package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    protected T presenter;

    protected View rootView;

    public FasterBaseView(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

        if (getPresenter().getAreActivity() != null)
        {
            rootView = getPresenter().getAreActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }
        else    if (getPresenter().getAreFragment() != null)
        {
            rootView = getPresenter().getAreFragment().getView();
        }
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
        return presenter;
    }

    @Override
    public Context getContext() {
        return getPresenter().getContext();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

}
