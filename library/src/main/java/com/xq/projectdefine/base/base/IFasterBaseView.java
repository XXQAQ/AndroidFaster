package com.xq.projectdefine.base.base;


import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsView;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public interface IFasterBaseView<T extends IFasterBasePresenter> extends AbsView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        for (AbsView view : getImplementsViews())
        {
            view.afterOnCreate(savedInstanceState);
        }
    }

    @Override
    default void onResume() {
        for (AbsView view : getImplementsViews())
        {
            view.onResume();
        }
    }

    @Override
    default void onPause() {
        for (AbsView view : getImplementsViews())
        {
            view.onPause();
        }
    }

    @Override
    default void onDestroy() {
        for (AbsView view : getImplementsViews())
        {
            view.onDestroy();
        }
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        for (AbsView view : getImplementsViews())
        {
            view.onSaveInstanceState(outState);
        }
    }

    default List<AbsView> getImplementsViews(){
        List<AbsView> list = new LinkedList<>();
        Type[] types = getClass().getGenericInterfaces();
        for (Type type : types)
        {
            if (type instanceof AbsView)
            {
                list.add((AbsView) type);
            }
        }
        return list;
    }

    public int getLayoutId();
}
