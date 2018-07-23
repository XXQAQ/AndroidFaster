package com.xq.projectdefine.callback;

import com.xq.projectdefine.bean.behavior.SuccessBehavior;

import java.util.Objects;

public interface SuccessCallback {

    public void onCallback(SuccessBehavior behavior, Object... objects);

}
