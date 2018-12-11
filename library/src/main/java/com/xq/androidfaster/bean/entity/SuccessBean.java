package com.xq.androidfaster.bean.entity;

import android.os.Parcel;

import com.xq.androidfaster.bean.behavior.SuccessBehavior;

public class SuccessBean implements SuccessBehavior{

    protected boolean isSuccess;
    protected Object tag;

    public SuccessBean() {
    }

    public SuccessBean(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public SuccessBean(boolean isSuccess, Object tag) {
        this.isSuccess = isSuccess;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "SuccessBean{" +
                "isSuccess=" + isSuccess +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuccessBean that = (SuccessBean) o;

        if (isSuccess != that.isSuccess) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }

    @Override
    public int hashCode() {
        int result = (isSuccess ? 1 : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
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
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this);
    }

    protected SuccessBean(Parcel in) {
        SuccessBehavior behavior = (SuccessBehavior) in.readSerializable();
        this.isSuccess = behavior.isSuccess();
        this.tag = behavior.getTag();
    }

    public static final Creator<SuccessBean> CREATOR = new Creator<SuccessBean>() {
        @Override
        public SuccessBean createFromParcel(Parcel source) {
            return new SuccessBean(source);
        }

        @Override
        public SuccessBean[] newArray(int size) {
            return new SuccessBean[size];
        }
    };
}
