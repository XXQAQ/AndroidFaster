package com.xq.projectdefine.behavior;


import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xq on 2017/7/5.
 */

//该接口专用于列表选择，实现该接口可以使您的对象直接显示在列表中
public interface TitleBehavior extends Serializable,Parcelable {

    //返回标题
    public String getTitle();

    //附加信息
    public Object getTag();

}
