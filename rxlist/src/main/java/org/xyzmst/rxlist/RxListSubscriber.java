package org.xyzmst.rxlist;


import org.xyzmst.base.SubscriberBase;
import org.xyzmst.rxlist.fragment.RxListFragment;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author mac
 * @title RxListSubscriber
 * @description 支持拼接map数据格式
 * @modifier
 * @date
 * @since 16/3/31 12:01
 **/
public abstract class RxListSubscriber<T> extends SubscriberBase<T> {
    RxListFragment mFragment;

    public RxListSubscriber(RxListFragment fragmentx) {
        mFragment = fragmentx;
    }

    @Override
    public void doOnNext(T item) {
        super.doOnNext(item);
        bindData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberBase<HashMap<String, Object>>() {
                    @Override
                    public void doOnCompleted() {
                        RxListSubscriber.this.doOnCompleted();
                        mFragment.stopLoadingOrRfresh();

                    }

                    @Override
                    public void doOnError(Throwable e) {
                        RxListSubscriber.this.doOnError(e);
                        mFragment.stopLoadingOrRfresh();
                    }

                    @Override
                    public void doOnNext(HashMap<String, Object> item) {
                        super.doOnNext(item);
                        pageNext(item);
                    }
                });
    }

    /**
     * 绑定数据方法
     *
     * @return
     */
    public abstract Observable<HashMap<String, Object>> bindData();


    /**
     * 默认分页方法
     *
     * @param stringObjectHashMap
     */
    void pageNext(HashMap<String, Object> stringObjectHashMap) {
        if (stringObjectHashMap != null) {
            int count = (int) stringObjectHashMap.get("count");
            List<Object> list = (List<Object>) stringObjectHashMap.get("list");
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
            }
            mFragment.stopLoadingOrRfresh();
        }
    }
}
