package com.xq.projectdefine.bean.behavior;


import java.io.Serializable;


//该接口专注于开关选项，实现该接口可以使您的对象直接显示在开关信息中
public interface SwitchBehavior extends Serializable{

    //当前开关状态
    public boolean isOpen();

    //当前开关状态的描述，如果不需要返回null即可
    public String stateDescript();

    public Object getTag();

}
