package com.xq.projectdefine.base.base;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xq.projectdefine.base.abs.AbsView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


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

        getImplementsView().afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        getImplementsView().onResume();
    }

    @Override
    public void onPause() {
        getImplementsView().onPause();
    }

    @Override
    public void onDestroy() {
        getImplementsView().onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getImplementsView().onSaveInstanceState(outState);
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

    protected boolean isTopContainer(){
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations)
        {
            if (annotation instanceof topcontainer)
                return true;
        }
        return false;
    }

    private AbsView getImplementsView(){
        Type[] types = getClass().getGenericInterfaces();
        for (Type type : types)
        {
            if (type instanceof AbsView)
            {
                return (AbsView) type;
            }
        }
        return null;
    }

}
