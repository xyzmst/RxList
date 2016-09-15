package org.xyzmst.rxlist.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * @author mac
 * @title RxAdapterData
 * @description 用于方便 将 item 显示 放到 sub中处理的封装
 * @modifier
 * @date
 * @since 16/4/23 23:48
 **/
public class RxAdapterData<T> {
    //每个条目的数据
    public T data;
    //位置
    public int position;
    //holder
    public RecyclerView.ViewHolder holder;
}
