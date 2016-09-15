package org.xyzmst.rxlist.fragment;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * @author mac
 * @title ListFragmentEventOnSubscribe
 * @description
 * @modifier
 * @date
 * @since 16/3/22 18:17
 **/
public class RxFragmentEventOnSubscribe implements Observable.OnSubscribe<Void> {

    final RxListFragment fragment;

    public RxFragmentEventOnSubscribe(RxListFragment fragment) {
        this.fragment = fragment;

    }


    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        RxListFragment.Event event = new RxListFragment.Event() {
            @Override
            public void next() {
                subscriber.onNext(null);
            }
        };
        fragment.setEvent(event);
//        当 fragment 或者 activity 生命周期结束后 再缓存中 结束事件调用 rx 缓存机制 防止 事件不销毁造成内存泄露
        final Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                fragment.setEvent(null);
            }
        });

        subscriber.add(subscription);

    }


}
