package org.xyzmst.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import java.lang.reflect.Field;

/**
 * @author green
 * @title BaseFragment
 * @description Fragment基础类
 * @modifier green
 * @date 15/1/9 上午9:51
 * @since 2014/5/12 3:06
 */
public abstract class BaseFragment extends Fragment {
    // Arbitrary value; set it to some reasonable default
    private static final int DEFAULT_CHILD_ANIMATION_DURATION = 250;
    private static final String TAG = "BaseFragment";


    public BaseFragment() {
        setArguments(new Bundle());
    }

    /**
     * 整个Fragment的根（仅适用于使用setContentView来设置layout的状况）
     */
    protected View mFragmentViewRoot;
    private boolean mIsBindDataAfterLayoutComplete;


    /**
     * 本方法仅在FragmentViewRoot第一次创建时执行（仅适用于使用setContentView加载layout的情况）
     */
    protected abstract void onFragmentLayoutInit();

    /**
     * 在Views初始化完毕后，执行数据绑定操作。</br>
     */
    protected abstract void onFragmentDataBind();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mFragmentViewRoot;
    }

    /**
     * 指定layout id
     *
     * @param rid
     */
    protected void setContentView(int rid) {
        setContentView(rid, LayoutInflater.from(getActivity()), null, null);
    }

    protected void setContentView(int rid, ViewGroup container, boolean b) {
        setContentView(rid, LayoutInflater.from(getActivity()), container, null, b);
    }

    protected void setContentView(int rid, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(rid, inflater, container, savedInstanceState, false);
    }

    protected void setContentView(int rid, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, boolean b) {
        if (mFragmentViewRoot == null) {
            mFragmentViewRoot = inflater.inflate(rid, container, b);
            onFragmentLayoutInit();
            ViewUtil.onGlobalLayoutOnce(getFragmentViewRoot(), new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    onFragmentLayoutComplete();
                    if (isBindDataAfterLayoutComplete()) {
                        onFragmentDataBind();
                    }
                }
            });
            if (!isBindDataAfterLayoutComplete()) {
                onFragmentDataBind();
            }
        } else {
            if (mFragmentViewRoot.getParent() != null) {
                if (mFragmentViewRoot.getParent() instanceof ViewGroup) {
                    ((ViewGroup) mFragmentViewRoot.getParent()).removeView(mFragmentViewRoot);
                }
            }
            onFragmentDataBind();
        }

    }

    /**
     * 在Layout布局树完成时调用
     */
    protected void onFragmentLayoutComplete() {

    }


    /**
     * 设置是否在布局树完成之后才触发数据绑定
     *
     * @param isBindDataAfterLayoutComplete
     */
    public void setBindDataAfterLayoutComplete(boolean isBindDataAfterLayoutComplete) {
        mIsBindDataAfterLayoutComplete = isBindDataAfterLayoutComplete;
    }

    /**
     * 是否在布局树完成之后才触发数据绑定
     *
     * @return
     */
    public boolean isBindDataAfterLayoutComplete() {
        return mIsBindDataAfterLayoutComplete;
    }

    /**
     * 整个Fragment的根（仅适用于使用setContentView来设置layout的状况）
     *
     * @return
     */
    protected View getFragmentViewRoot() {
        return mFragmentViewRoot;
    }


    /**
     * 处理掉异常：IllegalStateException("Fragment already active")</br>
     * 即使在已经添加的情况下仍然可以修改参数
     *
     * @param args
     */
    @Override
    public void setArguments(Bundle args) {
        //
        if (getArguments() == null) {
            try {
                super.setArguments(new Bundle());
            } catch (IllegalStateException e) {
                try {
                    Field mArguments_Field = getClass().getDeclaredField("mArguments");
                    mArguments_Field.setAccessible(true);
                    mArguments_Field.set(this, new Bundle());
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }
        }
        getArguments().clear();
        if (args != null) {
            getArguments().putAll(args);
        }
    }

    /**
     * 更新数据
     */
    final public void refresh() {
        onFragmentDataBind();
    }

    /**
     * 重新传入bundle,并更新数据
     *
     * @param bundle
     */
    final public void refresh(@NonNull Bundle bundle) {
        setArguments(bundle);
        onFragmentDataBind();
    }

    @Override
    public void onDestroy() {
        hideLoadingDialog();
        super.onDestroy();
    }



    public final void hideLoadingDialog() {
        if (getActivity() instanceof ActionBarBaseActivity) {
//            ((ActionBarBaseActivity) getActivity()).hideLoadingAnim();
        } else {
            LoadingDialogUtils.hide(getChildFragmentManager(), getClass().getSimpleName());
        }
    }

    public final void hideLoadingDialogAnim() {
        if (getActivity() instanceof ActionBarBaseActivity) {
            ((ActionBarBaseActivity) getActivity()).hideLoadingAnim();
        } else {
            LoadingDialogUtils.hide(getChildFragmentManager(), getClass().getSimpleName());
        }
    }


    /**
     * 重写Fragment的创建动画，避免在Fragment使用动画时，被嵌套的Fragment无法正常显示的问题
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        final Fragment parent = getParentFragment();

        // Apply the workaround only if this is a child fragment, and the parent
        // is being removed.
        if (!enter && parent != null && parent.isRemoving()) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            Animation doNothingAnim = new AlphaAnimation(1, 1);
            doNothingAnim.setDuration(getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION));
            return doNothingAnim;
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    private static long getNextAnimationDuration(Fragment fragment, long defValue) {
        try {
            // Attempt to get the resource ID of the next animation that
            // will be applied to the given fragment.
            Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);
            int nextAnimResource = nextAnimField.getInt(fragment);
            Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

            // ...and if it can be loaded, return that animation's duration
            return (nextAnim == null) ? defValue : nextAnim.getDuration();
        } catch (Exception ex) {
            Log.w(TAG, "Unable to load next animation from parent.", ex);
            return defValue;
        }
    }


}
