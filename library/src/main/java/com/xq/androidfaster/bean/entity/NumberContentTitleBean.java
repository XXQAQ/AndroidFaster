package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.xq.androidfaster.bean.behavior.NumberContentTitleBehavior;

import java.io.Serializable;

public class NumberContentTitleBean extends TitleBean implements NumberContentTitleBehavior {

    protected CharSequence content;
    protected Number number;

    public NumberContentTitleBean() {
    }

    public NumberContentTitleBean(CharSequence title) {
        super(title);
    }

    public NumberContentTitleBean(CharSequence title, CharSequence content, Number number) {
        super(title);
        this.content = content;
        this.number = number;
    }

    public NumberContentTitleBean(CharSequence title, CharSequence content, Number number,Object tag) {
        super(title,tag);
        this.content = content;
        this.number = number;
    }

    public NumberContentTitleBean(CharSequence title, CharSequence content, Number number,Object tag,int id) {
        super(title,tag,id);
        this.content = content;
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberContentTitleBean{" +
                "content=" + content +
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

        NumberContentTitleBean that = (NumberContentTitleBean) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public CharSequence getContent() {
        return content;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (content instanceof Parcelable)
            dest.writeParcelable((Parcelable) content, flags);
        else    if (content instanceof Serializable)
            dest.writeSerializable((Serializable) content);
        else
            dest.writeString(content == null?null:content.toString());
        dest.writeSerializable(this.number);
    }

    protected NumberContentTitleBean(Parcel in) {
        super(in);
        if (content instanceof Parcelable)
            this.content = in.readParcelable(CharSequence.class.getClassLoader());
        else    if (content instanceof Serializable)
            this.content = (CharSequence) in.readSerializable();
        else
            this.content = in.readString();
        this.number = (Number) in.readSerializable();
    }

    public static final Creator<NumberContentTitleBean> CREATOR = new Creator<NumberContentTitleBean>() {
        @Override
        public NumberContentTitleBean createFromParcel(Parcel source) {
            return new NumberContentTitleBean(source);
        }

        @Override
        public NumberContentTitleBean[] newArray(int size) {
            return new NumberContentTitleBean[size];
        }
    };
}
