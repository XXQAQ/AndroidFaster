package com.xq.androidfaster.bean.entity;

import com.xq.androidfaster.bean.behavior.TitleBehavior;
import java.io.Serializable;

//TitleBehavior的最简单实现类
public class TitleBean implements TitleBehavior ,Serializable{

    protected CharSequence title;
    protected Object tag;

    public TitleBean() {
    }

    public TitleBean(CharSequence title) {
        this.title = title;
    }

    public TitleBean(CharSequence title, Object tag) {
        this.title = title;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "title=" + title +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleBean titleBean = (TitleBean) o;

        if (title != null ? !title.equals(titleBean.title) : titleBean.title != null) return false;
        return tag != null ? tag.equals(titleBean.tag) : titleBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

}
