package com.xq.projectdefine.bean.entity;



import com.xq.projectdefine.bean.behavior.NumberContentTitleBehavior;


public class NumberContentTitleBean extends TitleBean implements NumberContentTitleBehavior {

    protected CharSequence content;
    protected Number number;

    public NumberContentTitleBean() {
    }

    public NumberContentTitleBean(CharSequence title, CharSequence content, Number number) {
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

}
