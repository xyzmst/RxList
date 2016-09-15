package org.xyzmst.rxlist.adapter;

import java.util.List;

/**
 * @author mac
 * @title RxAdapterAble
 * @description 当rx绑定非RxBaseAdapter时使用的adapter需要实现这个接口
 * @modifier
 * @date
 * @since 16/3/21 18:09
 **/
public interface RxAdapterAble<T> {

    void clear();

    void addAll(Object all);

    int size();

    void notifyChanged();

    List<T> getList();
}
