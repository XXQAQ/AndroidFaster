package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.life.PresenterLife;
import com.xq.projectdefine.util.callback.ActivityResultCallback;

import java.util.List;


public interface IFasterBasePresenter<T extends IFasterBaseView> extends AbsPresenter<T>, PresenterLife{


}
