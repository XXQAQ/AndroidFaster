package com.xq.projectdefine.util;

import android.content.Context;
import android.util.Size;
import android.widget.ImageView;

public class AbsImageLoader{

    protected static Loader loader;

    protected AbsImageLoader(){

    }

    public static void loadImage(Context context, ImageView view, String url, Object... objects) {
        loader.loadImage(context,view,url,objects);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
        loader.loadImage(context,view,url,placeHolder,objects);
    }


    public static abstract class Loader{

        public void loadImage(Context context, ImageView view, String url, Object... objects) {
            loadImage(context,view,url,0,objects);
        }

        public abstract void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects);

    }

}
