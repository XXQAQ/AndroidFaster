package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.xq.androidfaster.bean.behavior.TagBehavior;
import java.io.Serializable;

public class ParentBean implements Serializable, Parcelable, TagBehavior {

    protected Object tag;

    public ParentBean() {
    }

    @Override
    public String toString() {
        return "ParentBean{" +
                "tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentBean that = (ParentBean) o;

        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @Override
    public Object getTag() {
        return tag;
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
        if (tag instanceof Parcelable)
            dest.writeParcelable((Parcelable) tag, flags);
        else    if (tag instanceof Serializable)
            dest.writeSerializable((Serializable) tag);
    }

    protected ParentBean(Parcel in) {
        if (tag instanceof Parcelable)
            this.tag = in.readParcelable(Object.class.getClassLoader());
        else    if (tag instanceof Serializable)
            this.tag = in.readSerializable();
    }

    public static final Creator<ParentBean> CREATOR = new Creator<ParentBean>() {
        @Override
        public ParentBean createFromParcel(Parcel in) {
            return new ParentBean(in);
        }

        @Override
        public ParentBean[] newArray(int size) {
            return new ParentBean[size];
        }
    };

}
