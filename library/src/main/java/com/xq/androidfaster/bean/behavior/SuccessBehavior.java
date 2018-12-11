package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//实现该接口可以使您的对象描述成功或失败的能力
public interface SuccessBehavior extends Serializable,Parcelable {

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

    //返回是否成功
    public boolean isSuccess();

    //附加信息
    default Object getTag(){
        return null;
    }
}
