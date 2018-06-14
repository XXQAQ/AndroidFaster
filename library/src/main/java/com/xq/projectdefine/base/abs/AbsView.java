package com.xq.projectdefine.base.abs;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.xq.projectdefine.base.life.ViewLife;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface AbsView<T extends AbsPresenter> extends ViewLife {

    public T getPresenter();

    public Context getContext();

    public View getRootView();

    default FragmentManager getCPFragmentManager() {
        if (getPresenter().getAreActivity() != null)
            return ((FragmentActivity)getPresenter().getAreActivity()).getSupportFragmentManager();
        else     if (getPresenter().getAreFragment() != null)
            return (getPresenter().getAreFragment()).getChildFragmentManager();
        return null;
    }

    default LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    default View findViewById(int id){
        return getRootView().findViewById(id);
    }

}
