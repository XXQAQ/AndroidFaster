package com.xq.projectdefine.util.callback;

import android.content.Intent;

public interface ActivityResultCallback {

    public void onSuccess(Intent data);

    default void onCancel(){

    }

}
