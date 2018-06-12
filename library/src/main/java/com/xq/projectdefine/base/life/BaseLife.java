package com.xq.projectdefine.base.life;

import android.os.Bundle;

public interface BaseLife {

    default void afterOnCreate(Bundle savedInstanceState) {

    }

    default void onResume() {

    }

    default void onPause() {

    }

    default void onDestroy() {

    }

    default void onSaveInstanceState(Bundle outState) {

    }

}
