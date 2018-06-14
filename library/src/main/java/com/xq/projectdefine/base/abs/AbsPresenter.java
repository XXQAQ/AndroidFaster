package com.xq.projectdefine.base.abs;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.life.PresenterLife;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface AbsPresenter<T extends AbsView> extends PresenterLife {

    public T getBindView();

    public Context getContext();

    public Fragment getAreFragment();

    public Activity getAreActivity();

    public void finishSelf();

}
