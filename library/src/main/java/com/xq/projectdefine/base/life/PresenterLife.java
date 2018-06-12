package com.xq.projectdefine.base.life;

import android.content.Intent;

public interface PresenterLife extends BaseLife {

    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
