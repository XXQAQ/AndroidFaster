package com.xq.androidfaster.bean.entity;

import android.os.Parcel;

import com.xq.androidfaster.bean.behavior.BaseUIBehavior;

public class BaseUIBean extends NumberContentTitleBean implements BaseUIBehavior {

    private int imageRes;
    private String imageUrl;
    private String imageRole;

    public BaseUIBean() {
    }

    public BaseUIBean(CharSequence title) {
        super(title);
    }

    public BaseUIBean(CharSequence title, CharSequence content, Number number) {
        super(title, content, number);
    }

    public BaseUIBean(int imageRes, CharSequence title) {
        super(title);
        this.imageRes = imageRes;
    }

    public BaseUIBean(int imageRes, CharSequence title, CharSequence content, Number number) {
        super(title, content, number);
        this.imageRes = imageRes;
    }

    public BaseUIBean(int imageRes,CharSequence title, CharSequence content, Number number, Object tag, int id) {
        super(title, content, number, tag, id);
        this.imageRes = imageRes;
    }

    public BaseUIBean(String imageUrl,CharSequence title) {
        super(title);
        this.imageUrl = imageUrl;
    }

    public BaseUIBean(String imageUrl,CharSequence title, CharSequence content, Number number) {
        super(title, content, number);
        this.imageUrl = imageUrl;
    }

    public BaseUIBean(String imageUrl,CharSequence title, CharSequence content, Number number, Object tag, int id) {
        super(title, content, number, tag, id);
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BaseUIBean{" +
                "imageRes=" + imageRes +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageRole='" + imageRole + '\'' +
                ", content=" + content +
                ", number=" + number +
                ", title=" + title +
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

        BaseUIBean that = (BaseUIBean) o;

        if (imageRes != that.imageRes) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        return imageRole != null ? imageRole.equals(that.imageRole) : that.imageRole == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + imageRes;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (imageRole != null ? imageRole.hashCode() : 0);
        return result;
    }

    @Override
    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImageRole() {
        return imageRole;
    }

    public void setImageRole(String imageRole) {
        this.imageRole = imageRole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.imageRes);
        dest.writeString(this.imageUrl);
        dest.writeString(this.imageRole);
    }

    protected BaseUIBean(Parcel in) {
        super(in);
        this.imageRes = in.readInt();
        this.imageUrl = in.readString();
        this.imageRole = in.readString();
    }

    public static final Creator<BaseUIBean> CREATOR = new Creator<BaseUIBean>() {
        @Override
        public BaseUIBean createFromParcel(Parcel source) {
            return new BaseUIBean(source);
        }

        @Override
        public BaseUIBean[] newArray(int size) {
            return new BaseUIBean[size];
        }
    };
}
