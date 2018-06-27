package com.xq.projectdefine.bean.entity;


import android.os.Parcel;

import com.xq.projectdefine.bean.behavior.NumberContenTitleBehavior;

import java.io.Serializable;


public class NumberContentTitleBean extends TitleBean implements Serializable,NumberContenTitleBehavior {

    protected String content;
    protected Number number;

    public NumberContentTitleBean() {
    }

    public NumberContentTitleBean(String title, String content, Number number) {
        super(title);
        this.content = content;
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberContentTitleBean{" +
                "content='" + content + '\'' +
                ", number=" + number +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
        dest.writeString(this.content);
        dest.writeSerializable(this.number);
    }

    protected NumberContentTitleBean(Parcel in) {
        super(in);
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
