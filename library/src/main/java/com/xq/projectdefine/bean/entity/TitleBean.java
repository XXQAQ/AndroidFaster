package com.xq.projectdefine.bean.entity;



import com.xq.projectdefine.bean.behavior.TitleBehavior;

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
                "title='" + title + '\'' +
                '}';
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
