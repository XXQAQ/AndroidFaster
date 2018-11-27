package com.xq.androidfaster.base.life;

import android.content.Intent;

public interface PresenterLife extends CommonLife {

    public void onActivityResult(int requestCode, int resultCode, Intent data);
}
