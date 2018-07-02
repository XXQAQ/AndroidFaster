package com.xq.projectdefine.base.base;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public interface IFasterBasePresenter<T extends IFasterBaseView> extends AbsPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        for (AbsPresenter presenter : getImplementspresenters())
        {
            presenter.afterOnCreate(savedInstanceState);
        }
    }

    @Override
    default void onResume() {
        for (AbsPresenter presenter : getImplementspresenters())
        {
            presenter.onResume();
        }
    }

    @Override
    default void onPause() {
        for (AbsPresenter presenter : getImplementspresenters())
        {
            presenter.onPause();
        }
    }

    @Override
    default void onDestroy() {
        for (AbsPresenter presenter : getImplementspresenters())
        {
            presenter.onDestroy();
        }
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (AbsPresenter presenter : getImplementspresenters())
        {
            presenter.onActivityResult(requestCode,resultCode,data);
        }
    }

    default List<AbsPresenter> getImplementspresenters(){
        List<AbsPresenter> list = new LinkedList<>();
        Type[] types = getClass().getGenericInterfaces();
        for (Type type : types)
        {
            if (type instanceof AbsPresenter)
            {
                list.add((AbsPresenter) type);
            }
        }
        return list;
    }

}
