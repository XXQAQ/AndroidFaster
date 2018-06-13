package com.xq.projectdefine.bean.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.xq.projectdefine.bean.behavior.ContentTitleBehavior;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class ContentTitleBean extends TitleBean implements ContentTitleBehavior, Serializable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.content);
    }

    protected ContentTitleBean(Parcel in) {
        super(in);
        this.content = in.readString();
    }

    public static final Parcelable.Creator<ContentTitleBean> CREATOR = new Parcelable.Creator<ContentTitleBean>() {
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
