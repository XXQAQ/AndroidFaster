package com.xq.androidfaster.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.xq.androidfaster.bean.behavior.ContentTitleBehavior;

import java.io.Serializable;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (content instanceof Parcelable)
            dest.writeParcelable((Parcelable) content, flags);
        else    if (content instanceof Serializable)
            dest.writeSerializable((Serializable) content);
        else
            dest.writeString(content == null?null:content.toString());
    }

    protected ContentTitleBean(Parcel in) {
        super(in);
        if (content instanceof Parcelable)
            this.content = in.readParcelable(CharSequence.class.getClassLoader());
        else    if (content instanceof Serializable)
            this.content = (CharSequence) in.readSerializable();
        else
            this.content = in.readString();
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
