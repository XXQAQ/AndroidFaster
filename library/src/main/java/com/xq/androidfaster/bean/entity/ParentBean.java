package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.xq.androidfaster.bean.behavior.IdBehavior;
import com.xq.androidfaster.bean.behavior.TagBehavior;
import java.io.Serializable;

public class ParentBean implements Serializable, Parcelable, TagBehavior , IdBehavior {

    protected Object tag;
    protected int id;
    protected String idRole;

    public ParentBean() {
    }

    public ParentBean(Object tag) {
        this.tag = tag;
    }

    public ParentBean(Object tag, int id) {
        this.tag = tag;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ParentBean{" +
                "tag=" + tag +
                ", id=" + id +
                ", idRole='" + idRole + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentBean that = (ParentBean) o;

        if (id != that.id) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        return idRole != null ? idRole.equals(that.idRole) : that.idRole == null;
    }

    @Override
    public int hashCode() {
        int result = tag != null ? tag.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        return result;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
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
        dest.writeInt(id);
        dest.writeString(idRole);
    }

    protected ParentBean(Parcel in) {
        if (tag instanceof Parcelable)
            this.tag = in.readParcelable(Object.class.getClassLoader());
        else    if (tag instanceof Serializable)
            this.tag = in.readSerializable();
        this.id = in.readInt();
        this.idRole = in.readString();
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
