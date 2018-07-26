package com.xq.projectdefine.base.base;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    protected T presenter;

    protected View rootView;

    public FasterBaseView(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

        if (getPresenter().getAreActivity() != null)
        {
            rootView = getPresenter().getAreActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }
        else    if (getPresenter().getAreFragment() != null)
        {
            rootView = getPresenter().getAreFragment().getView();
        }

        if (isAutoFindView())
            autoFindView();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public T getPresenter() {
        return presenter;
    }

    @Override
    public Context getContext() {
        return getPresenter().getContext();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    //判断是否顶部容器
    protected boolean isTopContainer(){
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations)
        {
            if (annotation instanceof TopContainer)
                return true;
        }
        return false;
    }

    //重定义此方法返回false可以取消默认自动的findViewById
    protected boolean isAutoFindView(){
        return true;
    }

    //自动findViewById(必须保证布局文件中的id与变量名一致)
    private void autoFindView() {
        Class mClass = this.getClass();
        while (true)
        {
            Field[] fields = mClass.getDeclaredFields();
            for (Field field : fields)
            {
                if (View.class.isAssignableFrom(field.getType()))
                {
                    String fileName = field.getName();
                    View view = findViewById(getContext().getResources().getIdentifier(fileName, "id", getContext().getPackageName()));
                    if (view != null)
                    {
                        field.setAccessible(true);
                        try {
                            field.set(this,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            mClass = mClass.getSuperclass();
            if (mClass.getName().equals(Object.class.getName()))
                break;
        }
    }

}
