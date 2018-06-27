package com.xq.projectdefine.base.abs;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.xq.projectdefine.base.life.ViewLife;
import com.xq.projectdefine.util.tools.ImageUtils;


public interface AbsView<T extends AbsPresenter> extends ViewLife {

    //获取该View层对应P层
    public T getPresenter();

    //获取上下文
    public Context getContext();

    //获取根布局View
    public View getRootView();

    //获取对应FragmentManager，无需判断Activity或者Fragment下的不同使用情景
    default FragmentManager getCPFragmentManager() {
        if (getPresenter().getAreActivity() != null)
            return ((FragmentActivity)getPresenter().getAreActivity()).getSupportFragmentManager();
        else     if (getPresenter().getAreFragment() != null)
            return (getPresenter().getAreFragment()).getChildFragmentManager();
        return null;
    }

    //获取布局构造器
    default LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //findViewById
    default View findViewById(int id){
        return getRootView().findViewById(id);
    }

    //根布局截图
    default Bitmap getRootViewBitmap(){
        return ImageUtils.view2Bitmap(getRootView());
    }

}
