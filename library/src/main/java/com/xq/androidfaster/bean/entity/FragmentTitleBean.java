package com.xq.androidfaster.bean.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import com.xq.androidfaster.bean.behavior.FragmentTitleBehavior;
import java.lang.reflect.Constructor;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(fragment.getClass().getName());
        dest.writeBundle(fragment.getArguments());
    }

    protected FragmentTitleBean(Parcel in) {
        super(in);
        String fragmentName = in.readString();
        Bundle bundle = in.readBundle();
        try {
            Constructor constructor = Class.forName(fragmentName).getDeclaredConstructor();
            constructor.setAccessible(true);
            Fragment fragment = (Fragment) constructor.newInstance();
            fragment.setArguments(bundle);
            this.fragment = fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Creator<FragmentTitleBean> CREATOR = new Creator<FragmentTitleBean>() {
        @Override
        public FragmentTitleBean createFromParcel(Parcel source) {
            return new FragmentTitleBean(source);
        }

        @Override
        public FragmentTitleBean[] newArray(int size) {
            return new FragmentTitleBean[size];
        }
    };
}
