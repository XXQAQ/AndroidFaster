package com.xq.androidfaster.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoader {

    private static Loader loader;

    public static void loadImage(Context context, String url,ImageView view) {
        loader.loadImage(context,url,view);
    }

    public static void loadImage(Context context, int placeHolder, String url, ImageView view) {
        loader.loadImage(context,placeHolder,url,view);
    }

    public static void loadImage(Context context, String url, BitmapTarget target){
        loader.loadImage(context,url,target);
    }

    public static void loadImage(Context context, int placeHolder, String url, BitmapTarget target){
        loader.loadImage(context,placeHolder,url,target);
    }

    public static void setLoader(Loader loader){
        ImageLoader.loader = loader;
    }

    public static abstract class Loader{

        public void loadImage(Context context, String url,ImageView view) {
            loadImage(context,0,url,view);
        }

        public abstract void loadImage(Context context, int placeHolder, String url, ImageView view);

        public void loadImage(Context context, String url, BitmapTarget target) {
            loadImage(context,0,url,target);
        }

        public abstract void loadImage(Context context, int placeHolder, String url, BitmapTarget target);

    }

    public static interface BitmapTarget{

        public void onReceiveBitmap(Bitmap bitmap);

    }

}
