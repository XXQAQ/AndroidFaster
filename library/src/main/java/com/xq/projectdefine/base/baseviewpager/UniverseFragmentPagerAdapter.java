package com.xq.projectdefine.base.baseviewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

//万能fragmentPagerAdapter
public class UniverseFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment;
    private List<String> list_title;

    public UniverseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_title = list_title;
    }

    public UniverseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        this.list_fragment = list_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (list_title == null)
            return super.getPageTitle(position);
        else
            return list_title.get(position);
    }
}
