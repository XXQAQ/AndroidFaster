package com.xq.projectdefine.bean.behavior;


import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xq on 2017/7/5.
 */

//该接口专注于标题显示，实现该接口可以让你的对象以一个标题的形式呈现
public interface TitleBehavior extends Serializable,Parcelable {

    //返回标题
    public String getTitle();

    //附加信息
    public Object getTag();

}
