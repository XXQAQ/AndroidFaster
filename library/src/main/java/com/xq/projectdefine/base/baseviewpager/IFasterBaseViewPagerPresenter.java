package com.xq.projectdefine.base.baseviewpager;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.util.List;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IFasterBaseViewPagerPresenter<T extends IFasterBaseViewPagerView> extends AbsPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //设置title与fragment集合
    public abstract List<TitleBehavior> getFragmentsAndTitles();
}