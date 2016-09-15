package org.xyzmst.base;

import rx.Subscriber;

/**
 * 可用{@see com.mofun.rx.SubscriberWithWeakHost替代}；
 * 用此类时,切记要及时unsubscribe，否则容易影响GC回收
 * 名称: Subscriber基础类，用于占位，避免onerror使得程序崩溃<br>
 * 简述: <br>
 * 类型: JAVA<br>
 * 最近修改时间:2014/12/10s 10:22<br>
 * 改用doOnxx系列方法，方便catch到异常，避免错误事件循环冒泡的问题
 *
 * @author pwy
 * @since 2014/4/8
 */
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
