package com.xq.androidfaster.bean.behavior;

import android.os.Parcel;

public interface BaseUIBehavior extends ResBehavior,NumberContentTitleBehavior{

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {

    }

}
