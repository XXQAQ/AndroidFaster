package com.xq.androidfaster.bean.behavior;

import java.io.Serializable;
import java.util.List;

//实现该对象可以使您的对象提供描述列表的能力
public interface ListBehavior extends Serializable{

    //返回列表
    public List getList();
}
