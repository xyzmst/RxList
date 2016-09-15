package org.xyzmst.rxlist.adapter;

import android.support.v7.widget.RecyclerView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * @author mac
 * @title RxAdapterEventOnSubscribe
 * @description 用于 RxAdapter可订阅的封装
 * @modifier
 * @date
 * @since 16/3/22 18:17
 **/
public class RxAdapterEventOnSubscribe<T> implements Observable.OnSubscribe<RxAdapterData> {

    final RxBaseAdapter mAdapter;

    public RxAdapterEventOnSubscribe(RxBaseAdapter adapter) {
        this.mAdapter = adapter;
    }


    @Override
    public void call(final Subscriber<? super RxAdapterData> subscriber) {

        RxBaseAdapter.Event<T> event = new RxBaseAdapter.Event<T>() {

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position) {
                RxAdapterData<T> data = new RxAdapterData();
                data.data = item;
                data.position = position;
                data.holder = holder;
                subscriber.onNext(data);
            }
        };

        mAdapter.setAdapterEvent(event);

        final Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                mAdapter.setAdapterEvent(null);
            }
        });

        subscriber.add(subscription);
    }

}
