package com.xq.androidfaster.base.base;

import android.content.Intent;

public interface ResultCallback {

    public void onSuccess(Intent data);

    default void onCancel(){

    }
}
