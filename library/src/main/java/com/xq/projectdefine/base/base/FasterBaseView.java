package com.xq.projectdefine.base.base;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xq.projectdefine.base.life.ViewLife;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    protected T presenter;

    protected View rootView;

    private List<ViewLife> list_life = new LinkedList<>();

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

        for (ViewLife life : list_life)     life.afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        for (ViewLife life : list_life)     life.onResume();
    }

    @Override
    public void onPause() {
        for (ViewLife life : list_life)     life.onPause();
    }

    @Override
    public void onDestroy() {
        for (ViewLife life : list_life)     life.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (ViewLife life : list_life)     life.onSaveInstanceState(outState);
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

    @Override
    public List<ViewLife> getLifes() {
        return list_life;
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

    //重定义此方法返回false可以取消默认findViewById
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
