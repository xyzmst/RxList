package org.xyzmst.rxlist;

import android.util.Log;

import org.xyzmst.base.SubscriberBase;
import org.xyzmst.rxlist.fragment.RxListFragment;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author mac
 * @title RxListSubscriber
 * @description 支持标准rx数据格式
 * @modifier
 * @date
 * @since 16/3/31 12:01
 **/
public abstract class RxListBaseDataSubscriber<T> extends SubscriberBase<Object> {
    RxListFragment mFragment;
    private static final String TAG = "RxDataSubscriber";

    public RxListBaseDataSubscriber(RxListFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void doOnNext(Object item) {
        onStart();
        Log.e(TAG, "bindData: rx");
        bindData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberBase<RxBaseData<T>>() {

                    @Override
                    public void doOnCompleted() {
                        Log.e(TAG, "bindData doOnCompleted: ");
                        RxListBaseDataSubscriber.this.doOnCompleted();
                        if (mFragment != null)
                            mFragment.stopLoadingOrRfresh();

                    }

                    @Override
                    public void doOnError(Throwable e) {
                        Log.e(TAG, "bindData doOnError: ");
                        RxListBaseDataSubscriber.this.doOnError(e);
                        if (mFragment != null)
                            mFragment.stopLoadingOrRfresh();
                    }

                    @Override
                    public void doOnNext(RxBaseData<T> item) {
                        pageNext(item);
                    }
                });
    }

    /**
     * 绑定数据方法
     *
     * @return
     */
    public abstract Observable<RxBaseData<T>> bindData();


    /**
     * 默认分页方法
     *
     * @param data
     */
    void pageNext(RxBaseData<T> data) {
        Log.e(TAG, "pageNext: rx ");
        if (data != null) {
            int count = data.count;
            mFragment.TOTAL_COUNT = data.count;
            List<T> list = data.list;
            if (list != null && list.size() > 0) {
                if (mFragment.cursor == 0) {
                    mFragment.adapter.clear();
                }
                mFragment.cursor += mFragment.PAGE_COUNT;
                mFragment.adapter.addAll(list);
                mFragment.adapter.notifyChanged();

                if (mFragment.adapter.size() == 0 || mFragment.cursor > count) {
                    mFragment.mIsLoadingMore = false;
                } else {
                    mFragment.mIsLoadingMore = true;
                }
            } else if (mFragment.cursor == 0) {
                mFragment.adapter.clear();
                mFragment.adapter.notifyChanged();
            }
            mFragment.stopLoadingOrRfresh();

        }
    }
}
