package com.xq.projectdefine.callback;

import android.content.Intent;

public interface ActivityResultCallback {

    public void onSuccess(Intent data);

    default void onCancel(){

    }

}
