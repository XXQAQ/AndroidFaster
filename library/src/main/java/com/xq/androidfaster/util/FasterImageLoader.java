package com.xq.androidfaster.util;

import android.content.Context;
import android.widget.ImageView;

public class FasterImageLoader {

    private static Loader loader;

    protected static void loadImage(Context context, ImageView view, String url, Object... objects) {
        loader.loadImage(context,view,url,objects);
    }

    protected static void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
        loader.loadImage(context,view,url,placeHolder,objects);
    }

    protected static abstract class Loader{

        protected void loadImage(Context context, ImageView view, String url, Object... objects) {
            loadImage(context,view,url,0,objects);
        }

        protected abstract void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects);

    }

}
