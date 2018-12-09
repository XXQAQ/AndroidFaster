package com.xq.androidfaster.bean.behavior;

import java.io.Serializable;

//实现该接口可以使您的对象描述成功或失败的能力
public interface SuccessBehavior extends Serializable {

    public boolean isSuccess();
}
