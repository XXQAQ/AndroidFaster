package com.xq.androidfaster.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xq.androidfaster.util.tools.ActivityUtils;
import com.xq.androidfaster.util.tools.FragmentUtils;
import com.xq.androidfaster.util.tools.ReflectUtils;
import java.util.LinkedList;
import java.util.List;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public abstract class FasterBaseFragment<T extends IFasterBaseBehavior> extends Fragment implements IFasterBaseBehavior<T> ,FragmentUtils.OnBackClickListener,OnStartFragmentBehavior {

    {
        ReflectUtils.reflect(this).field("mLifecycleRegistry",getLifecycle());
    }

    private T another;
    {
        if (another == null)
            another = createBindView();
        if (another == null)
            another = createBindPresenter();
    }

    private Context context;

    protected abstract T createBindView();

    protected abstract T createBindPresenter();

    @Deprecated
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //布局初始化
        {
            if (getLayoutId() != 0 || (getBindAnother() != null && getBindAnother().getLayoutId() != 0))
            {
                rootView = inflater.inflate(getLayoutId() == 0?getBindAnother().getLayoutId() : getLayoutId(),container,false);

                autoFindView(this);
                if (getBindAnother() != null)   autoFindView(getBindAnother());
            }
        }

        return rootView;
    }

    @Deprecated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) isRestoreState = true;

        //传参初始化
        {
            Bundle bundle = getArguments();
            if (bundle == null) bundle = new Bundle();

            resolveBundle(bundle);
            if (getBindAnother() != null) getBindAnother().resolveBundle(bundle);
        }

        //自定义初始化
        {
            init();
            if (getBindAnother() != null) getBindAnother().init();
        }

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

    @Deprecated
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            invisible();
        else
            visible();
    }

    @Deprecated
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

        List<Fragment> containerFragments = new LinkedList<>();
        for (Fragment f : FragmentUtils.getFragments(getTopFragmentManager())){
            if (getArguments()!= null && f.getArguments() != null && getArguments().getInt(FragmentUtils.ARGS_ID) != 0 && f.getArguments().getInt(FragmentUtils.ARGS_ID,0) == getArguments().getInt(FragmentUtils.ARGS_ID,0)){
                containerFragments.add(f);
            }
        }
        if (containerFragments.size() >= 2)
            FragmentUtils.show(containerFragments.get(containerFragments.size()-1-1));
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
        getLifecycle().handleCreate(savedInstanceState);
    }

    @Override
    public void visible() {
        getLifecycle().handleVisible();
    }

    @Override
    public void invisible() {
        isFirstVisible = false;
        getLifecycle().handleInvisible();
    }

    @Override
    public void destroy() {
        getLifecycle().handleDestroy();

        if (getArguments() != null && getArguments().getInt("requestCode") !=0 && !isSetResult)
            setResult(RESULT_CANCELED,null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getLifecycle().handleSaveInstanceState(outState);
    }

    private FasterLifecycleRegistry lifecycleRegistry;
    @Override
    public FasterLifecycleRegistry getLifecycle() {
        if (lifecycleRegistry == null) lifecycleRegistry = new FasterLifecycleRegistry(this);
        return lifecycleRegistry;
    }

    //封装startActivityForResult成回调的形式
    private SparseArray<ResultCallback> spa_resultCallback = new SparseArray();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getLifecycle().handleActivityResult(requestCode,resultCode,data);

        ResultCallback callback = spa_resultCallback.get(requestCode);
        spa_resultCallback.remove(requestCode);
        if (null != callback)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    callback.onSuccess(data);
                    break;
                case RESULT_CANCELED:
                    callback.onCancel();
                    break;
            }
        }
    }

    public void  startActivityForResult(Intent intent, ResultCallback callback){
        startActivityForResult(intent,0,0,callback);
    }

    public void  startActivityForResult(Intent intent,int enterAnim,int exitAnim, ResultCallback callback){
        int requestCode = callback.hashCode();
        requestCode &= 0x0000ffff;
        spa_resultCallback.append(requestCode,callback);

        Bundle bundle = new Bundle(intent.getExtras());
        bundle.putInt("enterAnim",enterAnim);
        bundle.putInt("exitAnim",exitAnim);
        intent.putExtras(bundle);

        startActivityForResult(intent,requestCode,ActivityUtils.getOptionsBundle(getContext(),enterAnim,exitAnim));
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

    public void startActivity(Intent intent,int enterAnim,int exitAnim){

        Bundle bundle = new Bundle(intent.getExtras());
        bundle.putInt("enterAnim",enterAnim);
        bundle.putInt("exitAnim",exitAnim);
        intent.putExtras(bundle);

        startActivity(intent,ActivityUtils.getOptionsBundle(getContext(),enterAnim,exitAnim));
    }

    @Override
    public void startFragment(Fragment fragment,int containerId,int enterAnim,int exitAnim) {
        if (getContext() instanceof OnStartFragmentBehavior)
            ((OnStartFragmentBehavior) getContext()).startFragment(fragment,containerId,enterAnim,exitAnim);
    }

    @Override
    public void startFragmentForResult(Fragment fragment,int containerId,int enterAnim,int exitAnim,ResultCallback callback){
        if (getContext() instanceof OnStartFragmentBehavior)
            ((OnStartFragmentBehavior) getContext()).startFragmentForResult(fragment,containerId,enterAnim,exitAnim,callback);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public T getBindAnother() {
        return another;
    }

    @Override
    public void init() {

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
    public void resolveBundle(Bundle bundle) {

    }

    private View rootView;
    @Override
    public View getRootView() {
        return rootView;
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
    public void initFragment(Fragment fragment) {

    }

    @Override
    public void finish() {
        if (getContext() instanceof FasterBaseActivity && FragmentUtils.getTopShowInStack(getTopFragmentManager()) == this){
            popFragment();
        } else {
            removeMe();
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    public int getColor(int resId){
        return ContextCompat.getColor(getContext(),resId);
    }

    boolean isSetResult = false;
    public void setResult(int resultCode,Intent intent){
        if (getContext() instanceof OnStartFragmentBehavior)
        {
            if (getArguments() != null)
            {
                int requestCode = getArguments().getInt("requestCode");
                if (requestCode != 0)
                {
                    isSetResult = true;
                    ((OnStartFragmentBehavior) getContext()).onFragmentResult(requestCode,resultCode,intent);
                }
            }
        }
    }

}
