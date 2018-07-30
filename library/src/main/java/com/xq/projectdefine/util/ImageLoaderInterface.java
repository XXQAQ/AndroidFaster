package com.xq.projectdefine.util;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoaderInterface {

    default void loadImage(Context context, ImageView view, String url, Object... object) {
        loadImage(context,view,url,0,object);
    }

    public void loadImage(Context context, ImageView view, String url,int placeHolder, Object... object);

}
