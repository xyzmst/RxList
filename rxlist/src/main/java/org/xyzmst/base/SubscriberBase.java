package org.xyzmst.base;

import rx.Subscriber;

public class SubscriberBase<T> extends Subscriber<T> {

    //    @Override
    public void doOnCompleted() {

    }

    //    @Override
    public void doOnError(Throwable e) {

    }

    //    @Override
    public void doOnNext(T item) {

    }

    @Override
    final public void onCompleted() {
        try {
            doOnCompleted();
        } catch (Exception e) {

        }
    }

    @Override
    final public void onError(Throwable e) {
        try {
            doOnError(e);
        } catch (Exception e2) {

        }
    }

    @Override
    final public void onNext(T t) {
        try {
            doOnNext(t);
        } catch (OutOfMemoryError e) {

        }
    }

}
