package com.xq.androidfaster.bean.entity;

import android.os.Parcel;

import com.xq.androidfaster.bean.behavior.NumberTitleBehavior;

public class NumberTitleBean extends TitleBean implements NumberTitleBehavior{

    protected Number number;

    public NumberTitleBean() {
    }

    public NumberTitleBean(CharSequence title, Number number) {
        super(title);
        this.number = number;
    }

    public NumberTitleBean(CharSequence title, Number number,Object tag) {
        super(title,tag);
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberTitleBean{" +
                "number=" + number +
                ", title=" + title +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NumberTitleBean that = (NumberTitleBean) o;

        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    protected NumberTitleBean(Parcel in) {
        NumberTitleBehavior behavior = (NumberTitleBehavior) in.readSerializable();
        this.title = behavior.getTitle();
        this.tag = behavior.getTag();
        this.number = behavior.getNumber();
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
