package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//该接口专注于标题显示，实现该接口可以让你的对象以一个标题的形式呈现
public interface TitleBehavior extends Serializable,Parcelable{

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

    //返回标题
    public CharSequence getTitle();

    //上述内容在属性值中扮演的角色，不需要处理判断逻辑可直接返回null
    default String getTitleRole(){
        return null;
    }

}
