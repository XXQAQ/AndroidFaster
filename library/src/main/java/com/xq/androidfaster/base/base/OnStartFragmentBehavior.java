package com.xq.androidfaster.base.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

public interface OnStartFragmentBehavior {

    public void onFragmentResult(int requestCode,int resultCode, Intent intent);

    default void startFragmentForResult(Fragment fragment, int containerId, ResultCallback callback) {
        startFragmentForResult(fragment,containerId,0,0,callback);
    }

    public void startFragmentForResult(Fragment fragment, int containerId,int enterAnim,int exitAnim, ResultCallback callback);

    default void startFragment(Fragment fragment,int containerId){
        startFragment(fragment,containerId,0,0);
    }

    public void startFragment(Fragment fragment,int containerId,int enterAnim,int exitAnim);

}
