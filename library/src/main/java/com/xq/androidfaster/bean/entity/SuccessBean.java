package com.xq.androidfaster.bean.entity;

import android.os.Parcel;

import com.xq.androidfaster.bean.behavior.SuccessBehavior;

public class SuccessBean extends ParentBean implements SuccessBehavior{

    protected boolean isSuccess;
    protected String successRole;

    public SuccessBean() {
    }

    public SuccessBean(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public SuccessBean(int id,boolean isSuccess) {
        super(id);
        this.isSuccess = isSuccess;
    }

    public SuccessBean(int id,boolean isSuccess, Object tag) {
        super(id,tag);
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "SuccessBean{" +
                "isSuccess=" + isSuccess +
                ", successRole='" + successRole + '\'' +
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

        SuccessBean that = (SuccessBean) o;

        if (isSuccess != that.isSuccess) return false;
        return successRole != null ? successRole.equals(that.successRole) : that.successRole == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isSuccess ? 1 : 0);
        result = 31 * result + (successRole != null ? successRole.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String getSuccessRole() {
        return successRole;
    }

    public void setSuccessRole(String successRole) {
        this.successRole = successRole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.isSuccess ? (byte) 1 : (byte) 0);
        dest.writeString(this.successRole);
    }

    protected SuccessBean(Parcel in) {
        super(in);
        this.isSuccess = in.readByte() != 0;
        this.successRole = in.readString();
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
