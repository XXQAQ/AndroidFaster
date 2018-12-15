package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.xq.androidfaster.bean.behavior.TitleBehavior;

import java.io.Serializable;

//TitleBehavior的最简单实现类
public class TitleBean implements TitleBehavior{

    protected CharSequence title;
    protected Object tag;

    public TitleBean() {
    }

    public TitleBean(CharSequence title) {
        this.title = title;
    }

    public TitleBean(CharSequence title, Object tag) {
        this.title = title;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "title=" + title +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleBean titleBean = (TitleBean) o;

        if (title != null ? !title.equals(titleBean.title) : titleBean.title != null) return false;
        return tag != null ? tag.equals(titleBean.tag) : titleBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (title instanceof Parcelable)
            dest.writeParcelable((Parcelable) title, flags);
        else    if (title instanceof Serializable)
            dest.writeSerializable((Serializable) title);
        if (tag instanceof Parcelable)
            dest.writeParcelable((Parcelable) tag, flags);
        else    if (tag instanceof Serializable)
            dest.writeSerializable((Serializable) tag);
    }

    protected TitleBean(Parcel in) {
        if (title instanceof Parcelable)
            this.title = in.readParcelable(CharSequence.class.getClassLoader());
        else    if (title instanceof Serializable)
            this.title = (CharSequence) in.readSerializable();
        if (tag instanceof Parcelable)
            this.tag = in.readParcelable(Object.class.getClassLoader());
        else    if (tag instanceof Serializable)
            this.tag = in.readSerializable();
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
