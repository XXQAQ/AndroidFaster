package com.xq.projectdefine.base.life;

import android.content.Intent;

public interface PresenterLife extends CommonLife {

    public void onActivityResult(int requestCode, int resultCode, Intent data);
}
