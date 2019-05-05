package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.xq.androidfaster.bean.behavior.TitleBehavior;
import java.io.Serializable;

public class TitleBean extends ParentBean implements TitleBehavior {

    protected CharSequence title;
    protected String titleRole;

    public TitleBean() {
    }

    public TitleBean(CharSequence title) {
        this.title = title;
    }

    public TitleBean(int id,CharSequence title) {
        super(id);
        this.title = title;
    }

    public TitleBean(int id,CharSequence title, Object tag) {
        super(id,tag);
        this.title = title;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "title=" + title +
                ", titleRole='" + titleRole + '\'' +
                ", tag=" + tag +
                ", id=" + id +
                ", idRole='" + idRole + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TitleBean titleBean = (TitleBean) o;

        if (title != null ? !title.equals(titleBean.title) : titleBean.title != null) return false;
        return titleRole != null ? titleRole.equals(titleBean.titleRole) : titleBean.titleRole == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (titleRole != null ? titleRole.hashCode() : 0);
        return result;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    public String getTitleRole() {
        return titleRole;
    }

    public void setTitleRole(String titleRole) {
        this.titleRole = titleRole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (title instanceof Parcelable)
            dest.writeParcelable((Parcelable) title, flags);
        else    if (title instanceof Serializable)
            dest.writeSerializable((Serializable) title);
        else
            dest.writeString(title == null?null:title.toString());
        dest.writeString(titleRole);
    }

    protected TitleBean(Parcel in) {
        super(in);
        if (title instanceof Parcelable)
            this.title = in.readParcelable(CharSequence.class.getClassLoader());
        else    if (title instanceof Serializable)
            this.title = (CharSequence) in.readSerializable();
        else
            this.title = in.readString();
        this.titleRole = in.readString();
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
