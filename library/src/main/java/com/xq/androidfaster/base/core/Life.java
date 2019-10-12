package com.xq.androidfaster.base.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;

public interface Life extends LifecycleObserver{

    public void create(Bundle savedInstanceState);

    public void visible();

    public void invisible();

    public void destroy();

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onSaveInstanceState(Bundle outState);

    //判断是否第一次可见
    public boolean isFirstVisible();

    //是否销毁并重建
    public boolean isRestoreState();

    //以下方法不建议使用
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default void onCreate(){

    }
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    default void onStart(){

    }
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    default void onResume(){

    }
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    default void onPause(){

    }
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    default void onStop(){

    }
    @Deprecated
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    default void onDestroy(){

    }
}
