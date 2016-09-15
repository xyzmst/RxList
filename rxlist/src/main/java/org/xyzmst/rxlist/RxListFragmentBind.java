package org.xyzmst.rxlist;


import org.xyzmst.rxlist.adapter.RxAdapterAble;
import org.xyzmst.rxlist.fragment.RxFragmentEventOnSubscribe;
import org.xyzmst.rxlist.fragment.RxListFragment;

import rx.Observable;

/**
 * @author mac
 * @title RxListFragment
 * @description
 * @modifier
 * @date
 * @since 16/3/22 18:14
 **/
public class RxListFragmentBind {

    private static RxListFragmentBind mRxListFragmentx;
    static RxListFragment mFragmentx;
    static RxAdapterAble mAdapter;


    private RxListFragmentBind() {

    }

    public static RxListFragmentBind bindFragment(RxListFragment fragmentx) {
        if (mRxListFragmentx == null) {
            mRxListFragmentx = new RxListFragmentBind();
        }
        mFragmentx = fragmentx;
        return mRxListFragmentx;
    }


    public Observable<Void> bindAdapter(RxAdapterAble adapter) {
        if (mRxListFragmentx == null) {
            mRxListFragmentx = new RxListFragmentBind();
        }
        mAdapter = adapter;
        mFragmentx.bindAdapter(adapter);
        return Observable.create(new RxFragmentEventOnSubscribe(mFragmentx));
    }


}
