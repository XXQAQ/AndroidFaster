package com.xq.projectdefine.bean.entity;



import com.xq.projectdefine.bean.behavior.NumberTitleBehavior;


public class NumberTitleBean extends TitleBean implements NumberTitleBehavior{

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

}
