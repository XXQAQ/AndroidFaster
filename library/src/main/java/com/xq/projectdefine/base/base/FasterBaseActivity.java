package com.xq.projectdefine.base.base;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.xq.projectdefine.base.life.PresenterLife;
import com.xq.projectdefine.util.callback.ActivityResultCallback;

import java.util.LinkedList;
import java.util.List;


public abstract class FasterBaseActivity<T extends IFasterBaseView> extends AppCompatActivity implements IFasterBasePresenter<T> {

    protected Context context = this;

    protected T view = createBindView();

    private List<PresenterLife> list_life = new LinkedList<>();

    //重写该方法，返回对应View层
    protected abstract T createBindView();

    //初始化数据
    protected void initData(){

    }

    //该方法用于解析从其他页面传来的数据，注意如果传递数据不存在则不会执行该方法
    protected void resolveBundle(Bundle bundle) {

    }

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getBindView() != null)
            setContentView(getBindView().getLayoutId());

        initData();

        Intent intent = getIntent();
        if (intent != null)
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                resolveBundle(bundle);
        }

        if (getBindView() != null) getBindView().afterOnCreate(savedInstanceState);

        afterOnCreate(savedInstanceState);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        for (PresenterLife life:list_life)  life.afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getBindView() != null) getBindView().onResume();

        for (PresenterLife life:list_life)  life.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getBindView() != null) getBindView().onPause();

        for (PresenterLife life:list_life)  life.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getBindView() != null) getBindView().onDestroy();

        for (PresenterLife life:list_life)  life.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getBindView() != null) getBindView().onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultCallback callback = spa_resultCallback.get(requestCode);
        spa_resultCallback.remove(requestCode);
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

        for (PresenterLife life:list_life)  life.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void startActivity(Class mClass) {
        startActivity(new Intent(getContext(),mClass));
    }

    //封装startActivityForResult成回调的形式
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

    @Deprecated
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Deprecated
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void back() {
        new Thread(){
            public void run() {
                try{
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                }
                catch (Exception e) {
                }
            }
        }.start();
    }

    @Override
    public Fragment getAreFragment() {
        return null;
    }

    @Override
    public Activity getAreActivity() {
        return this;
    }

    @Override
    public T getBindView() {
        return view;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public List<PresenterLife> getLifes() {
        return list_life;
    }
}
