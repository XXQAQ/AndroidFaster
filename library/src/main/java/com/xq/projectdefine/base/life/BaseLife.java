package com.xq.projectdefine.base.life;

import android.os.Bundle;

public interface BaseLife {

    public void afterOnCreate(Bundle savedInstanceState);

    public void onResume();

    public void onPause();

    public void onDestroy();

}
