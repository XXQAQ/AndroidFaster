package com.xq.androidfaster.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoader {

    private static Loader loader;

    public static void loadImage(Context context, String url,ImageView view,Object... objects) {
        loader.loadImage(context,url,view,objects);
    }

    public static void loadImage(Context context, int placeHolder, String url, ImageView view, Object... objects) {
        loader.loadImage(context,placeHolder,url,view,objects);
    }

    public static void loadImage(Context context, String url, BitmapTarget target, Object... objects){
        loader.loadImage(context,url,target,objects);
    }

    public static void loadImage(Context context, int placeHolder, String url, BitmapTarget target, Object... objects){
        loader.loadImage(context,placeHolder,url,target,objects);
    }

    public static void setLoader(Loader loader){
        ImageLoader.loader = loader;
    }

    public static abstract class Loader{

        public void loadImage(Context context, String url,ImageView view,Object... objects) {
            loadImage(context,0,url,view,objects);
        }

        public abstract void loadImage(Context context, int placeHolder, String url, ImageView view, Object... objects);

        public void loadImage(Context context, String url, BitmapTarget target, Object... objects) {
            loadImage(context,0,url,target,objects);
        }

        public abstract void loadImage(Context context, int placeHolder, String url, BitmapTarget target, Object... objects);

    }

    public static interface BitmapTarget{

        public void onReceiveBitmap(Bitmap bitmap);

    }

}
