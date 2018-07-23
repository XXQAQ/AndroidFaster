package com.xq.projectdefine.bean.entity;



import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.io.Serializable;


//TitleBehavior的最简单实现类
public class TitleBean implements TitleBehavior ,Serializable{

    protected String title;
    protected Object tag;

    public TitleBean() {
    }

    public TitleBean(String title) {
        this.title = title;
    }

    public TitleBean(String title, Object tag) {
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
    public String getTitle() {
        return title;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

}
