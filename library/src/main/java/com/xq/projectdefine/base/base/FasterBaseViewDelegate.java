package com.xq.projectdefine.base.base;

import com.xq.projectdefine.base.life.ViewLife;

public abstract class FasterBaseViewDelegate implements ViewLife{

    private IFasterBaseView view;

    public FasterBaseViewDelegate(IFasterBaseView view) {
        this.view = view;
        view.getLifes().add(this);
    }
}
