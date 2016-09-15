package org.xyzmst.rxlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author mac
 * @title RxSimpleViewHolder
 * @description 简单的 ViewHolder 封装,支持一个 view
 * @modifier
 * @date
 * @since 16/4/25 14:13
 **/
public class RxSimpleViewHolder extends RecyclerView.ViewHolder {

    public View view;

    public RxSimpleViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }


}
