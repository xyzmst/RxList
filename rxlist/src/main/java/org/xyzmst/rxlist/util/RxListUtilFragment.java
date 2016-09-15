package org.xyzmst.rxlist.util;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xyzmst.base.BaseFragment;
import org.xyzmst.base.NetworkUtil;
import org.xyzmst.rxlist.fragment.RxLinearListFragment;

import butterknife.ButterKnife;

/**
 * @author mac
 * @title DListFragment
 * @description
 * @modifier
 * @date
 * @since 16/7/25 14:43
 **/
public abstract class RxListUtilFragment extends BaseFragment {

    public View mContentWrapper;
    public View mNoWebWrapper;
    public RxLinearListFragment mFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(getContentView());
        ButterKnife.bind(this, getFragmentViewRoot());
        return getFragmentViewRoot();
    }

    public abstract int getRxFragmentId();

    public abstract int getContentView();

    @Override
    protected void onFragmentLayoutInit() {
        ButterKnife.bind(this, getFragmentViewRoot());
    }

    @Override
    protected void onFragmentDataBind() {
        if (mFragment == null) {
            mFragment = new RxLinearListFragment();
            mFragment.setPadding(0, -2, 0, 0);
            mFragment.setBackgroundColor(Color.parseColor("#ffffff"));
            bindFragment();
            getChildFragmentManager().beginTransaction().add(getRxFragmentId(), mFragment).commitAllowingStateLoss();
        }
        if (NetworkUtil.isNetConnecting(getActivity())) {
            changeWeb();
        } else {
            changeNoWeb();
        }
    }

    public abstract void bindFragment();

    public void changeNoWeb() {
        if (mNoWebWrapper != null && mContentWrapper != null) {
            mNoWebWrapper.setVisibility(View.VISIBLE);
            mContentWrapper.setVisibility(View.GONE);
        }
    }

    public void changeWeb() {
        if (mNoWebWrapper != null && mContentWrapper != null) {
            mNoWebWrapper.setVisibility(View.GONE);
            mContentWrapper.setVisibility(View.VISIBLE);
        }
    }

    public void noWebclick() {
        if (NetworkUtil.isNetConnecting(getActivity())) {
            changeWeb();
            mFragment.cursor = 0;
            mFragment.refresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
