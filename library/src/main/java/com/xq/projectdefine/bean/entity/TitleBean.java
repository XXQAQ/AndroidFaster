package com.xq.projectdefine.bean.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.io.Serializable;

/**
 * Created by xq on 2017/7/10.
 */

//TitleBehavior的最简单实现类
public class TitleBean implements TitleBehavior,Serializable,Parcelable {

    protected String title;
    protected Object tag;

    public TitleBean() {
    }

    public TitleBean(String title) {
        this.title = title;
    }

    public TitleBean(String title, Object tag) {
        this.title = title;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        if (this.tag instanceof Parcelable)
            dest.writeParcelable((Parcelable) this.tag, flags);
    }

    protected TitleBean(Parcel in) {
        this.title = in.readString();
        if (this.tag instanceof Parcelable)
            this.tag = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<TitleBean> CREATOR = new Creator<TitleBean>() {
        @Override
        public TitleBean createFromParcel(Parcel source) {
            return new TitleBean(source);
        }

        @Override
        public TitleBean[] newArray(int size) {
            return new TitleBean[size];
        }
    };
}
