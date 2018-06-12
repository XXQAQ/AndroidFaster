package com.xq.projectdefine.base.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public abstract class FasterBaseView<T extends IFasterBasePresenter> implements IFasterBaseView<T> {

    protected T presenter;

    protected View rootView;

    protected Toolbar toolbar;

    public FasterBaseView(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

        if (getPresenter().getCPActivity() != null)
        {
            rootView = getPresenter().getCPActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }
        else    if (getPresenter().getCPFragment() != null)
        {
            rootView = getPresenter().getCPFragment().getView();
        }

        toolbar = (Toolbar) rootView.findViewById(getContext().getResources().getIdentifier("toolbar", "id", getContext().getPackageName()));

    }

    @Override
    public void initToolbar(String title) {

        initToolbar(title,true);
    }

    @Override
    public void initToolbar(String title, boolean isback) {

        if (toolbar == null)
            return;

        toolbar.setBackgroundColor(getToolbarBackgroundColor());

        TextView tv_toolTitle = (TextView) toolbar.findViewById(getContext().getResources().getIdentifier("tv_toolTitle", "id", getContext().getPackageName()));
        if (tv_toolTitle == null)
        {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(getToolbarWidgetColor());
        }
        else
        {
            tv_toolTitle.setText(title);
            tv_toolTitle.setTextColor(getToolbarWidgetColor());
        }

        if (isback)
        {
            toolbar.setNavigationIcon(getNavIcon());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppCompatActivity)getContext()).finish();
                }
            });
        }
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

    //重写该方法以自定义Toolbar背景颜色
    public abstract int getToolbarBackgroundColor();

    //重写该方法以自定义Toolbar上的控件颜色
    public abstract int getToolbarWidgetColor();

    public abstract int getNavIcon();

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
}
