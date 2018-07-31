package com.xq.projectdefine.base.base;

import com.xq.projectdefine.base.life.PresenterLife;

public abstract class FasterBasePresenterDelegate implements PresenterLife{

    private IFasterBasePresenter presenter;

    public FasterBasePresenterDelegate(IFasterBasePresenter presenter) {
        this.presenter = presenter;
        presenter.getLifes().add(this);
    }

}
