package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xq.projectdefine.util.callback.ActivityResultCallback;

public abstract class FasterBaseFragment<T extends IFasterBaseView> extends Fragment implements IFasterBasePresenter<T> {

    private Context context;

    protected T view;

    //构造方案
    {
        view = createBindView();
    }

    //重写该方法，返回对应View层
    protected abstract T createBindView();

    //初始化数据
    protected void initData(){

    }

    //该方法用于解析从其他页面传来的数据，注意如果传递数据不存在则不会执行该方法
    protected void resolveBundle(Bundle bundle) {

    }

    @Deprecated
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getBindView() == null)
            return super.onCreateView(inflater,container,savedInstanceState);

        View rootView = inflater.inflate(getBindView().getLayoutId(),container,false);

        return rootView;
    }

    @Deprecated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        Bundle bundle = getArguments();
        if (bundle != null)
            resolveBundle(bundle);

        if (getBindView() != null) getBindView().afterOnCreate(savedInstanceState);

        afterOnCreate(savedInstanceState);
    }

    @Deprecated
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getBindView() != null) getBindView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getBindView() != null) getBindView().onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getBindView() != null) getBindView().onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getBindView() != null) getBindView().onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        ActivityResultCallback callback = spa_resultCallback.get(requestCode);
        if (null != callback)
        {
            switch (resultCode)
            {
                case Activity.RESULT_OK:
                    callback.onSuccess(data);
                    break;
                case Activity.RESULT_CANCELED:
                    callback.onCancel();
                    break;
            }
        }
    }

    @Override
    public void startActivity(Class mClass) {
        startActivity(new Intent(getContext(),mClass));
    }

    //为了与Activity保持同步创建此方法
    public void startActivities(Intent[] intents) {
        getContext().startActivities(intents);
    }

    //封装startActivityForResult为回调的形式
    private SparseArray<ActivityResultCallback> spa_resultCallback = new SparseArray();
    public void  startActivityForResult(Intent intent, ActivityResultCallback callback){
        int requestCode;
        if (callback != null)
        {
            requestCode = callback.hashCode();
            requestCode &= 0x0000ffff;
            spa_resultCallback.append(requestCode,callback);
            startActivityForResult(intent, requestCode);
        }
        else    startActivity(intent);
    }

    @Override
    public void finishSelf() {
        getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    @Override
    public void finish() {
        ((Activity)getContext()).finish();
    }

    @Override
    public Fragment getAreFragment() {
        return this;
    }

    @Override
    public Activity getAreActivity() {
        return null;
    }

    @Override
    public T getBindView() {
        return view;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
