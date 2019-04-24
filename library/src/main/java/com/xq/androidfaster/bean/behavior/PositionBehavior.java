package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public interface PositionBehavior extends Serializable, Parcelable{

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

    public int getPosition();

    default String getPositionRole(){
        return null;
    }

}
