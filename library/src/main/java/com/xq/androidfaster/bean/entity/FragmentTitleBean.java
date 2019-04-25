package com.xq.androidfaster.bean.entity;

import android.support.v4.app.Fragment;

import com.xq.androidfaster.bean.behavior.FragmentTitleBehavior;

public class FragmentTitleBean extends TitleBean implements FragmentTitleBehavior {

    protected Fragment fragment;

    public FragmentTitleBean() {
    }

    public FragmentTitleBean(CharSequence title) {
        super(title);
    }

    public FragmentTitleBean(CharSequence title,Fragment fragment) {
        super(title);
        this.fragment = fragment;
    }

    public FragmentTitleBean(CharSequence title,Fragment fragment,Object tag) {
        super(title,tag);
        this.fragment = fragment;
    }

    public FragmentTitleBean(CharSequence title,Fragment fragment,Object tag,String titleRole) {
        super(title,tag,titleRole);
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "FragmentTitleBean{" +
                "fragment=" + fragment +
                ", title=" + title +
                ", titleRole='" + titleRole + '\'' +
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
