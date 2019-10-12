package com.xq.androidfaster.base.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

public interface OnStartFragmentBehavior {

    public void onFragmentResult(int requestCode,int resultCode, Intent intent);

    public void startFragmentForResult(Fragment fragment, int containerId, ResultCallback callback);

}
