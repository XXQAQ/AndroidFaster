package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.xq.androidfaster.bean.behavior.ListBehavior;
import java.util.List;

public class ListBean<T> implements ListBehavior<T> {

    protected List<T> list;
    protected Object tag;

    public ListBean() {
    }

    public ListBean(List<T> list) {
        this.list = list;
    }

    public ListBean(List<T> list, Object tag) {
        this.list = list;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "list=" + list +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListBean<?> listBean = (ListBean<?>) o;

        if (list != null ? !list.equals(listBean.list) : listBean.list != null) return false;
        return tag != null ? tag.equals(listBean.tag) : listBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
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
        dest.writeSerializable(this);
    }

    protected ListBean(Parcel in) {
        ListBehavior behavior = (ListBehavior) in.readSerializable();
        this.list = behavior.getList();
        this.tag = behavior.getTag();
    }

    public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
        @Override
        public ListBean createFromParcel(Parcel source) {
            return new ListBean(source);
        }

        @Override
        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };
}
