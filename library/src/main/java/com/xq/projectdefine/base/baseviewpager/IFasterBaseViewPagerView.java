package com.xq.projectdefine.base.baseviewpager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IFasterBaseViewPagerView<T extends IFasterBaseViewPagerPresenter> extends AbsView<T> {

    //初始化Viewpager、TabLayout等
    default void initFragmentsAndTitles(List<TitleBehavior> list) {

        List<String> list_title = new LinkedList<>();
        List<Fragment> list_fragment = new LinkedList<>();

        if (list != null && getViewPagerBuilder().tl != null)
        {
            for (int i=0;i<list.size();i++)
            {
                getViewPagerBuilder().tl.addTab(getViewPagerBuilder().tl.newTab());
                list_title.add(list.get(i).getTitle());
                list_fragment.add((Fragment) list.get(i).getTag());
            }
            getViewPagerBuilder().tl.setupWithViewPager(getViewPagerBuilder().vp);
        }

        getViewPagerBuilder().vp.setAdapter(new FragmentPagerAdapter(getCPFragmentManager(),list_fragment,list_title));

    }

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

        if (getRootView() instanceof ViewPager)   //如果根布局是ViewPager，则直接将根布局转化为vp
            getViewPagerBuilder().vp = (ViewPager) getRootView();
        else
            getViewPagerBuilder().vp = (ViewPager) getRootView().findViewById(getContext().getResources().getIdentifier("vp", "id", getContext().getPackageName()));
        getViewPagerBuilder().tl = (TabLayout) getRootView().findViewById(getContext().getResources().getIdentifier("tl", "id", getContext().getPackageName()));

        if (getViewPagerBuilder().tl != null)
        {
            getViewPagerBuilder().tl.setTabTextColors(getTabTextNormalColor(),getTabTextSelectColor());
            getViewPagerBuilder().tl.setBackgroundColor(getTabBackgroundColor());
        }

        initFragmentsAndTitles(getPresenter().getFragmentsAndTitles());

    }

    default void refreshViewPager(){
        getViewPagerBuilder().vp.getAdapter().notifyDataSetChanged();
    }

    public int getTabTextNormalColor();

    public int getTabTextSelectColor();

    public int getTabBackgroundColor();

    public ViewPagerBuilder getViewPagerBuilder();

    public static class ViewPagerBuilder{
        public ViewPager vp;
        public TabLayout tl;
    }

}