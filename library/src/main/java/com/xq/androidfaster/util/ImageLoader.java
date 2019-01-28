package com.xq.androidfaster.util;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoader {

    private static Loader loader;

    public static void loadImage(Context context, ImageView view, String url, Object... objects) {
        loader.loadImage(context,view,url,objects);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
        loader.loadImage(context,view,url,placeHolder,objects);
    }

    public static void setLoader(Loader loader){
        ImageLoader.loader = loader;
    }

    public static abstract class Loader{

        public void loadImage(Context context, ImageView view, String url, Object... objects) {
            loadImage(context,view,url,0,objects);
        }

        public abstract void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects);

    }

}
