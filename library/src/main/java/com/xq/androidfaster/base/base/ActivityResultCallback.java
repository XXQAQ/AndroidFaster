package com.xq.androidfaster.base.base;

import android.content.Intent;

public interface ActivityResultCallback {

    public void onSuccess(Intent data);

    default void onCancel(){

    }
}
