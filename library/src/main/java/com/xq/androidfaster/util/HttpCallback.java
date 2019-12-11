package com.xq.androidfaster.util;

public interface HttpCallback<T> {

    //当前请求进度(0-1.0)
    default void upLoadProgress(float progress){

    }

    //当前下载进度(0-1.0)
    default void downLoadProgress(float progress){

    }

    //请求开始
    default void requestStart() {

    }

    //请求错误
    default void requestError(Throwable e) {
        getCallbackBean().isOperateSuccess = false;
        operateErro(null);
    }

    //请求成功
    default void requestSuccess(T t) {

        getCallbackBean().data = t;

        if (operating(t)) {
            getCallbackBean().isOperateSuccess = true;
            operateSuccess(t);
        } else {
            getCallbackBean().isOperateSuccess = false;
            operateErro(t);
        }
    }

    //请求结束
    default void requestFinish() {

    }

    default Class<T> getDataClass(){
        return getCallbackBean().dataClass;
    }

    default boolean isOperateSuccess(){
        return getCallbackBean().isOperateSuccess;
    }

    default T getData(){
        return getCallbackBean().data;
    }

    //重写本方法判断业务操作是否成功
    default boolean operating(T t){
        return t != null;
    }

    //业务操作成功之后的回调方法
    public void operateSuccess(T t);

    //业务操作失败之后的回调方法
    public void operateErro(T t);

    default CallbackBean<T> getCallbackBean(){
        return createCallbackBean();
    }

    public CallbackBean<T> createCallbackBean();

    public class CallbackBean<T>{
        public boolean isOperateSuccess = false;
        public Class<T> dataClass;
        public T data;
    }

}
