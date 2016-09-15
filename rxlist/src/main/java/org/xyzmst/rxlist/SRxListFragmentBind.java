package org.xyzmst.rxlist;


import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.adapter.RxAdapterEventOnSubscribe;
import org.xyzmst.rxlist.adapter.RxBaseAdapter;
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
public class SRxListFragmentBind {
    static SRxListFragmentBind sRxListFragmentBind;
    static RxAdapterBindView mBindView;

    private SRxListFragmentBind() {

    }

    public static SRxListFragmentBind bindView(RxAdapterBindView bindView) {
        if (sRxListFragmentBind == null) {
            sRxListFragmentBind = new SRxListFragmentBind();
        }
        mBindView = bindView;
        return sRxListFragmentBind;
    }

    public static Observable<Object> bindFragment(RxListFragment fragment) {
        RxBaseAdapter mAdapter = new RxBaseAdapter();
        mAdapter.setBindView(mBindView);
        fragment.bindAdapter(mAdapter);
        Observable obAdpter = Observable.create(new RxAdapterEventOnSubscribe(mAdapter));
        Observable obFragment = Observable.create(new RxFragmentEventOnSubscribe(fragment));
        return obFragment.mergeWith(obAdpter);

    }


}
