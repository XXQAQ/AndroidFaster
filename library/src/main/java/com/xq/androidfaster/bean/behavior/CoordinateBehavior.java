package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public interface CoordinateBehavior extends Serializable, Parcelable {

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

    public void getX();

    public void getY();

    public void getZ();

    default String getCoordinateRole(){
        return null;
    }

}
