package com.xq.androidfaster.bean.entity;

import android.os.Parcel;

import com.xq.androidfaster.bean.behavior.ContentTitleBehavior;

public class ContentTitleBean extends TitleBean implements ContentTitleBehavior {

    protected CharSequence content;

    public ContentTitleBean() {
    }

    public ContentTitleBean(CharSequence title, CharSequence content) {
        super(title);
        this.content = content;
    }

    public ContentTitleBean(CharSequence title, CharSequence content,Object tag) {
        super(title,tag);
        this.content = content;
    }

    @Override
    public String toString() {
        return "ContentTitleBean{" +
                "content=" + content +
                ", title=" + title +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ContentTitleBean that = (ContentTitleBean) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public CharSequence getContent() {
        return content;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }

    protected ContentTitleBean(Parcel in) {
        ContentTitleBean behavior = (ContentTitleBean) in.readSerializable();
        this.title = behavior.getTitle();
        this.tag = behavior.getTag();
        this.content = behavior.getContent();
    }

    public static final Creator<ContentTitleBean> CREATOR = new Creator<ContentTitleBean>() {
        @Override
        public ContentTitleBean createFromParcel(Parcel source) {
            return new ContentTitleBean(source);
        }

        @Override
        public ContentTitleBean[] newArray(int size) {
            return new ContentTitleBean[size];
        }
    };
}
