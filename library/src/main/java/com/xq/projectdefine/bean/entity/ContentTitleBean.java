package com.xq.projectdefine.bean.entity;


import com.xq.projectdefine.bean.behavior.ContentTitleBehavior;



public class ContentTitleBean extends TitleBean implements ContentTitleBehavior {

    protected String content;

    public ContentTitleBean() {
    }

    public ContentTitleBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ContentTitleBean{" +
                "content='" + content + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
