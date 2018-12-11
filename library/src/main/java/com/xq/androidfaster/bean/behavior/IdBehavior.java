package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public interface IdBehavior extends Serializable, Parcelable {

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

    public int getId();

    public int getForeignId();

    default Object getTag(){
        return null;
    }

}
