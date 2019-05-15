package com.xq.androidfaster.base.base.aspresenter;

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
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.base.IFasterBasePresenter;
import com.xq.androidfaster.base.base.IFasterBaseView;
import com.xq.androidfaster.base.life.PresenterLife;
import com.xq.androidfaster.util.callback.ActivityResultCallback;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseFragment<T extends IFasterBaseView> extends Fragment implements IFasterBasePresenter<T> {

    protected Context context;

    protected T view = createBindView();

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getBindView() == null) return super.onCreateView(inflater,container,savedInstanceState);

        View rootView = inflater.inflate(getBindView().getLayoutId(),container,false);

        return rootView;
    }

    @Deprecated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            resolveBundle(bundle);
        else
            resolveBundle(new Bundle());

        initData();

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

        if (getBindView() != null) getBindView().afterOnCreate(savedInstanceState);

        for (PresenterLife life: list_delegate)  life.afterOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getBindView() != null) getBindView().onResume();

        for (PresenterLife life: list_delegate)  life.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getBindView() != null) getBindView().onPause();

        for (PresenterLife life: list_delegate)  life.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getBindView() != null) getBindView().onDestroy();

        for (PresenterLife life: list_delegate)  life.onDestroy();    list_delegate.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//    TODO:以下方法在Fragments宿主环境下不起作用，待后期维护
//        处理Fragment保存状态
//        if (!getBindView().isSaveFragmentState()) outState.putParcelable("android:support:fragments", null);

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
    public void startActivity(Class mClass) {
        startActivity(new Intent(getContext(),mClass));
    }

    //为了与Activity保持同步创建此方法
    public void startActivities(Intent[] intents) {
        getContext().startActivities(intents);
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
    public Fragment getAreFragment() {
        return this;
    }

    @Override
    public Activity getAreActivity() {
        return null;
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
    public boolean onBackClick() {
        return false;
    }

    @Override
    public void back() {
        ((Activity)getContext()).onBackPressed();
    }

    @Override
    public List<AbsPresenterDelegate> getDelegates() {
        return list_delegate;
    }

    @Override
    public void inject(AbsPresenterDelegate delegate) {
        getDelegates().add(delegate);
    }

}
