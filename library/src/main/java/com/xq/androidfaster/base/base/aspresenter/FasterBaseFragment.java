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
import com.xq.androidfaster.base.base.ActivityResultCallback;
import java.util.LinkedList;
import java.util.List;

public abstract class FasterBaseFragment<T extends IFasterBaseView> extends Fragment implements IFasterBasePresenter<T> {

    private Context context;

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

        if (savedInstanceState != null) isRestoreState = true;

        Bundle bundle = getArguments();
        if (bundle != null)
            resolveBundle(bundle);
        else
            resolveBundle(new Bundle());

        initData();

        create(savedInstanceState);
    }

    @Deprecated
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Deprecated
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            invisible();
        else
            visible();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        boolean change = isVisibleToUser != getUserVisibleHint();
        super.setUserVisibleHint(isVisibleToUser);
        // 在viewpager中，创建fragment时就会调用这个方法，但这时还没有resume，为了避免重复调用visible和invisible，
        // 只有当fragment状态是resumed并且初始化完毕后才进行visible和invisible的回调
        if (isResumed() && change)
        {
            if (getUserVisibleHint())
                visible();
            else
                invisible();
        }
    }

    @Deprecated
    @Override
    public void onResume() {
        super.onResume();
        // onResume并不代表fragment可见
        // 如果是在viewpager里，就需要判断getUserVisibleHint，不在viewpager时，getUserVisibleHint默认为true
        // 如果是其它情况，就通过isHidden判断，因为show/hide时会改变isHidden的状态
        // 所以，只有当fragment原来是可见状态时，进入onResume就回调onVisible
        if (getUserVisibleHint() && !isHidden()) visible();
    }

    @Deprecated
    @Override
    public void onPause() {
        super.onPause();
        // onPause时也需要判断，如果当前fragment在viewpager中不可见，就已经回调过了，onPause时也就不需要再次回调onInvisible了
        // 所以，只有当fragment是可见状态时进入onPause才加调onInvisible
        if (getUserVisibleHint() && !isHidden()) invisible();
    }

    @Deprecated
    @Override
    public void onStop() {
        super.onStop();
    }

    @Deprecated
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Deprecated
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        for (PresenterLife life: list_delegate)  life.destroy();    list_delegate.clear();
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
