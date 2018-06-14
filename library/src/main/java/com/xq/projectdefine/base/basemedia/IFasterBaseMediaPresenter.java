package com.xq.projectdefine.base.basemedia;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsView;

import java.io.File;
import java.util.List;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IFasterBaseMediaPresenter<T extends AbsView> extends AbsPresenter<T> {

    public static final int REQUEST_CODE_PHOTOS = 1;
    public static final int REQUEST_CODE_CUT = 2;
    public static final int REQUEST_CODE_CAMERA= 3;
    public static final int REQUEST_CODE_FILE= 4;

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    default void getPhotos(int what, int number) {
        getPhotos(what,number,0,0);
    }

    public void getPhotos(int what, int number, int width, int height);

    public void getCamera(int what);

    default void getFile(int what) {
        getMediaBuilder().what = what;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (getAreActivity() != null)
            getAreActivity().startActivityForResult(intent, REQUEST_CODE_FILE);
        else    if (getAreFragment() != null)
            getAreFragment().startActivityForResult(intent,REQUEST_CODE_FILE);
    }

    //以下所有what均为标记
    //接收到图片后调用
    public abstract void onReceivePhotos(List<File> list_file, int what);

    //接收到一个录像后调用
    public abstract void onReceiveCamera(File file, int what);

    //接收到一个文件后调用
    public abstract void onReceiveFile(File file, int what);

    public MediaBuilder getMediaBuilder();

    public static class MediaBuilder{
        public int what;
        public int width, height;
    }

}