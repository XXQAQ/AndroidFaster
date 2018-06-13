package com.xq.projectdefine.bean.behavior;


import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xq on 2017/8/4.
 */

//该接口专用于开关选项，实现该接口可以使您的对象直接显示在开关信息中
public interface SwitchBehavior extends Serializable,Parcelable {

    //当前开关状态
    public boolean isOpen();

    //当前开关状态的描述，如果不需要返回null即可
    public String stateDescript();

    public Object getTag();

}
