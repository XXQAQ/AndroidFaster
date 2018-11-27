package com.xq.androidfaster.base.life;

import android.os.Bundle;

public interface CommonLife {

    public void afterOnCreate(Bundle savedInstanceState);

    public void onResume();

    public void onPause();

    public void onDestroy();

}
