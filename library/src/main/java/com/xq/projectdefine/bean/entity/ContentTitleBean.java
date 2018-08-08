package com.xq.projectdefine.bean.entity;


import com.xq.projectdefine.bean.behavior.ContentTitleBehavior;



public class ContentTitleBean extends TitleBean implements ContentTitleBehavior {

    protected CharSequence content;

    public ContentTitleBean() {
    }

    public ContentTitleBean(CharSequence title, CharSequence content) {
        super(title);
        this.content = content;
    }

    @Override
    public String toString() {
        return "ContentTitleBean{" +
                "content='" + content + '\'' +
                '}';
    }

    public CharSequence getContent() {
        return content;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }

}
