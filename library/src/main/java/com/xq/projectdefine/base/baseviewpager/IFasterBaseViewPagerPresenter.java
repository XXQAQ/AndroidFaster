package com.xq.projectdefine.base.baseviewpager;


import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.behavior.TitleBehavior;

import java.util.List;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IFasterBaseViewPagerPresenter<T extends IFasterBaseViewPagerView> extends AbsPresenter<T> {

    //设置title与fragment集合
    public abstract List<TitleBehavior> getFragmentsAndTitles();
}