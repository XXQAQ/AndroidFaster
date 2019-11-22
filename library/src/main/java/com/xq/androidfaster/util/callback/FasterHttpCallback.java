package com.xq.androidfaster.util.callback;

public interface FasterHttpCallback<T> {

    //请求开始
    default void requestStart(Object... objects) {

    }

    //请求错误
    default void requestError(Object... objects) {
        getCallbackBuilder().isOperateSuccess = false;
        operateErro(null);
    }

    //当前请求进度(0-1.0)
    default void upLoadProgress(float progress){

    }

    //当前下载进度(0-1.0)
    default void downLoadProgress(float progress){

    }

    //请求成功
    default void requestSuccess(T t, Object... objects) {
        getCallbackBuilder().data = t;
        if (operating(t,objects))
        {
            getCallbackBuilder().isOperateSuccess = true;
            operateSuccess(t);
        }
        else
        {
            operateErro(t);
        }
    }

    //请求结束
    default void requestFinish(Object... objects) {

    }

    //判断业务操作是否成功
    public boolean operating(T t, Object... objects);

    //业务操作成功之后调用的方法
    public void operateSuccess(T t);

    //业务操作失败之后调用的方法
    public void operateErro(T t);

    public CallbackBuilder<T> getCallbackBuilder();

    public class CallbackBuilder<T>{
        public boolean isOperateSuccess = false;
        public T data;
    }

}
