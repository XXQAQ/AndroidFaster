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

    //上述内容在属性值中扮演的角色，不需要处理判断逻辑可直接返回null
    default String getSuccessRole(){
        return null;
    }
}
