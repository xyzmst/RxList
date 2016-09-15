package org.xyzmst.rxlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author mac
 * @title RxAdapterViewHelper
 * @description 用于将RxAdapter viewHolder 外放化
 * 解决 ViewHolder 必须写在adapter里的问题
 * @modifier
 * @date
 * @since 16/4/23 23:49
 **/
public interface RxAdapterBindView<T> {

    /**
     * 同 adapter的 getItemViewType
     *
     * @param data
     * @param position
     * @return
     */
    int getItemViewType(T data, int position);

    /**
     * 为 RxAdapter 指定一个 viewHolder
     *
     * @param viewType
     * @return
     */
    RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType);
}
