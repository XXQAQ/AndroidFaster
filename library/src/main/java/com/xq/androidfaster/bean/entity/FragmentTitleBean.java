package com.xq.androidfaster.bean.entity;

import android.support.v4.app.Fragment;

public class FragmentTitleBean extends TitleBean {

    protected Fragment fragment;

    public FragmentTitleBean() {
    }

    public FragmentTitleBean(CharSequence title) {
        super(title);
    }

    public FragmentTitleBean(CharSequence title, Object tag) {
        super(title, tag);
    }

    public FragmentTitleBean(CharSequence title, Object tag, Fragment fragment) {
        super(title, tag);
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "FragmentTitleBean{" +
                "fragment=" + fragment +
                ", title=" + title +
                ", tag=" + tag +
                '}';
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
