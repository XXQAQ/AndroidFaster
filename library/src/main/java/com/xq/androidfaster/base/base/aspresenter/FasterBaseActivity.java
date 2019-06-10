package com.xq.androidfaster.base.base.aspresenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.base.IFasterBasePresenter;
import com.xq.androidfaster.base.base.IFasterBaseView;
import com.xq.androidfaster.base.life.PresenterLife;
import com.xq.androidfaster.base.base.ActivityResultCallback;
import com.xq.androidfaster.util.tools.FragmentUtils;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseActivity<T extends IFasterBaseView> extends AppCompatActivity implements IFasterBasePresenter<T> {

    private T view = createBindView();

    private List<AbsPresenterDelegate> list_delegate = new LinkedList<>();

    //重写该方法，返回对应View层
    protected abstract T createBindView();

    //初始化数据
    protected void initData(){

    }

    //解析从其他页面传来的数据
    protected void resolveBundle(Bundle bundle) {

    }

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) isRestoreState = true;

        if (getBindView() != null) setContentView(getBindView().getLayoutId());

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null)
            resolveBundle(intent.getExtras());
        else
            resolveBundle(new Bundle());

        initData();

        create(savedInstanceState);
    }

    @Deprecated
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Deprecated
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Deprecated
    @Override
    protected void onResume() {
        super.onResume();
        visible();
    }

    @Deprecated
    @Override
    protected void onPause() {
        super.onPause();
        invisible();
        if (isFinishing())  destroy();
    }

    @Deprecated
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void create(Bundle savedInstanceState) {
        if (getBindView() != null) getBindView().create(savedInstanceState);

        for (PresenterLife life: list_delegate)  life.create(savedInstanceState);
    }

    @Override
    public void visible() {
        if (getBindView() != null) getBindView().visible();

        for (PresenterLife life: list_delegate)  life.visible();
    }

    @Override
    public void invisible() {
        isFirstVisible = false;

        if (getBindView() != null) getBindView().invisible();

        for (PresenterLife life: list_delegate)  life.invisible();
    }

    @Override
    public void destroy() {
        if (getBindView() != null) getBindView().destroy();

        for (PresenterLife life: list_delegate)  life.destroy();  list_delegate.clear();
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

        for (PresenterLife life: list_delegate)  life.onActivityResult(requestCode,resultCode,data);
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

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
    public T getBindView() {
        return view;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Fragment getAreFragment() {
        return null;
    }

    @Override
    public Activity getAreActivity() {
        return this;
    }

    private boolean isFirstVisible = true;
    @Override
    public boolean isFirstVisible() {
        return isFirstVisible;
    }

    private boolean isRestoreState = false;
    @Override
    public boolean isRestoreState() {
        return isRestoreState;
    }

    @Deprecated
    @Override
    public void onBackPressed() {
        if (!FragmentUtils.dispatchBackPress(getSupportFragmentManager()))
        {
            if (!onBackClick()) super.onBackPressed();
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    @Override
    public void inject(AbsPresenterDelegate delegate) {
        getDelegates().add(delegate);
    }

    protected List<AbsPresenterDelegate> getDelegates() {
        return list_delegate;
    }

}
