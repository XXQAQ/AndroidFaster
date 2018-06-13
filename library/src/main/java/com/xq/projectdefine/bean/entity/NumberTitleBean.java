package com.xq.projectdefine.bean.entity;

import android.os.Parcel;


import com.xq.projectdefine.bean.behavior.NumberTitleBehavior;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class NumberTitleBean extends TitleBean implements NumberTitleBehavior, Serializable{

    protected Number number;

    public NumberTitleBean() {
    }

    public NumberTitleBean(String title, Number number) {
        this.title = title;
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberTitleBean{" +
                "title='" + title + '\'' +
                ", number=" + number +
                '}';
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
        dest.writeSerializable(this.number);
    }

    protected NumberTitleBean(Parcel in) {
        super(in);
        this.number = (Number) in.readSerializable();
    }

    public static final Creator<NumberTitleBean> CREATOR = new Creator<NumberTitleBean>() {
        @Override
        public NumberTitleBean createFromParcel(Parcel source) {
            return new NumberTitleBean(source);
        }

        @Override
        public NumberTitleBean[] newArray(int size) {
            return new NumberTitleBean[size];
        }
    };
}
